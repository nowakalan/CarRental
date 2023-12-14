package pl.zdjavapol140.carrental.service.reservationManagement;

import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.ReservationStatus;
import pl.zdjavapol140.carrental.service.reservationManagement.ReservationManagerStrategy;
import pl.zdjavapol140.carrental.service.reservationManagement.ReservationWrapper;

@Component
public class StrategyTransferTransfer implements ReservationManagerStrategy {


    @Override
    public boolean isAppropriate(ReservationWrapper reservationWrapper) {

        return reservationWrapper.previousReservation().isPresent() &&
                reservationWrapper.previousReservation().get().getStatus().equals(ReservationStatus.TRANSFER) &&
                reservationWrapper.nextReservation().isPresent() &&
                reservationWrapper.nextReservation().get().getStatus().equals(ReservationStatus.TRANSFER);
    }

    @Override
    public void manageReservations() {

    }
}
