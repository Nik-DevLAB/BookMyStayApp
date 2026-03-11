package BookMyStay;

import java.io.*;
import java.util.*;

// Reservation class (Serializable for persistence)
class Reservation implements Serializable {

    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}


// System State class containing inventory + booking history
class SystemState implements Serializable {

    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookingHistory;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookingHistory) {
        this.inventory = inventory;
        this.bookingHistory = bookingHistory;
    }
}


// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "booking_state.dat";

    // Save state to file
    public void saveState(SystemState state) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);

            System.out.println("System state saved successfully.");

        } catch (IOException e) {

            System.out.println("Error saving system state.");
        }
    }

    // Load state from file
    public SystemState loadState() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();

            System.out.println("System state restored successfully.");

            return state;

        } catch (FileNotFoundException e) {

            System.out.println("No previous state found. Starting fresh.");

        } catch (Exception e) {

            System.out.println("Error restoring system state.");
        }

        return null;
    }
}


public class UseCaseBookMyStay {

    public static void main(String[] args) {

        PersistenceService persistence = new PersistenceService();

        // Attempt to load previous state
        SystemState state = persistence.loadState();

        Map<String, Integer> inventory;
        List<Reservation> bookingHistory;

        if (state == null) {

            // Initialize fresh state
            inventory = new HashMap<>();
            bookingHistory = new ArrayList<>();

            inventory.put("Standard", 3);
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

        } else {

            inventory = state.inventory;
            bookingHistory = state.bookingHistory;
        }

        // Simulate new bookings
        Reservation r1 = new Reservation("RES101", "Alice", "Standard");
        Reservation r2 = new Reservation("RES102", "Bob", "Deluxe");

        bookingHistory.add(r1);
        bookingHistory.add(r2);

        inventory.put("Standard", inventory.get("Standard") - 1);
        inventory.put("Deluxe", inventory.get("Deluxe") - 1);

        System.out.println("\nCurrent Booking History:");

        for (Reservation r : bookingHistory) {
            r.display();
        }

        System.out.println("\nCurrent Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }

        // Save system state before shutdown
        SystemState newState = new SystemState(inventory, bookingHistory);
        persistence.saveState(newState);
    }
}