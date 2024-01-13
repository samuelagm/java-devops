package pl.icwt.ecabs.dtos;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class BookingDTO {

    private UUID bookingId;

    @NotBlank
    private String passengerName;

    @Pattern( regexp = "^\\+(?:[0-9] ?){6,14}[0-9]$", message = "Invalid phone number")
    private String passengerContactNumber;

    private OffsetDateTime pickupTime;

    private Boolean asap = true;

    private Integer waitingTime;

    @Positive(message = "Number of passengers must be positive")
    private Integer noOfPassengers;

    private BigDecimal price;

    @Range(min = 1, max = 5, message = "Rating must me number between 1 and 5")
    private Integer rating;

    private List<TripWaypointDTO> tripWayPoints = new ArrayList<>();
}
