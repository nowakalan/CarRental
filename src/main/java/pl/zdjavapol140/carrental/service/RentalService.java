package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Rental;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.RentalRepository;

import java.util.List;

@Service
public class RentalService {

    RentalRepository rentalRepository;
    BranchRepository branchRepository;

    public RentalService(RentalRepository rentalRepository, BranchRepository branchRepository) {
        this.rentalRepository = rentalRepository;
        this.branchRepository = branchRepository;
    }

    public Rental findRentalById(Long id){
        return rentalRepository.findRentalById(id);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }
}
