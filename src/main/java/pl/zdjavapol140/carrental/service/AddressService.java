package pl.zdjavapol140.carrental.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.repository.AddressRepository;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address createNewAddress(String country, String city, String postalCode, String details) {
        Address address = new Address(null, country, city, postalCode, details);
        return addressRepository.save(address);
    }

    @Transactional
    public Long addAddress(Address address) {

        addressRepository.save(address);
        return address.getId();
    }

    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address findAddressById(Long id) {

        return addressRepository.findById(id).orElseThrow(() -> new RuntimeException("Address id not found"));
    }


    public Address findByCountryAndCityAndPostalCodeAndDetails(String country, String city, String postalCode, String details) {
        return addressRepository.findByCountryAndCityAndPostalCodeAndDetails(country, city, postalCode, details);
    }
}
