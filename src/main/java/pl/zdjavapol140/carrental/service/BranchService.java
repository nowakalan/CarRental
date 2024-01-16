package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Address;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.repository.BranchRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BranchService {

    private final BranchRepository branchRepository;
    

    public List<Branch> getAllBranches() {

        return branchRepository.findAll();
    }


    public BranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }


    public Branch findBranchById(Long id) {

        if (branchRepository.findById(id).isEmpty()) {

            throw new RuntimeException("Branch id not found");
        }
        return branchRepository.findById(id).get();
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
