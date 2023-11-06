package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.zdjavapol140.carrental.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Admin findAdminByEmail(String email);
}
