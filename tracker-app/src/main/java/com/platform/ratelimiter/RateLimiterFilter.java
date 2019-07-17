package com.platform.ratelimiter;


import com.google.inject.Inject;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Optional;

@Provider
@com.platform.ratelimiter.RateLimiter
@Priority(value = 1)
@Slf4j
public class RateLimiterFilter implements ContainerRequestFilter {
    private final RateLimiterBuilder rateLimiterBuilder;

    @Context
    private ResourceInfo resourceInfo;

    @Inject
    public RateLimiterFilter(RateLimiterBuilder rateLimiterBuilder) {
        this.rateLimiterBuilder = rateLimiterBuilder;
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        Method resourceMethod = resourceInfo.getResourceMethod();
        Optional<com.platform.ratelimiter.RateLimiter> rateLimiterAnn = Optional.ofNullable(resourceMethod.getAnnotation(com.platform.ratelimiter.RateLimiter.class));
        rateLimiterAnn.ifPresent(annotation->{
            String keyPart1 = annotation.name();
            String separator = annotation.separator();
            String headerKey = annotation.headerKey();
            Optional<String> keyPart2 = Optional.ofNullable(containerRequestContext.getHeaders().getFirst(headerKey));
            Optional<RateLimiter> rateLimiter = rateLimiterBuilder.getRateLimiter(keyPart1+separator+keyPart2.orElseGet(()->""));
            log.info("rate limitor config key for request "+keyPart1+separator+keyPart2.orElseGet(()->""));
            rateLimiter.ifPresent(limiter-> {
                    log.info("rate limiter configured for request "+limiter.toString());
                    boolean permission=limiter.acquirePermission();
                    if (Thread.interrupted()) {
                        log.error("Thread was interrupted during permission wait, for rate limiter : " + limiter.getName());
                        containerRequestContext.abortWith(Response.ok("Thread was interrupted during permission wait, for rate limiter :"+ limiter.getName()).status(503).build());
                    }
                    if (!permission) {
                        log.error("Request not permitted for limiter: " + limiter.getName());
                        containerRequestContext.abortWith(Response.ok("Request not permitted for limiter: " + limiter.getName()).status(503).build());
                    } else {
                        log.info("request allowed through rate limiter");
                    }
                });
        });
    }
}