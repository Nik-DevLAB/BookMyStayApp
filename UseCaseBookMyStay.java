package BookMyStay;

public class UseCaseBookMyStay {


        public static void main(String[] args) {

            System.out.println("======================================");
            System.out.println("      Welcome to Book My Stay");
            System.out.println("      Hotel Booking System v2.1");
            System.out.println("======================================");

            // Create room objects using polymorphism
            Room singleRoom = new SingleRoom();
            Room doubleRoom = new DoubleRoom();
            Room suiteRoom = new SuiteRoom();

            // Static availability representation
            int singleRoomAvailability = 10;
            int doubleRoomAvailability = 5;
            int suiteRoomAvailability = 2;

            // Display room details
            singleRoom.displayDetails();
            System.out.println("Available Units: " + singleRoomAvailability);
            System.out.println("--------------------------------------");

            doubleRoom.displayDetails();
            System.out.println("Available Units: " + doubleRoomAvailability);
            System.out.println("--------------------------------------");

            suiteRoom.displayDetails();
            System.out.println("Available Units: " + suiteRoomAvailability);
            System.out.println("--------------------------------------");

            System.out.println("Application terminated successfully.");
        }
    }


    /**
     * Abstract Room class defining common structure
     */
    abstract class Room {

        // Encapsulated attributes
        private String roomType;
        private int numberOfBeds;
        private double roomSize;
        private double pricePerNight;

        public Room(String roomType, int numberOfBeds, double roomSize, double pricePerNight) {
            this.roomType = roomType;
            this.numberOfBeds = numberOfBeds;
            this.roomSize = roomSize;
            this.pricePerNight = pricePerNight;
        }

        // Common method for displaying room details
        public void displayDetails() {
            System.out.println("Room Type: " + roomType);
            System.out.println("Beds: " + numberOfBeds);
            System.out.println("Size: " + roomSize + " sq ft");
            System.out.println("Price per Night: $" + pricePerNight);
        }
    }


    /**
     * Concrete Single Room implementation
     */
    class SingleRoom extends Room {

        public SingleRoom() {
            super("Single Room", 1, 180.0, 120.0);
        }
    }


    /**
     * Concrete Double Room implementation
     */
    class DoubleRoom extends Room {

        public DoubleRoom() {
            super("Double Room", 2, 250.0, 200.0);
        }
    }


    /**
     * Concrete Suite Room implementation
     */
    class SuiteRoom extends Room {

        public SuiteRoom() {
            super("Suite Room", 3, 450.0, 450.0);
        }
    }


