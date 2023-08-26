package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
