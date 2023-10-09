package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Booking createBooking(Booking booking) {
        if (checkBookingOverlap(booking, null)) {
            return bookingRepository.saveBooking(booking);
        } else {
            throw new ValidationException("Booking overlaps with existing bookings");
        }
    }

    public void deleteBooking(String bookingId) {
        Booking booking = bookingRepository.findBookingById(bookingId);
        if (booking == null) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found");
        }
        bookingRepository.deleteBooking(bookingId);
    }

    public Booking updateBooking(String bookingId, Booking booking) {
        Booking existingBooking = bookingRepository.findBookingById(bookingId);
        if (existingBooking == null) {
            throw new EntityNotFoundException("Booking with ID " + bookingId + " not found");
        }
        if (checkBookingOverlap(booking, existingBooking.getId())) {
            return bookingRepository.updateBooking(bookingId, booking);
        } else {
            throw new ValidationException("Booking overlaps with existing bookings");
        }
    }

    private boolean checkBookingOverlap(Booking newBooking, String excludedBookingId) {
        List<Booking> existingBookings = bookingRepository.findBookingsByProperty(newBooking.getProperty());
        for (Booking existingBooking : existingBookings) {
            if (!existingBooking.getId().equals(excludedBookingId) && doDatesOverlap(newBooking, existingBooking)) {
                return false;
            }
        }
        return true;
    }

    private boolean doDatesOverlap(Booking booking1, Booking booking2) {
        LocalDate startDate1 = booking1.getStartDate();
        LocalDate endDate1 = booking1.getEndDate();
        LocalDate startDate2 = booking2.getStartDate();
        LocalDate endDate2 = booking2.getEndDate();
        return !(endDate1.isBefore(startDate2) || startDate1.isAfter(endDate2));
    }
}
