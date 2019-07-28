package com.tracker.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import com.tracker.model.status.TripStatus;
import com.tracker.model.status.TripSubStatus;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@DynamicUpdate
public class Trip extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;

    @Enumerated(EnumType.STRING)
    private TripSubStatus tripSubStatus;

    @JsonProperty
    @Column(unique = true)
    private String externalId;

    @Embedded
    private Location currentLocation;

    @JsonProperty
    @Column(unique = true, nullable = false)
    private String userId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TripToSubscriber",
            joinColumns = {@JoinColumn(name = "trip_id")}, inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
    @JsonManagedReference
    private Set<Subscriber> tripSubscribers = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "Trip_threads", joinColumns = {@JoinColumn(name = "trip_id")}, inverseJoinColumns = {@JoinColumn(name = "thread_id")})
    @JsonManagedReference
    private Set<TripThread> tripThreads = Sets.newHashSet();

    @OneToOne
    @JsonManagedReference
    private Step startStep;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "Trip_currentStep", joinColumns = {@JoinColumn(name = "trip_id")}, inverseJoinColumns = {@JoinColumn(name = "step_id")})
    @JsonManagedReference
    private Step currentStep;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "associatedTrip")
    private List<Attachment> attachments = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private TripType tripType;


    //TODO estimates
    @Embedded
    private TripStats tripStats;
}
