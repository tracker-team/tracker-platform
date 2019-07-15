package com.tracker.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    @JsonBackReference
    @Transient
    private Step associatedStep;

}
