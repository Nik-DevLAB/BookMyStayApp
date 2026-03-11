package BookMyStay;

import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {

    public InvalidBookingException(String message) {
        super(message);
    }
}


// Booking Validator
class InvalidBookingValidator {

    public static void validateRoomType(String roomType, Map<String, Integer> inventory)
            throws InvalidBookingException {

        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }

        if (inventory.get(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for: " + roomType);
        }
    }
}


// Booking Service
class BookingService {

    private Map<String, Integer> inventory = new HashMap<>();

    public BookingService() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 0);
    }

    public void processBooking(String guestName, String roomType) {

        try {

            // Validate input
            InvalidBookingValidator.validateRoomType(roomType, inventory);

            // Update inventory safely
            inventory.put(roomType, inventory.get(roomType) - 1);

            System.out.println("Booking confirmed for " + guestName +
                    " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed: " + e.getMessage());
        }
    }

    public void showInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}


public class UseCaseBookMyStay {

    public static void main(String[] args) {

        BookingService service = new BookingService();

        // Valid booking
        service.processBooking("Alice", "Standard");

        // Invalid room type
        service.processBooking("Bob", "Luxury");

        // Room not available
        service.processBooking("Charlie", "Suite");

        // Another valid booking
        service.processBooking("David", "Deluxe");

        service.showInventory();
    }
}