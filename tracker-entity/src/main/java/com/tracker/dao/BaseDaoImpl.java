package com.tracker.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.io.Serializable;

public class BaseDaoImpl<ENTITY,IDENTIFIER extends Serializable> extends AbstractDAO<ENTITY> implements BaseDao<ENTITY,IDENTIFIER> {

    public BaseDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
    @Override
    public ENTITY find(IDENTIFIER id) {
        return super.get(id);
    }

    @Override
    public void create(ENTITY entity) {
        currentSession().save(entity);
    }

    @Override
    public ENTITY merge(ENTITY entity) {
        return (ENTITY) currentSession().merge(entity);
    }

    @Override
    public void update(ENTITY entity) {
        currentSession().update(entity);
    }
}
