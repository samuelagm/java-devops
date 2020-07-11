package pl.icwt.ecabs.bookingconsumerservice.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import pl.icwt.ecabs.bookingconsumerservice.services.BookingsService;
import pl.icwt.ecabs.consts.AmqpConsts;
import pl.icwt.ecabs.messages.AddBookingMessage;
import pl.icwt.ecabs.messages.DelBookingMessage;
import pl.icwt.ecabs.messages.EditBookingMessage;

@Slf4j
@Component
public class BookingsListener {

    private final BookingsService bookingsService;

    public BookingsListener(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @RabbitListener(queues = AmqpConsts.BOOKING_ADD_QUEUE)
    public void receiveAdd(final AddBookingMessage message) {
        log.debug("Received add: {}", message.getBooking().getPassengerName());

        bookingsService.addBooking(message.getBooking());
    }

    @RabbitListener(queues = AmqpConsts.BOOKING_EDIT_QUEUE)
    public void receiveEdit(final EditBookingMessage message) {
        log.debug("Received edit: {}", message.getBooking().getPassengerName());

        bookingsService.editBooking(message.getBookingId(), message.getBooking());
    }

    @RabbitListener(queues = AmqpConsts.BOOKING_DEL_QUEUE)
    public void receiveDel(final DelBookingMessage message) {
        log.debug("Received delete: {}", message.getBookingId());

        bookingsService.delBooking(message.getBookingId());
    }

}
