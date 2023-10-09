package com.example.model;

import lombok.Data;

@Data
public class Property {

    private String id;
    private String name;
    private String location;
    private String description;

    public Property() {
    }

    public Property(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
    }
}
