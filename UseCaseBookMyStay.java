package BookMyStay;
import java.util.HashMap;
import java.util.Map;

public class UseCaseBookMyStay {

        public static void main(String[] args) {

            System.out.println("======================================");
            System.out.println("      Welcome to Book My Stay");
            System.out.println("      Hotel Booking System v3.1");
            System.out.println("======================================");

            // Initialize inventory
            RoomInventory inventory = new RoomInventory();

            // Register room types with availability
            inventory.addRoomType("Single Room", 10);
            inventory.addRoomType("Double Room", 5);
            inventory.addRoomType("Suite Room", 2);

            // Display current inventory
            inventory.displayInventory();

            // Simulate update
            System.out.println("\nBooking 1 Single Room...");
            inventory.updateAvailability("Single Room", -1);

            System.out.println("\nUpdated Inventory:");
            inventory.displayInventory();

            System.out.println("\nApplication terminated successfully.");
        }
    }



    class RoomInventory {

        private Map<String, Integer> inventory;


        public RoomInventory() {
            inventory = new HashMap<>();
        }


        public void addRoomType(String roomType, int count) {
            inventory.put(roomType, count);
        }


        public int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }


        public void updateAvailability(String roomType, int change) {
            if (inventory.containsKey(roomType)) {

                int current = inventory.get(roomType);
                int updated = current + change;

                if (updated < 0) {
                    System.out.println("Error: Cannot reduce below zero.");
                    return;
                }

                inventory.put(roomType, updated);
            } else {
                System.out.println("Room type not found.");
            }
        }


        public void displayInventory() {
            System.out.println("\nCurrent Room Inventory:");
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                System.out.println(entry.getKey() + " → Available: " + entry.getValue());
            }
        }
    }



