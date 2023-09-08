package pl.zdjavapol140.carrental.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zdjavapol140.carrental.service.*;

@RestController
@RequestMapping("/api")
public class RestApiController {

    private final CarService carService;
    private final CustomerService customerService;
    private final BranchService branchService;
    private final AddressService addressService;
    private final EmployeeService employeeService;
    private final RentalService rentalService;
    private final ReservationService reservationService;
    private final UserService userService;

    public RestApiController(CarService carService, CustomerService customerService, BranchService branchService, AddressService addressService, EmployeeService employeeService, RentalService rentalService, ReservationService reservationService, UserService userService) {
        this.carService = carService;
        this.customerService = customerService;
        this.branchService = branchService;
        this.addressService = addressService;
        this.employeeService = employeeService;
        this.rentalService = rentalService;
        this.reservationService = reservationService;
        this.userService = userService;
    }

}
