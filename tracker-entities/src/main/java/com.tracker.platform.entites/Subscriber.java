package com.tracker.platform.entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@DynamicUpdate
@ToString
public class Subscriber {
    @Id
    @JsonProperty
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty
    private String subscriberExternalId;

    private String name;

    private String emailId;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private EntityState entityState;

    @ManyToMany(mappedBy = "subscribers")
    @JsonIgnore
    @Transient
    private List<Trip> trips;

    @ManyToMany(mappedBy = "subscribers")
    @JsonIgnore
    @Transient
    private List<Step> steps;
}
