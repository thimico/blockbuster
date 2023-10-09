package com.example.service;

import com.example.model.Booking;
import com.example.repository.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private Booking booking1;

    @Mock
    private Booking booking2;

    private Method doDatesOverlapMethod;

    @BeforeEach
    public void setUp() throws NoSuchMethodException {
        doDatesOverlapMethod = BookingService.class.getDeclaredMethod("doDatesOverlap", Booking.class, Booking.class);
        doDatesOverlapMethod.setAccessible(true);
    }

    // Existing tests...

    @Test
    public void testUpdateBookingWhenBookingExistsAndNoOverlapThenReturnUpdatedBooking() {
        when(bookingRepository.findBookingById(anyString())).thenReturn(booking1);
        when(bookingRepository.findBookingsByProperty(any())).thenReturn(Collections.emptyList());

        Booking result = bookingService.updateBooking("1", booking2);

        assertEquals(booking2, result);
        verify(bookingRepository, times(1)).updateBooking(anyString(), any());
    }

    @Test
    public void testUpdateBookingWhenBookingDoesNotExistThenThrowEntityNotFoundException() {
        when(bookingRepository.findBookingById(anyString())).thenReturn(null);

        assertThrows(EntityNotFoundException.class, () -> bookingService.updateBooking("1", booking2));
        verify(bookingRepository, never()).updateBooking(anyString(), any());
    }

    @Test
    public void testUpdateBookingWhenBookingExistsAndOverlapThenThrowValidationException() {
        when(bookingRepository.findBookingById(anyString())).thenReturn(booking1);
        when(bookingRepository.findBookingsByProperty(any())).thenReturn(Collections.singletonList(booking1));

        assertThrows(ValidationException.class, () -> bookingService.updateBooking("1", booking2));
        verify(bookingRepository, never()).updateBooking(anyString(), any());
    }

    @Test
    public void testDoDatesOverlapWhenDatesOverlapThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 10));
        booking2.setStartDate(LocalDate.of(2022, 1, 5));
        booking2.setEndDate(LocalDate.of(2022, 1, 15));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }

    @Test
    public void testDoDatesOverlapWhenDatesDoNotOverlapThenReturnFalse() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 5));
        booking2.setStartDate(LocalDate.of(2022, 1, 6));
        booking2.setEndDate(LocalDate.of(2022, 1, 10));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertFalse(result);
    }

    @Test
    public void testDoDatesOverlapWhenStartDateEqualsEndDateThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 5));
        booking1.setEndDate(LocalDate.of(2022, 1, 10));
        booking2.setStartDate(LocalDate.of(2022, 1, 1));
        booking2.setEndDate(LocalDate.of(2022, 1, 5));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }

    @Test
    public void testDoDatesOverlapWhenEndDateEqualsStartDateThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 5));
        booking2.setStartDate(LocalDate.of(2022, 1, 5));
        booking2.setEndDate(LocalDate.of(2022, 1, 10));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }

    @Test
    public void testDoDatesOverlapWhenStartDateEqualsStartDateThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 10));
        booking2.setStartDate(LocalDate.of(2022, 1, 1));
        booking2.setEndDate(LocalDate.of(2022, 1, 5));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }

    @Test
    public void testDoDatesOverlapWhenEndDateEqualsEndDateThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 10));
        booking2.setStartDate(LocalDate.of(2022, 1, 5));
        booking2.setEndDate(LocalDate.of(2022, 1, 10));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }

    @Test
    public void testDoDatesOverlapWhenStartBeforeStartAndEndAfterEndThenReturnTrue() throws InvocationTargetException, IllegalAccessException {
        booking1.setStartDate(LocalDate.of(2022, 1, 1));
        booking1.setEndDate(LocalDate.of(2022, 1, 10));
        booking2.setStartDate(LocalDate.of(2022, 1, 3));
        booking2.setEndDate(LocalDate.of(2022, 1, 7));

        boolean result = (boolean) doDatesOverlapMethod.invoke(bookingService, booking1, booking2);

        assertTrue(result);
    }
}