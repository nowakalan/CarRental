package pl.zdjavapol140.carrental.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Branch;
import pl.zdjavapol140.carrental.repository.BranchRepository;

import java.util.List;

@Service
public class BranchService {

    BranchRepository branchRepository;

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }
}
