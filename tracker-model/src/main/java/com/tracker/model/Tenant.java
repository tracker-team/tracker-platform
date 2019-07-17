package com.tracker.model;

import java.util.Arrays;
import java.util.Optional;

public enum Tenant {
    MSME;

    public static Optional<Tenant> getTenant(String name){
        return Arrays.stream(Tenant.values()).filter(x->x.name().equals(name)).findFirst();
    }
}
