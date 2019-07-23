package com.tracker.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@DynamicUpdate
public class Step extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private StepState stepState;

    @JsonProperty
    @Column(unique = true)
    private String externalId;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "StepToSubscriber",
            joinColumns = {@JoinColumn(name = "step_id")}, inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
    private Set<Subscriber> stepSubscribers = Sets.newHashSet();

    @Embedded
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},mappedBy = "associatedStep")
    @JsonManagedReference
    private List<Attachment> attachments;

    //TODO:: How do we store in db
    @Transient
    private Set<StepCompletionCriteria> stepCompletionCriteria = Sets.newHashSet();

    @OneToOne
    @JoinColumn
    private Step previousStep;

    @OneToOne
    @JoinColumn
    private Step nextStep;
}
