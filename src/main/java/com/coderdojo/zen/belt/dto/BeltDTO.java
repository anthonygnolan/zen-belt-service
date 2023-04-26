package com.coderdojo.zen.belt.dto;

import com.coderdojo.zen.belt.model.Category;

public class BeltDTO {

    private final String name;
    private final String description;
    private final Category category;

    public BeltDTO(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }

}
