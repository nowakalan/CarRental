package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.zdjavapol140.carrental.model.Branch;

import java.util.List;
@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    List<Branch> findAll();
}
