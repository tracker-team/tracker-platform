package com.tracker.dao;

import com.tracker.entities.Step;

import java.util.Optional;

public interface StepDao extends BaseDao<Step,Long>{
    public Optional<Step> findByExternalId(String externalId);
}
