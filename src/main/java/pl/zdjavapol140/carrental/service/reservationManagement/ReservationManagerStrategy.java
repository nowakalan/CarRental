package pl.zdjavapol140.carrental.service.reservationManagement;

public interface ReservationManagerStrategy {

    boolean isAppropriate(ReservationWrapper reservationWrapper);

    void manageReservations();

}
