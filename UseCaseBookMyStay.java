package BookMyStay;
import java.util.HashMap;
import java.util.Map;

public class UseCaseBookMyStay {

        public static void main(String[] args) {

            System.out.println("======================================");
            System.out.println("      Welcome to Book My Stay");
            System.out.println("      Hotel Booking System v4.1");
            System.out.println("======================================");

            // Initialize room domain objects
            Room single = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suite = new SuiteRoom();

            // Initialize centralized inventory
            RoomInventory inventory = new RoomInventory();
            inventory.addRoomType(single.getRoomType(), 3);
            inventory.addRoomType(doubleRoom.getRoomType(), 0); // Unavailable
            inventory.addRoomType(suite.getRoomType(), 2);

            // Initialize search service
            SearchService searchService = new SearchService(inventory);

            // Perform room search
            searchService.displayAvailableRooms(single, doubleRoom, suite);

            System.out.println("\nSearch completed. Inventory unchanged.");
        }
    }



    abstract class Room {

        private String roomType;
        private int beds;
        private double size;
        private double price;

        public Room(String roomType, int beds, double size, double price) {
            this.roomType = roomType;
            this.beds = beds;
            this.size = size;
            this.price = price;
        }

        public String getRoomType() {
            return roomType;
        }

        public void displayDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + beds);
            System.out.println("Size: " + size + " sq ft");
            System.out.println("Price per Night: $" + price);
        }
    }

    class SingleRoom extends Room {
        public SingleRoom() {
            super("Single Room", 1, 180.0, 120.0);
        }
    }

    class DoubleRoom extends Room {
        public DoubleRoom() {
            super("Double Room", 2, 250.0, 200.0);
        }
    }

    class SuiteRoom extends Room {
        public SuiteRoom() {
            super("Suite Room", 3, 450.0, 450.0);
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
    }



    class SearchService {

        private RoomInventory inventory;

        public SearchService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        public void displayAvailableRooms(Room... rooms) {

            System.out.println("\nAvailable Rooms:\n");

            for (Room room : rooms) {

                int available = inventory.getAvailability(room.getRoomType());

                // Defensive check: only show available rooms
                if (available > 0) {
                    room.displayDetails();
                    System.out.println("Available Units: " + available);
                    System.out.println("--------------------------------------");
                }
            }
        }
    }



