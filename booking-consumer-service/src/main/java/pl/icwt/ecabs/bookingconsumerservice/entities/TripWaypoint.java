package pl.icwt.ecabs.bookingconsumerservice.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Entity
public class TripWaypoint {

    @Id
    private UUID tripWayPointId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Booking booking;
    private Boolean lastStop;
    private String locality;
    private Double lat;
    private Double lng;
    private Instant tripWayPointTimestamp;
}
