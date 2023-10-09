package com.example.database;

import com.example.model.Booking;
import com.example.model.Block;
import com.example.model.Property;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Database {

    private static final Map<String, Booking> bookings = new HashMap<>();
    private static final Map<String, Block> blocks = new HashMap<>();
    private static final Map<String, Property> properties = new HashMap<>();

}
