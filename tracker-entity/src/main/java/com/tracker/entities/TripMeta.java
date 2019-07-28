package com.tracker.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class TripMeta extends BaseEntity{
    private String name;
    private String description;

    @OneToOne
    @JoinColumn
    private StepMeta startStep;

    private List<String> subscriberAccountIds;
}
