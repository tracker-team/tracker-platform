package com.tracker.dao;

import com.google.inject.Inject;
import com.tracker.entities.Step;
import com.tracker.model.RequestThreadContext;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;


public class StepDaoImpl extends BaseDaoImpl<Step,Long> implements StepDao{
    @Inject
    public StepDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


    @Override
    public Optional<Step> findByExternalId(String externalId) {
//        CriteriaBuilder criteriaBuilder = currentSession().getCriteriaBuilder();
//        CriteriaQuery<Step> criteriaQuery = criteriaQuery();
//        Root<Step> root = criteriaQuery.from(Step.class);
//        criteriaQuery = criteriaQuery.select(root)
//                .where(criteriaBuilder.equal(root.get("stepExternalId"), stepExternalId),
//                        criteriaBuilder.equal(root.get("tenant"), RequestThreadContext.get().getTenant().name())
//                );
//
//        List<Step> steps = list(criteriaQuery);
//        if(steps.size() == 0) {
//            return Optional.absent();
//        }
//        else if(steps.size() > 1) {
//            //error
//        }
//        return Optional.fromNullable(steps.get(0));

        Criteria criteria = criteria();
        criteria.add(Restrictions.and(Restrictions.eq("externalId", externalId)));
        criteria.add(Restrictions.and(Restrictions.eq("tenant",
                RequestThreadContext.get().getTenant().name())));
        Step step = (Step) criteria.uniqueResult();
        return Optional.ofNullable(step);
    }
}
