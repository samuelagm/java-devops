package pl.icwt.ecabs.bookingproducerservice.services;

import pl.icwt.ecabs.dtos.BookingDTO;

import java.util.UUID;

public interface BookingsService {
    BookingDTO addBooking(final BookingDTO bookingDTO);
    BookingDTO editBooking(final UUID bookingId, final BookingDTO bookingDTO);
    void delBooking(final UUID bookingId);
}
