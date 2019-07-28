package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Step;
import com.tracker.model.RequestThreadContext;
import com.tracker.model.exceptions.TrackerException;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;


public class StepDaoImpl extends BaseDaoImpl<Step,Long> implements StepDao{
    @Inject
    public StepDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public Optional<Step> findByExternalId(String externalId) {
        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
        CriteriaQuery<Step> criteriaQuery = criteriaQuery();
        Root<Step> root = criteriaQuery.from(Step.class);
        criteriaQuery = criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("externalId"), externalId),
                        criteriaBuilder.equal(root.get("tenant"), RequestThreadContext.get().getTenant().name())
                );

        List<Step> steps = list(criteriaQuery);
        if(steps == null || steps.size() == 0) {
            return Optional.empty();
        }
        else if(steps.size() > 1) {
            throw new TrackerException("More than one step present for external id :: " + externalId);
        }
        return Optional.ofNullable(steps.get(0));
    }
}
