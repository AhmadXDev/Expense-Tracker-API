package com.elm.expense_tracker_api.model;

import java.time.Instant;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    private Instant createdAt; 
    private Instant updatedAt; 

    @PrePersist
    void onCreate() { 
        this.createdAt = Instant.now(); 
    }

    @PreUpdate
    void onUpdate(){ 
        this.updatedAt = Instant.now(); 
    }

}
