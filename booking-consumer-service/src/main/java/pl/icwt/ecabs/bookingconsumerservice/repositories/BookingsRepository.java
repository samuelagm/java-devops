package pl.icwt.ecabs.bookingconsumerservice.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.icwt.ecabs.bookingconsumerservice.entities.Booking;

import java.util.UUID;

@Repository
public interface BookingsRepository extends CrudRepository<Booking, UUID> {
    void deleteByBookingId(UUID id);
}
