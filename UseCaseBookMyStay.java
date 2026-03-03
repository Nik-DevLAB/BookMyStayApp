package BookMyStay;
import java.util.*;

public class UseCaseBookMyStay {

        public static void main(String[] args) {

            System.out.println("======================================");
            System.out.println("      Welcome to Book My Stay");
            System.out.println("      Hotel Booking System v5.1");
            System.out.println("======================================");

            // Initialize booking queue
            BookingRequestQueue requestQueue = new BookingRequestQueue();

            // Simulate incoming booking requests
            requestQueue.addRequest(new Reservation("R001", "Alice", "Single Room"));
            requestQueue.addRequest(new Reservation("R002", "Bob", "Suite Room"));
            requestQueue.addRequest(new Reservation("R003", "Charlie", "Single Room"));
            requestQueue.addRequest(new Reservation("R004", "Diana", "Double Room"));

            // Display queued requests
            requestQueue.displayPendingRequests();

            System.out.println("\nAll booking requests are queued in FIFO order.");
            System.out.println("No inventory changes performed.");
        }
    }


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

        public void display() {
            System.out.println("Reservation ID: " + reservationId);
            System.out.println("Guest Name: " + guestName);
            System.out.println("Requested Room: " + roomType);
            System.out.println("--------------------------------------");
        }
    }


    class BookingRequestQueue {

        private Queue<Reservation> queue;

        public BookingRequestQueue() {
            queue = new LinkedList<>();
        }


        public void addRequest(Reservation reservation) {
            queue.add(reservation);
            System.out.println("Request received for " + reservation.getGuestName()
                    + " → Added to queue.");
        }


        public void displayPendingRequests() {

            System.out.println("\nPending Booking Requests (FIFO Order):\n");

            for (Reservation reservation : queue) {
                reservation.display();
            }
        }
    }



