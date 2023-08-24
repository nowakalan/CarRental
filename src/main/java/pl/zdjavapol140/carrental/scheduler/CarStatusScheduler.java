//package pl.zdjavapol140.carrental.scheduler;
//
//import org.springframework.context.annotation.Configuration;
//import pl.zdjavapol140.carrental.model.Car;
//import pl.zdjavapol140.carrental.model.CarStatus;
//import pl.zdjavapol140.carrental.repository.CarRepository;
//import pl.zdjavapol140.carrental.repository.CarRentRepository;
//import pl.zdjavapol140.carrental.repository.ReservationRepository;
//import pl.zdjavapol140.carrental.repository.CarReturnRepository;
//
//import java.time.LocalDateTime;
//
//@Configuration
//public class CarStatusScheduler {
//
//    CarRepository carRepository;
//    ReservationRepository reservationRepository;
//    CarRentRepository carRentRepository;
//    CarReturnRepository carReturnRepository;
//
//    Long minutesBeforeReservationStart;
//    Long minutesAfterReservationEnd;
//
//    public void setCarStatusUnavailable(Long minutesToReservationStart) {
//        LocalDateTime changeStatusDateTime = LocalDateTime.now().minusMinutes(minutesToReservationStart);
//        carRepository.findAllById(reservationRepository.getCarIdsByStartDateTimeBefore(changeStatusDateTime))
//                .forEach(car -> car.setStatus(CarStatus.RESERVED));
//    }
//
//}
