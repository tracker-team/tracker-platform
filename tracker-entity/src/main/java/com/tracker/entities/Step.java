package com.tracker.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Step extends BaseEntity{
    @Column(length = 1024)
    private String description;

    @Column(unique = true,nullable = false)
    private String externalStepId;

}
