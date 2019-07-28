package com.tracker.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class EntityThread extends BaseEntity{

    @Lob
    private String text;

    private String associatedEntityId;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Attachment> attachments;

}
