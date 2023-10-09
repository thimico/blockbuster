package com.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Block {

    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Property property;

    public Block() {
    }

    public Block(LocalDate startDate, LocalDate endDate, Property property) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.property = property;
    }
}
