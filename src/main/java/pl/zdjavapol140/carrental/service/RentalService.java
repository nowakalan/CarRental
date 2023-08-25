package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.repository.BranchRepository;
import pl.zdjavapol140.carrental.repository.RentalRepository;

@Service
@AllArgsConstructor
public class RentalService {

    RentalRepository rentalRepository;
    BranchRepository branchRepository;

}
