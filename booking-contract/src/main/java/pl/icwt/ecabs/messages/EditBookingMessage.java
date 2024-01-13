package pl.icwt.ecabs.messages;

import lombok.Data;
import pl.icwt.ecabs.dtos.BookingDTO;

import java.util.UUID;

@Data
public class EditBookingMessage {
    private UUID bookingId;
    private BookingDTO booking;
}
