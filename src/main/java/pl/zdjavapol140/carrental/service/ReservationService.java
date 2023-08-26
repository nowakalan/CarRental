package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.CarRent;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.model.ReservationStatus;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

//    public void addReservation(Reservation reservation) {
//        reservation.setStatus(ReservationStatus.SET);
//        reservationRepository.save(reservation);
//    }
//
//    //TODO
//    public Long updateReservationStatusToInProgress(Long reservationId) {
//        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(() -> new RuntimeException("Reservation Id not foung"));
//        reservation.setStatus(ReservationStatus.IN_PROGRESS);
//        reservationRepository.save(reservation);
//        return reservation.getCarId();
//}
}
