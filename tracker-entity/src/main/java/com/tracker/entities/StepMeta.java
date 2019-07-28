package com.tracker.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class StepMeta extends BaseEntity {
    private String name;
    private String description;

    @Embedded
    private Location location;

    @OneToOne
    @JoinColumn
    private StepMeta nextStepMeta;

    @OneToOne
    @JoinColumn
    private StepMeta previousStepMeta;

    private List<String> subscriberAccountIds;
}
