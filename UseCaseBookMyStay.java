package BookMyStay;
import java.util.*;

    public class UseCaseBookMyStay {

        // Queue to store booking requests (FIFO)
        private Queue<BookingRequest> requestQueue = new LinkedList<>();

        // Inventory for room types
        private Map<String, Integer> inventory = new HashMap<>();

        // Map room type -> allocated room IDs
        private Map<String, Set<String>> allocatedRooms = new HashMap<>();

        // Set to ensure global uniqueness of room IDs
        private Set<String> usedRoomIds = new HashSet<>();


        public UseCaseBookMyStay() {
            // Initial inventory
            inventory.put("Standard", 3);
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);

            allocatedRooms.put("Standard", new HashSet<>());
            allocatedRooms.put("Deluxe", new HashSet<>());
            allocatedRooms.put("Suite", new HashSet<>());
        }

        // Add booking request
        public void addBookingRequest(String guestName, String roomType) {
            requestQueue.add(new BookingRequest(guestName, roomType));
        }

        // Process booking requests
        public void processRequests() {

            while (!requestQueue.isEmpty()) {

                BookingRequest request = requestQueue.poll(); // FIFO
                String roomType = request.roomType;

                System.out.println("\nProcessing request for " + request.guestName + " (" + roomType + ")");

                if (!inventory.containsKey(roomType) || inventory.get(roomType) == 0) {
                    System.out.println("Reservation Failed: No rooms available");
                    continue;
                }

                String roomId = generateRoomId(roomType);

                // Record room ID
                usedRoomIds.add(roomId);
                allocatedRooms.get(roomType).add(roomId);

                // Update inventory
                inventory.put(roomType, inventory.get(roomType) - 1);

                System.out.println("Reservation Confirmed!");
                System.out.println("Assigned Room ID: " + roomId);
            }
        }

        // Generate unique room ID
        private String generateRoomId(String roomType) {

            String roomId;

            do {
                roomId = roomType.substring(0, 2).toUpperCase() + "-" + UUID.randomUUID().toString().substring(0, 5);
            } while (usedRoomIds.contains(roomId));

            return roomId;
        }


        // Booking request model
        static class BookingRequest {
            String guestName;
            String roomType;

            BookingRequest(String guestName, String roomType) {
                this.guestName = guestName;
                this.roomType = roomType;
            }
        }

        public static void main(String[] args) {

            UseCaseBookMyStay service = new UseCaseBookMyStay();

            // Sample requests
            service.addBookingRequest("Nikhil", "Standard");
            service.addBookingRequest("Bob", "Deluxe");
            service.addBookingRequest("Charlie", "Standard");
            service.addBookingRequest("David", "Suite");
            service.addBookingRequest("Eva", "Standard");

            service.processRequests();
        }
    }