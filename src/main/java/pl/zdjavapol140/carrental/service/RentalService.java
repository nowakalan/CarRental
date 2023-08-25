package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Division;
import pl.zdjavapol140.carrental.model.Rental;
import pl.zdjavapol140.carrental.repository.DivisionRepository;
import pl.zdjavapol140.carrental.repository.RentalRepository;

@Service
@AllArgsConstructor
public class RentalService {

    RentalRepository rentalRepository;
    DivisionRepository divisionRepository;

//    @Transactional
//    public void addDivision(Division division, Long rentalId) {
//        divisionRepository.save(division);
//        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental id not found"));
//        rental.getDivisions().add(division);
//        rentalRepository.save(rental);
//    }
//
//    @Transactional
//    public void deleteDivision(Long divisionId, Long rentalId) {
//        Rental rental = rentalRepository.findById(rentalId).orElseThrow(() -> new RuntimeException("Rental id not found"));
//        Division division = divisionRepository.findById(divisionId).orElseThrow(() -> new RuntimeException("Division id not found"));
//        rental.getDivisions().remove(division);
//        rentalRepository.save(rental);
//    }
}
