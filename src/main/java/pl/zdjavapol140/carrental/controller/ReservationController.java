package pl.zdjavapol140.carrental.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.model.Reservation;
import pl.zdjavapol140.carrental.repository.ReservationRepository;
import pl.zdjavapol140.carrental.service.ReservationService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReservationController {

//    private final ReservationRepository reservationRepository;
//
//    public ReservationController(ReservationRepository reservationRepository) {
//        this.reservationRepository = reservationRepository;
//    }
//
//    @PutMapping("/reservation/{id}")
//    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @RequestBody Reservation updatedReservation) {
//        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
//        if (optionalReservation.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        Reservation reservationToUpdate = optionalReservation.get();
//        reservationToUpdate.setCar(updatedReservation.getCar());
//        reservationToUpdate.setStatus(updatedReservation.getStatus());
//        reservationToUpdate.setCustomer(updatedReservation.getCustomer());
//        return ResponseEntity.ok(reservationRepository.save(reservationToUpdate));
//    }
//}
//    private final ReservationService reservationService;
//
//    public ReservationController(ReservationService reservationService) {
//        this.reservationService = reservationService;
//    }
//
//    @PutMapping("reservation/customer")
//    public ResponseEntity<Reservation> updateReservationCustomer(@RequestParam Long reservationId, @RequestParam Long customerId) {
//        if (reservationService.updateReservationCustomer(reservationId, customerId))
//            return ResponseEntity.ok().build();
//        return ResponseEntity.notFound().build();
//    }
}
