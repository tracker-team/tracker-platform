package com.tracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attachment extends BaseEntity{
    @Column(unique = true)
    private String docKey;
    private String fileName;
    private Long fileSize;
    private String description;
    private String contentType;
    private String collectionName;
    private String url;
    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "step_id")
    @JsonBackReference
    private Step associatedStep;

}
