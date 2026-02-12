package com.elm.expensetracker.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
public class CategoryRequest {

    @NotBlank(message = "Category must have a name")
    @Size(min = 1, max = 30, message = "name characters must be between 1 to 30")
    private final String name;

    @Size(max = 200, message = "Description must not exceed 200 characters")
    private final String description;
}
