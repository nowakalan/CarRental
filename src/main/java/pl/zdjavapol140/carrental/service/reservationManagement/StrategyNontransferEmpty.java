package pl.zdjavapol140.carrental.service.reservationManagement;

import org.springframework.stereotype.Component;
import pl.zdjavapol140.carrental.model.ReservationStatus;

@Component
public class StrategyNontransferEmpty implements ReservationManagerStrategy {
    @Override
    public boolean isAppropriate(ReservationWrapper reservationWrapper) {

        return  reservationWrapper.previousReservation().isPresent() &&
                !reservationWrapper.previousReservation().get().getStatus().equals(ReservationStatus.TRANSFER) &&
                !reservationWrapper.previousReservation().get().getStatus().equals(ReservationStatus.CANCELED) &&
                reservationWrapper.nextReservation().isEmpty();
    }

    @Override
    public void manageReservations() {

    }
}
