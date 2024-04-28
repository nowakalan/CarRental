package pl.zdjavapol140.carrental.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.Car;
import pl.zdjavapol140.carrental.model.Rental;
import pl.zdjavapol140.carrental.repository.RentalRepository;
import pl.zdjavapol140.carrental.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static pl.zdjavapol140.carrental.model.Job.EMPLOYEE;
import static pl.zdjavapol140.carrental.utils.CarGenerator.generateCarByType;

@Slf4j
@Component
public class DbCreatorByAlan {

    private final AddressService addressService;
    private final RentalService rentalService;
    private final RentalRepository rentalRepository;
    private final BranchService branchService;
    private final EmployeeService employeeService;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final CarService carService;


    public DbCreatorByAlan(AddressService addressService, RentalService rentalService, RentalRepository rentalRepository, BranchService branchService, EmployeeService employeeService, AdminService adminService, CustomerService customerService, CarService carService) {
        this.addressService = addressService;
        this.rentalService = rentalService;
        this.rentalRepository = rentalRepository;
        this.branchService = branchService;

        this.employeeService = employeeService;
        this.adminService = adminService;
        this.customerService = customerService;
        this.carService = carService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertCorrectDataToDatabase(){


        Random random = new Random();
        int baseNumberOfObjects = 1;


        /**
        Fill addressRepository
        **/
        List<String[]> addressData = List.of(
                new String[]{"Poland", "Warszawa", "00-000", "Krakowska 100"},
                new String[]{"Poland", "Krakow", "10-000", "Warszawska 1"},
                new String[]{"Poland", "Gdansk", "20-000", "Szczecinsk 2"},
                new String[]{"Poland", "Poznan", "30-000", "Wroclawska 1"},
                new String[]{"Poland", "Wroclaw", "11-111", "Poznanska 2"},
                new String[]{"Poland", "Katowice", "50-000", "Chorzowska 5"},
                new String[]{"Norway", "Oslo", "1020", "Kongsgate 5"},
                new String[]{"Norway", "Bergen", "5060", "Gaupåsvegen 300"},
                new String[]{"Norway", "Kristiansand", "3030", "Marviksveien 5"},
                new String[]{"Norway", "Stavanger", "2020", "Parkveien 1"}
        );

        for (String[] address : addressData) {
            String country = address[0];
            String city = address[1];
            String postalCode = address[2];
            String street = address[3];

            if (addressService.findByCountryAndCityAndPostalCodeAndDetails(country, city, postalCode, street) == null) {
                addressService.createNewAddress(country, city, postalCode, street);
            }
        }



        /**
        * Fill rentalRepository
         **/
        if (rentalService.findRentalByName("Sunny Car Rental Poland") == null) {
            rentalService.createNewRentalCompany("Sunny Car Rental Poland", "https://www.sunnycarrental.com", addressService.findByCountryAndCityAndPostalCodeAndDetails("Poland", "Warszawa", "00-000", "Krakowska 100"), null, null, null);
        }
        if (rentalService.findRentalByName("Easy Drive Car Rental Norway") == null) {
            rentalService.createNewRentalCompany("Easy Drive Car Rental Norway", "https://www.easydrive.com", addressService.findByCountryAndCityAndPostalCodeAndDetails("Norway", "Oslo", "1020", "Kongsgate 5"), null, null, null);
        }

        List<Rental> rentals = rentalService.getAllRentals();



        /**
        * Fill branchRepository
         **/

        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Warszawa") == null ){
             branchService.createNewBranch(1L, 1L);
         }
        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Krakow") == null ){
            branchService.createNewBranch(2L, 1L);
        }
        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Gdansk") == null ){
            branchService.createNewBranch(3L, 1L);
        }
        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Poznan") == null ){
            branchService.createNewBranch(4L, 1L);
        }
        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Wroclaw") == null ){
            branchService.createNewBranch(5L, 1L);
        }
        if (branchService.findBranchByOwnerAndName("Sunny Car Rental Poland", "Katowice") == null ){
            branchService.createNewBranch(6L, 1L);
        }
        if (branchService.findBranchByOwnerAndName("Easy Drive Car Rental Norway", "Oslo") == null ){
            branchService.createNewBranch(7L, 2L);
        }
        if (branchService.findBranchByOwnerAndName("Easy Drive Car Rental Norway", "Bergen") == null ){
            branchService.createNewBranch(8L, 2L);
        }
        if (branchService.findBranchByOwnerAndName("Easy Drive Car Rental Norway", "Kristiansand") == null ){
            branchService.createNewBranch(9L, 2L);
        }
        if (branchService.findBranchByOwnerAndName("Easy Drive Car Rental Norway", "Stavanger") == null ){
            branchService.createNewBranch(10L, 2L);
        }

        /**Fill carRepository
        *
        */
        if (carService.getAll().size() < 50) {
            String[] carTypes = {"small", "economy", "compact", "estates", "suv"};

            for (String carType : carTypes) {
                for (int i = 0; i < baseNumberOfObjects * 10; i++) {
                    Car car = generateCarByType(carType);

                    int randomForRental = random.nextInt(rentals.size());
                    Rental rental = rentals.get(randomForRental);
                    car.setRental(rental);
                    carService.addCar(car);
                }
            }
        }


        /**
         Fill userRepository
         **/
        if (employeeService.findEmployeeByEmail("employee@gmail.com") == null) {
            employeeService.addNewEmployee("employee", "employee", "employee@gmail.com", EMPLOYEE, null);
        }
        if (adminService.findAdminByEmail("admin@gmail.com") == null) {
            adminService.addNewAdmin("admin", "admin", "admin@gmail.com");
        }
        if (customerService.findCustomerByEmail("customer@gmail.com") == null) {
            customerService.addNewCustomer("customer", "customer", "customer@gmail.com", null);
        }

    }
}
