package pl.icwt.ecabs.messages;

import lombok.Data;

import java.util.UUID;

@Data
public class DelWaypointMessage {
    private UUID bookingId;
    private UUID waypointId;
}
