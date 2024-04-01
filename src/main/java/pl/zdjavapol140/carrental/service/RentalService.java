package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.Rental;
import pl.zdjavapol140.carrental.repository.RentalRepository;

import java.util.List;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental findRentalById(Long id){
        return rentalRepository.findRentalById(id);
    }

    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    public Rental createNewRentalCompany(String name, String url, Address address, String owner, List<Branch> branches, List<Car> cars) {
        Rental rental = new Rental(null, name, url, address, null, null, null);
        return rentalRepository.save(rental);
    }

    public Rental findRentalByName(String name) {
        return rentalRepository.findByName(name).orElse(null);
    }
}
