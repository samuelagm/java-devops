package pl.icwt.ecabs.bookingconsumerservice.services;

import pl.icwt.ecabs.dtos.BookingDTO;

import java.util.UUID;

public interface BookingsService {
    void addBooking(BookingDTO bookingDTO);

    void editBooking(UUID bookingId, BookingDTO bookingDTO);

    void delBooking(UUID bookingId);
}
