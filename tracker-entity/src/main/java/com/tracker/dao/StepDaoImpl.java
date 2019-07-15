package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Step;
import org.hibernate.SessionFactory;

public class StepDaoImpl extends BaseDaoImpl<Step,Long> implements StepDao{
    @Inject
    public StepDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
