package pl.zdjavapol140.carrental.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.*;
import pl.zdjavapol140.carrental.service.CarService;
import pl.zdjavapol140.carrental.service.ReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Slf4j
@Component
public class DbCreator {

    private final AddressRepository addressRepository;
    private final BranchRepository branchRepository;
    private final CarRepository carRepository;
    private final CarRentRepository carRentRepository;
    private final CarReturnRepository carReturnRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final RentalRepository rentalRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final CarService carService;

    public DbCreator(AddressRepository addressRepository, BranchRepository branchRepository, CarRepository carRepository, CarRentRepository carRentRepository, CarReturnRepository carReturnRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository, ReservationService reservationService, CarService carService) {
        this.addressRepository = addressRepository;
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
        this.carRentRepository = carRentRepository;
        this.carReturnRepository = carReturnRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.carService = carService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertDataToDb() {

//        log.info(String.valueOf(carService.removeCar(4L)));

        List<Address> addresses = new ArrayList<>();
        List<Branch> branches = new ArrayList<>();
        List<Car> cars = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<Employee> employees = new ArrayList<>();
        List<Rental> rentals = new ArrayList<>();
        List<Reservation> reservations = new ArrayList<>();


        /**Fill addressRepository
         *
         */
        int baseNumberOfObjects = 1;
        for (int i = 0; i < baseNumberOfObjects * 72; i++) {
            addresses.add(AddressGenerator.generateRandomAddress());
        }
        addressRepository.saveAll(addresses);


        /**Fill rentalRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 3; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(addresses.size());
            Address address = addresses.get(randomNumber);
            if (rentals.stream().map(Rental::getAddress).toList().contains(address)) {
                i -= 1;
                continue;
            }
            Rental rental = RentalGenerator.generateRandomRental();
            rental.setAddress(address);
            rentals.add(rental);
        }
        rentalRepository.saveAll(rentals);


        /**Fill branchRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 9; i++) {
            Random random = new Random();
            int randomForAddress = random.nextInt(addresses.size());
            int randomForRental = random.nextInt(rentals.size());
            Address address = addresses.get(randomForAddress);

            if (branches.stream().map(Branch::getAddress).toList().contains(address)) {
                i -= 1;
                continue;
            }

            Branch branch = BranchGenerator.generateRandomBranch();
            branch.setAddress(address);
            branch.setRental(rentals.get(randomForRental));
            branches.add(branch);
        }
        branchRepository.saveAll(branches);


        /**Fill carRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 30; i++) {
            Random random = new Random();
            int randomForRental = random.nextInt(rentals.size());
            Rental rental = rentals.get(randomForRental);
            Car car = CarGenerator.generateRandomCar();
            car.setRental(rental);

            cars.add(car);
        }
        carRepository.saveAll(cars);


        /**Fill customerRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 60; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(addresses.size());
            Address address = addresses.get(randomNumber);
            if (customers.stream().map(Customer::getAddress).toList().contains(address)) {
                i -= 1;
                continue;
            }
            customers.add(CustomerGenerator.generateRandomCustomer(address));
        }
        customerRepository.saveAll(customers);


        /**Fill employeeRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 18; i++) {
            Random random = new Random();
            int randomNumber = random.nextInt(branches.size());
            Employee employee = EmployeeGenerator.generateRandomEmployee();
            employee.setBranch(branches.get(randomNumber));
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);

        /**Update branchRepository
         *
         */
        for (Branch branch : branches) {
            branch.setEmployees(employeeRepository.findEmployeesByBranch(branch));
        }
        branchRepository.saveAll(branches);


        /**Update rentalRepository
         *
         */
        for (Rental rental : rentals) {
            rental.setBranches(branchRepository.findBranchesByRental(rental));
        }
        rentalRepository.saveAll(rentals);
        carRepository.saveAll(cars);


        /**Fill reservationRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 180; i++) {
            Random random = new Random();
            int randomForCustomer = random.nextInt(customers.size());
            int randomForCar = random.nextInt(cars.size());

            Car car = carRepository.findAll().get(randomForCar);
            Reservation reservation = ReservationGenerator.generateRandomReservation(customers.get(randomForCustomer), car);

            reservations.add(reservation);

            reservation.setCar(car);

            Reservation lastReservation = reservations.stream().filter(r -> r.getCar().equals(car)).max(Comparator.comparing(Reservation::getDropOffDateTime)).get();
            Reservation firstReservation = reservations.stream().filter(r -> r.getCar().equals(car)).min(Comparator.comparing(Reservation::getPickUpDateTime)).get();

            if (car.getReservations().stream().toList().indexOf(reservation) % 2 != 0) {
                reservation.setPickUpDateTime(lastReservation.getDropOffDateTime().plusDays(3));
                reservation.setDropOffDateTime(lastReservation.getDropOffDateTime().plusDays(random.nextInt(14)));
                reservation.setBookingDate(lastReservation.getDropOffDateTime().minusDays(random.nextInt(10)));
                reservation.setPickUpBranchId(lastReservation.getDropOffBranchId());
                reservation.setDropOffBranchId(car.getRental().getBranches().get(random.nextInt(car.getRental().getBranches().size())).getId());
            }

            if (car.getReservations().stream().toList().indexOf(reservation) % 2 == 0) {
                reservation.setDropOffDateTime(firstReservation.getPickUpDateTime().minusDays(1));
                reservation.setPickUpDateTime(firstReservation.getPickUpDateTime().minusDays(random.nextInt(14)));
                reservation.setBookingDate(reservation.getPickUpDateTime().minusDays(random.nextInt(10)));
                reservation.setDropOffBranchId(firstReservation.getPickUpBranchId());
                reservation.setPickUpBranchId(car.getRental().getBranches().get(random.nextInt(car.getRental().getBranches().size())).getId());
            }

            if (reservation.getDropOffDateTime().isBefore(LocalDateTime.now())) {
                reservation.setStatus(ReservationStatus.COMPLETED);
            }
            if (reservation.getDropOffDateTime().isAfter(LocalDateTime.now()) && reservation.getPickUpDateTime().isBefore(LocalDateTime.now())) {
                reservation.setStatus(ReservationStatus.IN_PROGRESS);
            }
            if (reservation.getPickUpDateTime().isAfter(LocalDateTime.now())) {
                reservation.setStatus(ReservationStatus.SET);
            }

        }
        reservationRepository.saveAll(reservations);


        /**Update carRepository
         *
         */
        for (Car car : cars) {
            car.setReservations(reservationRepository.findReservationsByCarId(car.getId()));
        }
        carRepository.saveAll(cars);


        /**Update customerRepository
         *
         */
        for (Customer customer : customers) {
            customer.setReservations(reservationRepository.findReservationsByCustomerId(customer.getId()));
        }
        customerRepository.saveAll(customers);

    }
}

