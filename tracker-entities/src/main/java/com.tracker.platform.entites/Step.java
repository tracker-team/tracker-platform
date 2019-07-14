package com.tracker.platform.entites;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@DynamicUpdate
@ToString
public class Step {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StepState stepState;

    @JsonProperty
    private String stepExternalId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "StepToSubscriber",
            joinColumns = {@JoinColumn(name = "stepId")}, inverseJoinColumns = {@JoinColumn(name = "subscriberId")})
    private Set<Subscriber> subscribers = Sets.newHashSet();

    @Embedded
    private Location location;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Attachment> attachments;

    //TODO:: How do we store in db
    private Set<StepCompletionCriteria> stepCompletionCriteria = Sets.newHashSet();
}
