package pl.icwt.ecabs.bookingproducerservice.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import pl.icwt.ecabs.consts.AmqpConsts;
import pl.icwt.ecabs.dtos.BookingDTO;
import pl.icwt.ecabs.messages.AddBookingMessage;
import pl.icwt.ecabs.messages.DelBookingMessage;
import pl.icwt.ecabs.messages.EditBookingMessage;

import java.util.Objects;
import java.util.UUID;

@Service
public class BookingsServiceImpl implements BookingsService {

    private final RabbitTemplate rabbitTemplate;

    public BookingsServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public BookingDTO addBooking(final BookingDTO bookingDTO) {

        bookingDTO.setBookingId(UUID.randomUUID());
        bookingDTO.getTripWayPoints().forEach(tripWaypointDTO -> tripWaypointDTO.setTripWayPointId(UUID.randomUUID()));
        AddBookingMessage message = new AddBookingMessage();
        message.setBooking(bookingDTO);

        rabbitTemplate.convertAndSend(AmqpConsts.MESSAGE_EXCHANGE, AmqpConsts.BOOKING_ADD_TOPIC, message);

        return bookingDTO;
    }

    @Override
    public BookingDTO editBooking(UUID bookingId, BookingDTO bookingDTO) {
        EditBookingMessage message = new EditBookingMessage();
        message.setBookingId(bookingId);
        message.setBooking(bookingDTO);

        bookingDTO.getTripWayPoints().forEach(tripWaypointDTO -> {
            if(Objects.isNull(tripWaypointDTO.getTripWayPointId())) {
                tripWaypointDTO.setTripWayPointId(UUID.randomUUID());
            }
        });

        rabbitTemplate.convertAndSend(AmqpConsts.MESSAGE_EXCHANGE, AmqpConsts.BOOKING_EDIT_TOPIC, message);

        return bookingDTO;
    }

    @Override
    public void delBooking(UUID bookingId) {
        DelBookingMessage message = new DelBookingMessage();
        message.setBookingId(bookingId);

        rabbitTemplate.convertAndSend(AmqpConsts.MESSAGE_EXCHANGE, AmqpConsts.BOOKING_DEL_TOPIC, message);
    }
}
