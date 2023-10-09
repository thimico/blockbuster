package com.example.repository;

import com.example.model.Booking;
import com.example.model.Property;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookingRepository {

    private final List<Booking> bookings = new ArrayList<>();

    public Booking findBookingById(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getId().equals(bookingId)) {
                return booking;
            }
        }
        return null;
    }

    public Booking saveBooking(Booking booking) {
        booking.setId(String.valueOf(bookings.size() + 1));
        bookings.add(booking);
        return booking;
    }

    public void deleteBooking(String bookingId) {
        bookings.removeIf(booking -> booking.getId().equals(bookingId));
    }

    public Booking updateBooking(String bookingId, Booking updatedBooking) {
        // Implement updateBooking logic
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            if (booking.getId().equals(bookingId)) {
                updatedBooking.setId(bookingId);
                bookings.set(i, updatedBooking);
                return updatedBooking;
            }
        }
        return null;
    }

    public List<Booking> findBookingsByProperty(Property property) {
        List<Booking> propertyBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getProperty().equals(property)) {
                propertyBookings.add(booking);
            }
        }
        return propertyBookings;
    }
}
