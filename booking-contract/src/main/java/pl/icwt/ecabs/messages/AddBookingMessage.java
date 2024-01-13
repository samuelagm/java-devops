package pl.icwt.ecabs.messages;

import lombok.Data;
import pl.icwt.ecabs.dtos.BookingDTO;

@Data
public class AddBookingMessage {
    private BookingDTO booking;
}
