package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public void addReservation(Reservation reservation) {
        reservation.setStatus(ReservationStatus.SET);
        reservationRepository.save(reservation);
    }
}
