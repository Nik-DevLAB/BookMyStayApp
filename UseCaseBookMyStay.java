package BookMyStay;

import java.util.*;

// Reservation class representing a confirmed booking
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println(
                "Reservation ID: " + reservationId +
                        ", Guest: " + guestName +
                        ", Room Type: " + roomType
        );
    }
}


// Booking History class storing confirmed bookings
class BookingHistory {

    private List<Reservation> reservations = new ArrayList<>();

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
        System.out.println("Reservation stored in booking history.");
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}


// Reporting Service
class BookingReportService {

    public void generateReport(List<Reservation> reservations) {

        System.out.println("\n----- Booking History Report -----");

        if (reservations.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        for (Reservation r : reservations) {
            r.displayReservation();
        }

        System.out.println("\nTotal Bookings: " + reservations.size());
    }
}


public class UseCaseBookMyStay {

    public static void main(String[] args) {

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        Reservation r1 = new Reservation("RES101", "Alice", "Standard");
        Reservation r2 = new Reservation("RES102", "Bob", "Deluxe");
        Reservation r3 = new Reservation("RES103", "Charlie", "Suite");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);

        // Admin generates report
        reportService.generateReport(history.getReservations());
    }
}