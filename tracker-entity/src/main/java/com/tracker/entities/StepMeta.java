package com.tracker.entities;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
@Data
@DynamicUpdate
public class StepMeta extends BaseEntity {
    private String name;
    private String description;

    @Embedded
    private Location location;

    //List<SubscriberMeta> :: SubscriberMeta{name, emailId, phoneNumber, AccountId}
}
