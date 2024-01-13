package pl.icwt.ecabs.dtos;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Data
public class TripWaypointDTO {
    private UUID tripWayPointId;

    @NotNull(message = "Last Stop can't be null")
    private Boolean lastStop;

    @NotBlank(message = "Locality can't be blank")
    private String locality;

    @NotNull(message = "Lat can't be null")
    private Double lat;

    @NotNull(message = "Lng can't be null")
    private Double lng;

    private Instant tripWayPointTimestamp;;
}
