package BookMyStay;

import java.util.*;

// Reservation class
class Reservation {

    private String reservationId;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }
}


// Cancellation Service
class CancellationService {

    // Reservation storage
    private Map<String, Reservation> reservations = new HashMap<>();

    // Inventory
    private Map<String, Integer> inventory = new HashMap<>();

    // Stack for rollback tracking
    private Stack<String> rollbackStack = new Stack<>();


    public CancellationService() {

        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Add reservation (simulate confirmed booking)
    public void addReservation(Reservation reservation) {

        reservations.put(reservation.getReservationId(), reservation);

        inventory.put(
                reservation.getRoomType(),
                inventory.get(reservation.getRoomType()) - 1
        );

        System.out.println("Reservation confirmed: " + reservation.getReservationId());
    }

    // Cancel reservation
    public void cancelReservation(String reservationId) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation res = reservations.get(reservationId);

        if (!res.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Record room ID for rollback
        rollbackStack.push(res.getRoomId());

        // Restore inventory
        inventory.put(
                res.getRoomType(),
                inventory.get(res.getRoomType()) + 1
        );

        // Update reservation state
        res.cancel();

        System.out.println("Reservation cancelled: " + reservationId);
        System.out.println("Room released: " + res.getRoomId());
    }

    public void showInventory() {

        System.out.println("\nCurrent Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }

    public void showRollbackStack() {

        System.out.println("\nRollback Stack (Recently Released Rooms):");

        for (String room : rollbackStack) {
            System.out.println(room);
        }
    }
}


public class UseCaseBookMyStay {

    public static void main(String[] args) {

        CancellationService service = new CancellationService();

        // Simulated confirmed bookings
        Reservation r1 = new Reservation("RES101", "Standard", "ST101");
        Reservation r2 = new Reservation("RES102", "Deluxe", "DL201");

        service.addReservation(r1);
        service.addReservation(r2);

        // Cancellation requests
        service.cancelReservation("RES101");

        // Invalid cancellation
        service.cancelReservation("RES999");

        // Duplicate cancellation
        service.cancelReservation("RES101");

        service.showInventory();
        service.showRollbackStack();
    }
}