package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.repository.BranchRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    private final AddressService addressService;
    private final RentalService rentalService;
    

    public List<Branch> getAllBranches() {

        return branchRepository.findAll();
    }


    public BranchService(BranchRepository branchRepository, AddressService addressService, RentalService rentalService) {
        this.branchRepository = branchRepository;
        this.addressService = addressService;
        this.rentalService = rentalService;
    }


    public Branch createNewBranch(Long addressId, Long rentalId) {
        Branch branch = new Branch(
                null,
                addressService.findAddressById(addressId),
                rentalService.findRentalById(rentalId),
                null,
                addressService.findAddressById(addressId).getCity(),
                rentalService.findRentalById(rentalId).getName());

        return branchRepository.save(branch);
    }

    public Branch findBranchById(Long id) {

        if (branchRepository.findById(id).isEmpty()) {

            throw new RuntimeException("Branch id not found");
        }
        return branchRepository.findById(id).get();
    }

    public Branch findBranchByOwnerAndName(String owner, String name) {
        return branchRepository.findBranchByOwnerAndName(owner, name);
    }

    public Address findBranchAddressById(Long id) {

        Optional<Branch> branchOptional = branchRepository.findById(id);

        if (branchOptional.isPresent()) {
            Branch branch = branchOptional.get();
            return branch.getAddress();
        } else {
            return null;
        }
    }
}
