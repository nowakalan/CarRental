package pl.zdjavapol140.carrental.service.reservationManagement;

import lombok.Getter;
import pl.zdjavapol140.carrental.model.Reservation;

import java.util.Optional;


public record ReservationWrapper(Optional<Reservation> previousReservation, Optional<Reservation> nextReservation) {

}
