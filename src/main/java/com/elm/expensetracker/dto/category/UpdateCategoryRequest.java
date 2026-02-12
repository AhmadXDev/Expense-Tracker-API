package com.elm.expensetracker.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class UpdateCategoryRequest {

    @Size(min = 1, max = 30, message = "Name characters must be between 1 and 30")
    private final String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private final String description;
}
