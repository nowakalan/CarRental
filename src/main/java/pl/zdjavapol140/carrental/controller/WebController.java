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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.AddressRepository;
import pl.zdjavapol140.carrental.repository.CustomerRepository;
import pl.zdjavapol140.carrental.repository.ReservationRepository;
import pl.zdjavapol140.carrental.repository.UserRepository;
import pl.zdjavapol140.carrental.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Slf4j
@Controller
public class WebController {

    private final BranchService branchService;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;
    private final CarService carService;
    private final CustomerService customerService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeService employeeService;


    public WebController(BranchService branchService, ReservationService reservationService, CarService carService, CustomerService customerService, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ReservationRepository reservationRepository, UserService userService, BCryptPasswordEncoder passwordEncoder, AddressRepository addressRepository, CustomerRepository customerRepository, EmployeeService employeeService) {
        this.branchService = branchService;
        this.reservationService = reservationService;
        this.carService = carService;
        this.customerService = customerService;
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.customerRepository = customerRepository;
        this.employeeService = employeeService;
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

//        model.addAttribute("reservationConfirmed", "Reservation confirmed");

        try {
            reservationService.setCurrentReservationAndOptionalTransferReservations(currentReservation);
            model.addAttribute("reservationConfirmed", "Reservation confirmed");

        } catch (Exception e) {
            model.addAttribute("error", "Reservation aborted" + e.getMessage());
        }


        return "confirm-page";

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

        // Tworzenie i zapisanie użytkownika
        User user = new User();
        user.setEmail(email);
        user.setPassword(encodedPassword);
        user.setRole(Role.ROLE_CUSTOMER);
        userRepository.save(user);

        // Tworzenie i zapisanie adresu
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setDetails(details);
        addressRepository.save(address);

        // Tworzenie i zapisanie klienta z odniesieniem do użytkownika i adresu
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
    public String loginPage() {

        return "custom-login";
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


}
