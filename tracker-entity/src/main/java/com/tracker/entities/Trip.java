package com.tracker.entities;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Trip extends BaseEntity{
    private String description;
}
