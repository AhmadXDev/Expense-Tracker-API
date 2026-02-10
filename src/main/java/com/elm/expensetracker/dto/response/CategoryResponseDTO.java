package com.elm.expensetracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryResponseDTO {
    private final Long id;
    private final String name;
    private final String description;
}

//public CategoryResponseDTO  (cate)
