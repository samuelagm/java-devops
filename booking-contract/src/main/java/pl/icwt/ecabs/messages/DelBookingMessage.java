package pl.icwt.ecabs.messages;

import lombok.Data;

import java.util.UUID;

@Data
public class DelBookingMessage {
    private UUID bookingId;
}
