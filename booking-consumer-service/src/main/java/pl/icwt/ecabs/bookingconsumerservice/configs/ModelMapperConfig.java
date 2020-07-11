package pl.icwt.ecabs.bookingconsumerservice.configs;

import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.icwt.ecabs.bookingconsumerservice.entities.Booking;
import pl.icwt.ecabs.bookingconsumerservice.entities.TripWaypoint;
import pl.icwt.ecabs.dtos.BookingDTO;
import pl.icwt.ecabs.dtos.TripWaypointDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        TypeMap<BookingDTO, Booking> typeMap = mapper.createTypeMap(BookingDTO.class, Booking.class);

        typeMap.addMappings(mapping -> mapping.using(waypointConverter(mapper))
                .map(BookingDTO::getTripWayPoints, Booking::setTripWayPoints));

        return mapper;
    }

    private Converter<List<TripWaypointDTO>, List<TripWaypoint>> waypointConverter(ModelMapper mapper) {
        return new Converter<List<TripWaypointDTO>, List<TripWaypoint>>() {
            @Override
            public List<TripWaypoint> convert(MappingContext<List<TripWaypointDTO>, List<TripWaypoint>> context) {

                List<TripWaypoint> destination = (Objects.isNull(context.getDestination())) ? new ArrayList<>() : context.getDestination();

                for (TripWaypointDTO waypointDTO : context.getSource()) {
                    if (Objects.nonNull(context.getDestination())) {
                        Optional<TripWaypoint> optionalTripWaypoint = destination.stream()
                                .filter(tripWaypoint -> tripWaypoint.getTripWayPointId().equals(waypointDTO.getTripWayPointId()))
                                .findFirst();

                        if (optionalTripWaypoint.isPresent()) {
                            TripWaypoint waypoint = optionalTripWaypoint.get();
                            mapper.map(waypointDTO, waypoint);
                            context.getDestination().add(waypoint);
                        } else {
                            destination.add(mapper.map(waypointDTO, TripWaypoint.class));
                        }
                    } else {

                        destination.add(mapper.map(waypointDTO, TripWaypoint.class));
                    }
                }

                return destination;
            }
        };

    }


}
