package com.platform.services;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.tracker.model.exceptions.TrackerException;
import com.tracker.dao.StepDao;
import com.tracker.entities.Step;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class StepServiceImpl implements StepService {
    private final StepDao stepDao;

    @Inject
    public StepServiceImpl(StepDao stepDao) {
        this.stepDao = stepDao;
    }

    @Override
    @Transactional
    public Step getStep(String externalId) {
        Optional<Step> step = stepDao.findByExternalId(externalId);
        if(!step.isPresent()) {
            throw new TrackerException("Unable to find step with id :: " + externalId, Response.Status.NOT_FOUND);
        }
        return step.get();
    }
}
