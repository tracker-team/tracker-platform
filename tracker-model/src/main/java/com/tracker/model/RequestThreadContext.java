package com.tracker.model;

import lombok.Data;

@Data
public class RequestThreadContext {
    private String userName;
    private String clientId;
    private Tenant tenant;
    private static ThreadLocal<RequestThreadContext> threadLocal = new ThreadLocal<RequestThreadContext>() {
        @Override
        protected RequestThreadContext initialValue() {
            return new RequestThreadContext();
        }

    };
    public static RequestThreadContext get() {
        return threadLocal.get();
    }
}
