package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;

import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {

    ReservationRepository reservationRepository;

    public List<Reservation> findAllReservationByCarId(Long carId) {
        return reservationRepository.findReservationsByCarId(carId);
    }

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
