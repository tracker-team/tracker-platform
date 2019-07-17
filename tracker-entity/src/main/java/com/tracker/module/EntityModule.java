package com.tracker.module;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.tracker.dao.StepDao;
import com.tracker.dao.StepDaoImpl;

public class EntityModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StepDao.class).to(StepDaoImpl.class).in(Singleton.class);
    }
}
