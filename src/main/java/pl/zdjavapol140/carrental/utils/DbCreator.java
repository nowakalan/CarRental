package pl.zdjavapol140.carrental.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.*;
import pl.zdjavapol140.carrental.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    public DbCreator(AddressRepository addressRepository, BranchRepository branchRepository, CarRepository carRepository, CarRentRepository carRentRepository, CarReturnRepository carReturnRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, RentalRepository rentalRepository, ReservationRepository reservationRepository) {
        this.addressRepository = addressRepository;
        this.branchRepository = branchRepository;
        this.carRepository = carRepository;
        this.carRentRepository = carRentRepository;
        this.carReturnRepository = carReturnRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.rentalRepository = rentalRepository;
        this.reservationRepository = reservationRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void insertDataToDb() {

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


        /**Fill reservationRepository
         *
         */
        for (int i = 0; i < baseNumberOfObjects * 180; i++) {
            Random random = new Random();
            int randomForCustomer = random.nextInt(customers.size());
            int randomForCar = random.nextInt(cars.size());
            int randomForStatus = random.nextInt(2);


            List<ReservationStatus> pastReservationStatus = new ArrayList<>();
            pastReservationStatus.add(ReservationStatus.COMPLETED);
            pastReservationStatus.add(ReservationStatus.CANCELED);

            List<ReservationStatus> futureReservationStatus = new ArrayList<>();
            futureReservationStatus.add(ReservationStatus.SET);
            futureReservationStatus.add(ReservationStatus.CANCELED);

            Reservation reservation = ReservationGenerator.generateRandomReservation(customers.get(randomForCustomer), cars.get(randomForCar));

            int randomForBranch = random.nextInt(reservation.getCar().getRental().getBranches().size());


            Car car = cars.get(randomForCar);
            reservation.setCar(car);

            List<Reservation> futureReservations = reservations
                    .stream()
                    .filter(r -> r.getCar().equals(car) && futureReservationStatus.contains(r.getStatus()))
                    .toList();

            List<Reservation> pastReservations = reservations
                    .stream()
                    .filter(r -> r.getCar().equals(car) && r.getStatus().equals(ReservationStatus.COMPLETED))
                    .toList();

            if (!pastReservations.isEmpty()) {
                Reservation previousReservation = pastReservations.stream().max(Comparator.comparing(Reservation::getDropOffDateTime)).get();
                if (!futureReservations.isEmpty()) {
                    Reservation nextReservation = futureReservations.stream().min(Comparator.comparing(Reservation::getPickUpDateTime)).get();
                    if (!previousReservation.getPickUpDateTime().plusDays(3L).isBefore(nextReservation.getPickUpDateTime())) {
                        reservation.setStatus(ReservationStatus.CANCELED);
                        reservation.setPickUpBranchId(reservation.getCar().getRental().getBranches().get(randomForBranch).getId());
                        reservation.setDropOffBranchId(reservation.getCar().getRental().getBranches().get(randomForBranch).getId());
                    } else {
                        reservation.setPickUpDateTime(previousReservation.getDropOffDateTime().plusDays(1L));
                        reservation.setDropOffDateTime(nextReservation.getPickUpDateTime().minusDays(1L));
                        reservation.setPickUpBranchId(previousReservation.getDropOffBranchId());
                        reservation.setDropOffBranchId(nextReservation.getPickUpBranchId());

                        if (reservation.getDropOffDateTime().isBefore(LocalDateTime.of(2023, 8, 30, 8, 0, 0))) {
                            reservation.setStatus(pastReservationStatus.get(randomForStatus));
                        } else if (reservation.getPickUpDateTime().isBefore(LocalDateTime.of(2023, 8, 30, 8, 0, 0))) {
                            reservation.setStatus(ReservationStatus.IN_PROGRESS);
                        } else {
                            reservation.setStatus(ReservationStatus.SET);
                        }
                    }
                }
            }

            reservations.add(reservation);
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
