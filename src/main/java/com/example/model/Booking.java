package com.example.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Booking {

    private String id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guest;
    private Property property;

    public Booking() {
    }

    public Booking(LocalDate startDate, LocalDate endDate, Guest guest, Property property) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.guest = guest;
        this.property = property;
    }

    @Data
    public static class Guest {
        private String name;
        private String contactInfo;
    }
}
