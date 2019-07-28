package com.tracker.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Data
@DynamicUpdate
public class Trip extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private TripState tripState;

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
    private Set<Subscriber> tripSubscribers = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private Set<EntityThread> entityThreads = Sets.newHashSet();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "associatedTrip")
    private List<Step> steps = Lists.newArrayList();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "associatedTrip")
    private List<Attachment> attachments = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private TripType tripType;


    //TODO estimates
    @Embedded
    private TripStats tripStats;
}
