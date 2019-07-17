package com.platform.ratelimiter;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class RateLimiterModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(RateLimiterFilter.class).in(Singleton.class);
    }
}
