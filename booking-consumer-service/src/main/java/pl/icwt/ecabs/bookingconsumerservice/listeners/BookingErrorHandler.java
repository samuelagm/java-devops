package pl.icwt.ecabs.bookingconsumerservice.listeners;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Slf4j
@Component
public class BookingErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {

        log.error("Unable to process message: {}", t.getMessage());
        throw new AmqpRejectAndDontRequeueException("Unable to process message", t);

    }
}
