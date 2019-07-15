package com.platform.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Slf4j
@Provider
public class ResponceFilter implements ContainerResponseFilter {
    private final ObjectMapper objectMapper;

    @Inject
    public ResponceFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log.info("<<<Responding>>>");
        log.info("Response code : " + responseContext.getStatus());
        try {
            log.info("Response Body : " + objectMapper.writeValueAsString(responseContext.getEntity()));
        } catch (JsonProcessingException e) {
            log.error("Error converting response entity to Json", e);
        }
        log.info("<<<Responded>>>");

    }
}
