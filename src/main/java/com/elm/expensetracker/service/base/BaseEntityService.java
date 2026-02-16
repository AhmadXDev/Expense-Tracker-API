package com.elm.expensetracker.service.base;

import com.elm.expensetracker.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseEntityService<Entity, REPO extends JpaRepository<Entity, Long>> {
    protected abstract REPO getRepository();
    protected abstract String getEntityName();

    public Entity findById(Long id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getEntityName(), id));

    }

}
