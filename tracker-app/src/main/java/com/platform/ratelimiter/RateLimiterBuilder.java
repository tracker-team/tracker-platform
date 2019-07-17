package com.platform.ratelimiter;

import com.google.inject.Singleton;
import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.internal.InMemoryRateLimiterRegistry;
import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
@Singleton
public class RateLimiterBuilder {
    private InMemoryRateLimiterRegistry inMemoryRateLimiterRegistry;
    public RateLimiterBuilder() {
        this.inMemoryRateLimiterRegistry = new InMemoryRateLimiterRegistry();
    }

    public void  addConfigs(Map<String,RateLimiterConfig> configs){
        inMemoryRateLimiterRegistry = new InMemoryRateLimiterRegistry(configs);
        configs.entrySet().forEach(x->inMemoryRateLimiterRegistry.rateLimiter(x.getKey(), x.getKey()));
    }

    public void  addConfig(String name,RateLimiterConfig config){
        inMemoryRateLimiterRegistry.addConfiguration(name,config );
        inMemoryRateLimiterRegistry.rateLimiter(name, name);
    }

    public Optional<RateLimiter> getRateLimiter(String name){
        Optional<RateLimiterConfig> configuration = inMemoryRateLimiterRegistry.getConfiguration(name);
        if(configuration.isPresent()){
            return Optional.ofNullable(inMemoryRateLimiterRegistry.rateLimiter(name));
        }else{
            return Optional.empty();
        }
    }

}
