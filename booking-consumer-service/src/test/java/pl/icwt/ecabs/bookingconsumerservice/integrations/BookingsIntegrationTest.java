package pl.icwt.ecabs.bookingconsumerservice.integrations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.icwt.ecabs.bookingconsumerservice.entities.Booking;
import pl.icwt.ecabs.bookingconsumerservice.entities.TripWaypoint;
import pl.icwt.ecabs.bookingconsumerservice.listeners.BookingsListener;
import pl.icwt.ecabs.bookingconsumerservice.repositories.BookingsRepository;
import pl.icwt.ecabs.dtos.BookingDTO;
import pl.icwt.ecabs.dtos.TripWaypointDTO;
import pl.icwt.ecabs.messages.AddBookingMessage;
import pl.icwt.ecabs.messages.DelBookingMessage;
import pl.icwt.ecabs.messages.EditBookingMessage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class BookingsIntegrationTest {

    @Autowired
    private BookingsListener bookingsListener;

    @MockBean
    private BookingsRepository repository;

    @Test
    public void testAddBookingSuccess() {

        BookingDTO bookingDTO = getSimpleBookingDTO();

        AddBookingMessage message = new AddBookingMessage();
        message.setBooking(bookingDTO);

        bookingsListener.receiveAdd(message);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        Mockito.verify(repository).save(captor.capture());

        Booking savedBooking = captor.getValue();

        compareDtoAndModel(bookingDTO, savedBooking);
    }

    @Test
    public void testEditBookingChangeNameSuccess() {

        BookingDTO bookingDTO = getSimpleBookingDTO();

        Mockito.when(repository.findById(bookingDTO.getBookingId())).thenReturn(Optional.of(getSimpleBooking()));

        bookingDTO.setPassengerName("Other Passenger Name");

        EditBookingMessage message = new EditBookingMessage();
        message.setBookingId(bookingDTO.getBookingId());
        message.setBooking(bookingDTO);

        bookingsListener.receiveEdit(message);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        Mockito.verify(repository).save(captor.capture());

        Booking savedBooking = captor.getValue();

        Assertions.assertEquals(bookingDTO.getPassengerName(), savedBooking.getPassengerName());
    }

    @Test
    public void testEditBookingAddWaypointSuccess() {

        BookingDTO bookingDTO = getSimpleBookingDTO();
        Booking booking = getSimpleBooking();

        Mockito.when(repository.findById(bookingDTO.getBookingId())).thenReturn(Optional.of(booking));

        TripWaypointDTO waypointDTO = new TripWaypointDTO();
        waypointDTO.setTripWayPointId(UUID.randomUUID());
        waypointDTO.setLastStop(true);
        waypointDTO.setLocality("Gzira");
        waypointDTO.setLat(35.90583);
        waypointDTO.setLng(14.48806);
        waypointDTO.setTripWayPointTimestamp(Instant.now());

        bookingDTO.getTripWayPoints().add(waypointDTO);

        EditBookingMessage message = new EditBookingMessage();
        message.setBookingId(bookingDTO.getBookingId());
        message.setBooking(bookingDTO);

        bookingsListener.receiveEdit(message);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        Mockito.verify(repository).save(captor.capture());

        Booking savedBooking = captor.getValue();

        compareDtoAndModel(bookingDTO, savedBooking);
        Assertions.assertEquals(2, savedBooking.getTripWayPoints().size());

        Assertions.assertEquals(waypointDTO.getTripWayPointId(), savedBooking.getTripWayPoints().get(1).getTripWayPointId());
        Assertions.assertEquals(waypointDTO.getLastStop(), savedBooking.getTripWayPoints().get(1).getLastStop());
        Assertions.assertEquals(waypointDTO.getLocality(), savedBooking.getTripWayPoints().get(1).getLocality());
        Assertions.assertEquals(waypointDTO.getLat(), savedBooking.getTripWayPoints().get(1).getLat());
        Assertions.assertEquals(waypointDTO.getLng(), savedBooking.getTripWayPoints().get(1).getLng());
        Assertions.assertEquals(waypointDTO.getTripWayPointTimestamp(), savedBooking.getTripWayPoints().get(1).getTripWayPointTimestamp());
    }

    @Test
    public void testEditBookingDelWaypointSuccess() {

        BookingDTO bookingDTO = getSimpleBookingDTO();

        Mockito.when(repository.findById(bookingDTO.getBookingId())).thenReturn(Optional.of(getSimpleBooking()));

        bookingDTO.getTripWayPoints().clear();

        EditBookingMessage message = new EditBookingMessage();
        message.setBookingId(bookingDTO.getBookingId());
        message.setBooking(bookingDTO);

        bookingsListener.receiveEdit(message);

        ArgumentCaptor<Booking> captor = ArgumentCaptor.forClass(Booking.class);
        Mockito.verify(repository).save(captor.capture());

        Booking savedBooking = captor.getValue();

        Assertions.assertEquals(0, savedBooking.getTripWayPoints().size());
    }


    @Test
    public void testDeleteBookingSuccess() {

        UUID bookingId = UUID.randomUUID();

        DelBookingMessage message = new DelBookingMessage();
        message.setBookingId(bookingId);

        bookingsListener.receiveDel(message);
        Mockito.verify(repository).deleteByBookingId(ArgumentMatchers.eq(bookingId));
    }

    private BookingDTO getSimpleBookingDTO() {
        UUID bookingId = UUID.randomUUID();
        UUID waypointId = UUID.randomUUID();

        TripWaypointDTO waypointDTO = new TripWaypointDTO();
        waypointDTO.setTripWayPointId(waypointId);
        waypointDTO.setLastStop(true);
        waypointDTO.setLocality("Naxxar");
        waypointDTO.setLat(35.914982);
        waypointDTO.setLng(14.445219);
        waypointDTO.setTripWayPointTimestamp(Instant.now());

        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setBookingId(bookingId);
        bookingDTO.setPassengerName("John Doe");
        bookingDTO.setPassengerContactNumber("+35699740158");
        bookingDTO.setPickupTime(OffsetDateTime.now(ZoneId.systemDefault()));
        bookingDTO.setAsap(true);
        bookingDTO.setWaitingTime(1);
        bookingDTO.setNoOfPassengers(2);
        bookingDTO.setPrice(BigDecimal.valueOf(25.10));
        bookingDTO.setRating(5);
        bookingDTO.getTripWayPoints().add(waypointDTO);

        return bookingDTO;
    }

    private Booking getSimpleBooking() {
        UUID bookingId = UUID.randomUUID();
        UUID waypointId = UUID.randomUUID();

        TripWaypoint waypoint = new TripWaypoint();
        waypoint.setTripWayPointId(waypointId);
        waypoint.setLastStop(true);
        waypoint.setLocality("Naxxar");
        waypoint.setLat(35.914982);
        waypoint.setLng(14.445219);
        waypoint.setTripWayPointTimestamp(Instant.now());

        Booking booking = new Booking();
        booking.setBookingId(bookingId);
        booking.setPassengerName("John Doe");
        booking.setPassengerContactNumber("+35699740158");
        booking.setPickupTime(OffsetDateTime.now(ZoneId.systemDefault()));
        booking.setAsap(true);
        booking.setWaitingTime(1);
        booking.setNoOfPassengers(2);
        booking.setPrice(BigDecimal.valueOf(25.10));
        booking.setRating(5);
        booking.getTripWayPoints().add(waypoint);

        return booking;
    }

    private void compareDtoAndModel(BookingDTO bookingDTO, Booking savedBooking) {
        Assertions.assertEquals(bookingDTO.getBookingId(), savedBooking.getBookingId());
        Assertions.assertEquals(bookingDTO.getPassengerName(), savedBooking.getPassengerName());
        Assertions.assertEquals(bookingDTO.getPassengerContactNumber(), savedBooking.getPassengerContactNumber());
        Assertions.assertEquals(bookingDTO.getPickupTime(), savedBooking.getPickupTime());
        Assertions.assertEquals(bookingDTO.getAsap(), savedBooking.getAsap());
        Assertions.assertEquals(bookingDTO.getWaitingTime(), savedBooking.getWaitingTime());
        Assertions.assertEquals(bookingDTO.getNoOfPassengers(), savedBooking.getNoOfPassengers());
        Assertions.assertEquals(bookingDTO.getPrice(), savedBooking.getPrice());
        Assertions.assertEquals(bookingDTO.getRating(), savedBooking.getRating());

        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getTripWayPointId(), savedBooking.getTripWayPoints().get(0).getTripWayPointId());
        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getLastStop(), savedBooking.getTripWayPoints().get(0).getLastStop());
        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getLocality(), savedBooking.getTripWayPoints().get(0).getLocality());
        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getLat(), savedBooking.getTripWayPoints().get(0).getLat());
        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getLng(), savedBooking.getTripWayPoints().get(0).getLng());
        Assertions.assertEquals(bookingDTO.getTripWayPoints().get(0).getTripWayPointTimestamp(), savedBooking.getTripWayPoints().get(0).getTripWayPointTimestamp());
    }
}
