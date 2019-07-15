package com.tracker.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity
@Data
@DynamicUpdate
public class Trip extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private TripState tripState;

    @JsonProperty
    private String tripExternalId;

    @Embedded
    private Location currentLocation;

    @JsonProperty
    @Column(unique = true, nullable = false)
    private String userId;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TripToSubscriber",
            joinColumns = {@JoinColumn(name = "trip_id")}, inverseJoinColumns = {@JoinColumn(name = "subscriber_id")})
    private Set<Subscriber> tripSubscribers = Sets.newHashSet();

    @Cascade(value = ALL)
    @OneToMany(fetch = FetchType.LAZY)
    private Set<TripThread> tripThreads = Sets.newHashSet();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TripToStep",
            joinColumns = {@JoinColumn(name = "tripId")}, inverseJoinColumns = {@JoinColumn(name = "stepId")})
    private List<Step> steps = Lists.newArrayList();

    @Enumerated(EnumType.STRING)
    private TripType tripType;

    @Embedded
    private TripStats tripStats;
}
