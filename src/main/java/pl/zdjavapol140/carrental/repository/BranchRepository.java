package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
