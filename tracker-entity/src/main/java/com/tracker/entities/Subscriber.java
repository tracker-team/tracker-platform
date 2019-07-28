package com.tracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@DynamicUpdate
public class Subscriber extends BaseEntity {

    @JsonProperty
    @Column(unique = true)
    //TODO change to account ID
    private String externalId;

    private String name;

    private String emailId;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    @ManyToMany(mappedBy = "tripSubscribers",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Trip> trips;

    @ManyToMany(mappedBy = "stepSubscribers",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Step> steps;
}
