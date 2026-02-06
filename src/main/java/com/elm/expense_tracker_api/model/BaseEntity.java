package com.elm.expense_tracker_api.model;

import java.time.Instant;

import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    private Instant createdAt; 
    private Instant updatedAt; 

}
