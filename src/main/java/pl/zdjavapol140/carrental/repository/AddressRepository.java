package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByCountryAndCityAndPostalCodeAndDetails(String country, String city, String postalCode, String details);

}
