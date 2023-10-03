package pl.zdjavapol140.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.zdjavapol140.carrental.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

   // Optional<User> findByEmail(String email);

//    @Query("SELECT u FROM User u WHERE u.email = :email")
//    User findByEmail(@Param("email") String email);

    User findByEmail(String email);
}
