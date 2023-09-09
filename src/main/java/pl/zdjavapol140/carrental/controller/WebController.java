package pl.zdjavapol140.carrental.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.service.BranchService;
import pl.zdjavapol140.carrental.service.CarService;
import pl.zdjavapol140.carrental.service.CustomerService;
import pl.zdjavapol140.carrental.service.ReservationService;

import java.time.LocalDateTime;
import java.util.List;

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
        model.addAttribute("car", car);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not found.");
        }
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Customer customer = customerService.findCustomerByEmail(userDetails.getUsername());

            Reservation preReservation = reservationService.createCurrentPreReservation(car, customer, currentPickUpDateTime, currentDropOffDateTime, currentPickUpBranchId, currentDropOffBranchId);
            Branch pickUpBranch = branchService.findBranchById(currentPickUpBranchId);
            Branch dropOffBranch = branchService.findBranchById(currentDropOffBranchId);

            model.addAttribute("preReservation", preReservation);
            model.addAttribute("pickUpBranch", pickUpBranch);
            model.addAttribute("dropOffBranch", dropOffBranch);

            return "preselect-car";
        }
    }
