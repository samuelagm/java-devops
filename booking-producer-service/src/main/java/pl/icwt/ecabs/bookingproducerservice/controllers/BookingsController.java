package pl.icwt.ecabs.bookingproducerservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.icwt.ecabs.bookingproducerservice.services.BookingsService;
import pl.icwt.ecabs.dtos.BookingDTO;

import java.util.UUID;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/bookings")
public class BookingsController {

    private final BookingsService bookingsService;

    public BookingsController(BookingsService bookingsService) {
        this.bookingsService = bookingsService;
    }

    @Operation(summary = "Add new Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "New booking accepted for processing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid booking payload",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<BookingDTO> addBooking(
            @Validated @RequestBody BookingDTO bookingDTO) {

        log.debug("Received add booking request for passenger {}", bookingDTO.getPassengerName());
        BookingDTO responseDTO = bookingsService.addBooking(bookingDTO);

        return ResponseEntity.accepted().body(responseDTO);
    }

    @Operation(summary = "Edit Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Edit booking request accepted for processing",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = BookingDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid booking payload",
                    content = @Content)})
    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> editBooking(
            @Validated @RequestBody final BookingDTO bookingDTO,
            @PathVariable("bookingId") final UUID bookingId) {

        log.debug("Received edit booking request for id {}", bookingId);
        BookingDTO responseDTO = bookingsService.editBooking(bookingId, bookingDTO);

        return ResponseEntity.accepted().body(responseDTO);
    }

    @Operation(summary = "Delete Booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Delete booking request accepted for processing", content = @Content)})
    @DeleteMapping("/{bookingId}")
    public ResponseEntity deleteBooking(
            @PathVariable("bookingId") final UUID bookingId) {

        log.debug("Received delete booking request for id {}", bookingId);
        bookingsService.delBooking(bookingId);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
