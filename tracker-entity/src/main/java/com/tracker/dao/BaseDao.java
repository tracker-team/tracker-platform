package com.tracker.dao;

import java.io.Serializable;

public interface BaseDao<ENTITY, IDENTIFIER extends Serializable> {
    ENTITY find(IDENTIFIER id);
    void create(ENTITY entity);
    ENTITY merge(ENTITY entity);
    void update(ENTITY entity);
}
