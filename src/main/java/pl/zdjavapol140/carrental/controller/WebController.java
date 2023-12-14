package pl.zdjavapol140.carrental.controller;



import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.*;
import pl.zdjavapol140.carrental.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class WebController {

    private final BranchService branchService;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final CarRepository carRepository;
    private final CustomerService customerService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeService employeeService;
    private final RentalService rentalService;


    public WebController(BranchService branchService, ReservationService reservationService, CarService carService, CustomerService customerService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ReservationRepository reservationRepository, CarRepository carRepository, UserService userService, BCryptPasswordEncoder passwordEncoder, AddressRepository addressRepository, CustomerRepository customerRepository, EmployeeService employeeService, RentalService rentalService) {
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.carService = carService;
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.carRepository = carRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
        this.rentalService = rentalService;
    }


    @GetMapping("/")
    public String index(Model model) {

        List<Branch> allBranches = this.branchService.getAllBranches();
        model.addAttribute("allBranches", allBranches);
        model.addAttribute("selectedRentDetails", new CarSearch());

        return "index";
    }

    @PostMapping("/authenticateUser")
    public String authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");


        return "redirect:/index";
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


        BigDecimal totalPrice = reservationService.calculateReservationPrice(currentPickUpDateTime, currentDropOffDateTime, currentPickUpBranchId, currentDropOffBranchId, car);

        PreReservation preReservation = new PreReservation(
                car,
                customer,
                currentPickUpDateTime,
                currentDropOffDateTime,
                branchService.findBranchById(currentPickUpBranchId),
                branchService.findBranchById(currentDropOffBranchId),
                totalPrice);

//        log.info(preReservation.toString());
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
//        log.info(currentReservation.toString());

//        model.addAttribute("reservationConfirmed", "Reservation confirmed");

        try {
            reservationService.setCurrentReservationAndOptionalTransferReservations(currentReservation);
            model.addAttribute("reservationConfirmed", "Reservation confirmed");

        } catch (Exception e) {
            model.addAttribute("error", "Reservation aborted" + e.getMessage());
        }

        return "confirm-page";

    }

    @GetMapping("/cars")
    public String getCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars-list";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") Long carId){
        carService.deleteCar(carId);
        return "redirect:/cars";
    }

    @GetMapping("/updateCar")
    public String updateCar(@RequestParam("carId") Long carId, Model model){

        Car car = carRepository.findCarById(carId);
        model.addAttribute("car", car);
        return "car-update";
    }

    @PostMapping("/updateCar")
    public String updateCar(@ModelAttribute Car car, Model model) {
        try {
            Car existingCar = carRepository.findCarById(car.getId());

            existingCar.setBrand(car.getBrand());
            existingCar.setModel(car.getModel());
            existingCar.setMileage(car.getMileage());
            existingCar.setPrice(car.getPrice());

            carService.saveCar(existingCar);

            model.addAttribute("carDataUpdatedMessage", "Data has been updated");
            model.addAttribute("car", existingCar);
        } catch (Exception e) {
            model.addAttribute("carDataUpdatedMessage", "Data update failed");
        }

        return "car-update";
    }

    @GetMapping("/createCar")
    public String showCreateCarForm(Model model){

//        List<Branch> allBranches = branchService.getAllBranches();
//        model.addAttribute("allBranches", allBranches);
        List<Rental> allRentals = rentalService.getAllRentals();
        model.addAttribute("allRentals", allRentals);
        model.addAttribute("car", new Car());

        return "create-car";
    }

    @PostMapping("/create-car")
    public String createCar(
            @RequestParam("brand") String brand,
            @RequestParam("model") String model,
            @RequestParam("size") CarSize size,
            @RequestParam("productionYear") Integer productionYear,
            @RequestParam("transmissionType") CarTransmissionType transmissionType,
            @RequestParam("color") String color,
            @RequestParam("mileage") Double mileage,
            @RequestParam("price") BigDecimal price,
            @RequestParam("rental") Long rentalId,
            Model carModel) {

        Rental rental = rentalService.findRentalById(rentalId);

        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setSize(size);
        car.setProductionYear(productionYear);
        car.setTransmissionType(transmissionType);
        car.setColor(color);
        car.setMileage(mileage);
        car.setPrice(price);
        car.setRental(rental);

        carRepository.save(car);

        carModel.addAttribute("carCreatedMessage", "Car has been created");

        return "create-car";
        }

    @PostMapping("/find-car-by-id")
    public String findCarById(@RequestParam Long carId, Model model){
        Car foundCar = carRepository.findCarById(carId);

        if (foundCar !=null) {
            model.addAttribute("cars", List.of(foundCar));
        } else {
            model.addAttribute("carNotFoundErrorMessage", "Car not found");
            model.addAttribute("cars", carService.getAll());
        }
        return "cars-list";
    }

    @GetMapping("/create-customer")
    public String showCreateCustomerForm(Model model) {
        model.addAttribute("user", new User());
        return "create-customer";
    }

    @PostMapping("/create-customer")
    public String createCustomer(
            @RequestParam("password") String password,
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("details") String details,
            Model model) {

        // Sprawdzenie, czy email istnieje w bazie danych
        User existingUser = userRepository.findByEmail(email);
        if (existingUser != null) {
            model.addAttribute("userAlreadyExists", "An account with the provided email address already exists.");
            return "create-customer";
        }

        // Hashowanie hasła przed zapisaniem do bazy danych
        String encodedPassword = passwordEncoder.encode(password);


        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_CUSTOMER);
        userRepository.save(user);


        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setDetails(details);
        addressRepository.save(address);


        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setUser(user);
        customer.setAddress(address);
        customerRepository.save(customer);

        // Ustawienie atrybutu w modelu do wyświetlenia komunikatu
        model.addAttribute("customerAddedMessage", "Your account has been created");

        return "create-customer";
    }

    @GetMapping("/login")
    public String login(Model model){
        if(userService.isUserLoggedIn()){
            model.addAttribute("loggedIn", true);
        } else{
            model.addAttribute("loggedIn", false);
        }

        return "login";
    }



    @GetMapping("/reservations")
    public String getReservations(Model model, Authentication authentication) {

        boolean isAdminOrEmployee = false;
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_EMPLOYEE") || authority.getAuthority().equals("ROLE_ADMIN")) {
                isAdminOrEmployee = true;
                break;
            }
        }

        if (isAdminOrEmployee) {
            List<Reservation> allReservations = reservationService.getAll();
            model.addAttribute("reservations", allReservations);

        } else {
            UserDetails loggedInUser = (UserDetails) authentication.getPrincipal();
            String userEmail = loggedInUser.getUsername();
            Customer customer = customerService.findCustomersByEmail(userEmail);

            if (customer != null) {
                Long customerId = customer.getId();
                List<Reservation> userReservations = reservationService.findReservationsByCustomerId(customerId);
                model.addAttribute("reservations", userReservations);
            }
        }

        return "reservations-list";
    }

    @PostMapping("/deleteReservation")
    public String deleteReservation(@RequestParam("reservationId") Long reservationId){

        reservationService.deleteReservationById(reservationId);

        return "redirect:/reservations";
    }
}

