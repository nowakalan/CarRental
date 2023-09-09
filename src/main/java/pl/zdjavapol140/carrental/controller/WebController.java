package pl.zdjavapol140.carrental.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.service.BranchService;
import pl.zdjavapol140.carrental.service.CarService;
import pl.zdjavapol140.carrental.service.CustomerService;
import pl.zdjavapol140.carrental.service.ReservationService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class WebController {

    private final BranchService branchService;
    private final ReservationService reservationService;
    private final CarService carService;

    private final CustomerService customerService;

    public WebController(BranchService branchService, ReservationService reservationService, CarService carService, CustomerService customerService) {
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.carService = carService;
        this.customerService = customerService;
    }


    @GetMapping("/")
    public String index(Model model) {

        List<Branch> allBranches = this.branchService.getAllBranches();
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("selectedRentDetails", new CarSearch());

        return "index";
    }


    @GetMapping("/search")
    public String search(@RequestParam LocalDateTime currentPickUpDateTime,
                         @RequestParam LocalDateTime currentDropOffDateTime,
                         @RequestParam Long currentPickUpBranchId,
                         @RequestParam Long currentDropOffBranchId, Model model) {

        var optionalReservations = this.reservationService.findAvailableCarsWithOptionalAdjacentReservations(currentPickUpDateTime, currentDropOffDateTime, currentPickUpBranchId, currentDropOffBranchId);
        List<Car> carList = reservationService.findAvailableCars(optionalReservations);
        model.addAttribute("carList", carList);
        model.addAttribute("currentPickUpDateTime", currentPickUpDateTime);
        model.addAttribute("currentDropOffDateTime", currentDropOffDateTime);
        model.addAttribute("currentPickUpBranchId", currentPickUpBranchId);
        model.addAttribute("currentDropOffBranchId", currentDropOffBranchId);


        return "searchResults";
    }


    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars-list";
    }


    @GetMapping("/preselect-car")

    public String preselectCar(@RequestParam Long carId,
                               @RequestParam LocalDateTime currentPickUpDateTime,
                               @RequestParam LocalDateTime currentDropOffDateTime,
                               @RequestParam Long currentPickUpBranchId,
                               @RequestParam Long currentDropOffBranchId, Model model) {
        Car car = carService.findCarById(carId);
        log.info(car.toString());
        model.addAttribute("car", car);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not found.");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.info(String.valueOf(userDetails));

        Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());
        log.info(customer.toString());

        BigDecimal totalPrice = reservationService.calculateReservationPrice(currentPickUpDateTime, currentDropOffDateTime, currentPickUpBranchId, currentDropOffBranchId, car);

        PreReservation preReservation = new PreReservation(
                car,
                customer,
                currentPickUpDateTime,
                currentDropOffDateTime,
                branchService.findBranchById(currentPickUpBranchId),
                branchService.findBranchById(currentDropOffBranchId),
                totalPrice);

        log.info(preReservation.toString());
        model.addAttribute("preReservation", preReservation);


        return "preselect-car";
    }

    @PostMapping("/confirm-page")
    public String confirmReservation(@ModelAttribute PreReservation preReservation, Model model) {

        Reservation currentReservation = new Reservation();

        currentReservation.setStatus(ReservationStatus.SET);
        currentReservation.setBookingDate(LocalDateTime.now());
        currentReservation.setCustomer(preReservation.getCustomer());
        currentReservation.setCar(preReservation.getCar());
        currentReservation.setPickUpDateTime(preReservation.getPickUpDateTime());
        currentReservation.setPickUpBranchId(preReservation.getPickUpBranch().getId());
        currentReservation.setDropOffDateTime(preReservation.getDropOffDateTime());
        currentReservation.setDropOffBranchId(preReservation.getDropOffBranch().getId());
        currentReservation.setTotalPrice(preReservation.getTotalPrice());
        log.info(currentReservation.toString());

        try {
            reservationService.setCurrentReservationAndOptionalTransferReservations(currentReservation);
            model.addAttribute("message", "Reservation confirmed");

        } catch (Exception e) {
            model.addAttribute("error", "Reservation aborted" + e.getMessage());
        }

        return "confirm-page";
    }
}
