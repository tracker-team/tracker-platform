package com.tracker.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracker.model.RequestThreadContext;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String createdByUser;
    private String lastUpdatedByUser;
    private String tenant;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = new Date();
        createdByUser = RequestThreadContext.get().getUserName();
        onUpdate();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = new Date();
        lastUpdatedByUser = RequestThreadContext.get().getUserName();
    }
}
