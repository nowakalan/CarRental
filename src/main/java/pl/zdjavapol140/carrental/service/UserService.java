package pl.zdjavapol140.carrental.service;

import org.springframework.stereotype.Service;
import pl.zdjavapol140.carrental.model.Customer;
import pl.zdjavapol140.carrental.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
