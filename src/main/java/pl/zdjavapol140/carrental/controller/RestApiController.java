package pl.zdjavapol140.carrental.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.service.*;

import java.util.List;

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


    @GetMapping("/booking/cars")
    public ResponseEntity<List<Car>> findAvailableCars(BookingCriteria bookingCriteria) {

        var availableCarsWithOptionalAdjacentReservations = reservationService.findAvailableCarsWithOptionalAdjacentReservations(
                bookingCriteria.getCurrentPickUpDateTime(),
                bookingCriteria.getCurrentDropOffDateTime(),
                bookingCriteria.getCurrentPickUpBranchId(),
                bookingCriteria.getCurrentDropOffBranchId(),
                bookingCriteria.getPickUpBranch(),
                bookingCriteria.getDropOffBranch());

        List<Car> cars = reservationService.findAvailableCars(availableCarsWithOptionalAdjacentReservations);

        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping("/booking/selected_car")
    public ResponseEntity<Long> getSelectedCar(@RequestParam(name = "index") int index,
                                               @RequestParam List<Car> cars) {

        return new ResponseEntity<>(cars.get(index).getId(), HttpStatus.OK);
    }

    @GetMapping("/booking/pre_reservation")
    public ResponseEntity<Reservation> getPreReservation(BookingCriteria bookingCriteria,
                                                         @RequestParam Long carId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not found");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        Car car = carService.findCarById(carId);
        Branch pickUpBranch = branchService.findBranchById(bookingCriteria.getCurrentPickUpBranchId());
        Branch dropOffBranch = branchService.findBranchById(bookingCriteria.getCurrentDropOffBranchId());

        Reservation preReservation = reservationService
                .createCurrentPreReservation(
                        car,
                        customer,
                        bookingCriteria.getCurrentPickUpDateTime(),
                        bookingCriteria.getCurrentDropOffDateTime(),
                        bookingCriteria.getCurrentPickUpBranchId(),
                        bookingCriteria.getCurrentDropOffBranchId(),
                        pickUpBranch,
                        dropOffBranch
                        );

        return new ResponseEntity<>(preReservation, HttpStatus.OK);
    }

    @PostMapping("/booking/reservation")
    public ResponseEntity<Reservation> addReservationWithOptionalTransfers(@RequestBody Reservation reservation) {

        if (reservationService.setCurrentReservationAndOptionalTransferReservations(reservation)) {

            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
    }
}
