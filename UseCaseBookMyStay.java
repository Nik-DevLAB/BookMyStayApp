package BookMyStay;

import java.util.*;

// Booking Request Class
class BookingRequest {

    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}


// Concurrent Booking Processor
class ConcurrentBookingProcessor {

    private Queue<BookingRequest> bookingQueue = new LinkedList<>();
    private Map<String, Integer> inventory = new HashMap<>();

    public ConcurrentBookingProcessor() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Add booking request to queue
    public synchronized void addBookingRequest(BookingRequest request) {
        bookingQueue.add(request);
        System.out.println(request.guestName + " requested " + request.roomType);
    }

    // Process booking requests safely
    public void processBookings() {

        while (true) {

            BookingRequest request;

            synchronized (this) {

                if (bookingQueue.isEmpty()) {
                    return;
                }

                request = bookingQueue.poll();
            }

            allocateRoom(request);
        }
    }

    // Critical section for allocation
    private void allocateRoom(BookingRequest request) {

        synchronized (this) {

            int available = inventory.getOrDefault(request.roomType, 0);

            if (available > 0) {

                inventory.put(request.roomType, available - 1);

                System.out.println(
                        Thread.currentThread().getName() +
                                " allocated " + request.roomType +
                                " to " + request.guestName
                );

            } else {

                System.out.println(
                        Thread.currentThread().getName() +
                                " failed booking for " + request.guestName +
                                " (No rooms available)"
                );
            }
        }
    }

    public void showInventory() {

        System.out.println("\nFinal Inventory:");

        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}


public class UseCaseBookMyStay {

    public static void main(String[] args) {

        ConcurrentBookingProcessor processor = new ConcurrentBookingProcessor();

        // Simulating multiple booking requests
        processor.addBookingRequest(new BookingRequest("Alice", "Standard"));
        processor.addBookingRequest(new BookingRequest("Bob", "Standard"));
        processor.addBookingRequest(new BookingRequest("Charlie", "Deluxe"));
        processor.addBookingRequest(new BookingRequest("David", "Suite"));
        processor.addBookingRequest(new BookingRequest("Eva", "Standard"));

        // Multiple threads processing bookings
        Thread t1 = new Thread(() -> processor.processBookings(), "Thread-1");
        Thread t2 = new Thread(() -> processor.processBookings(), "Thread-2");
        Thread t3 = new Thread(() -> processor.processBookings(), "Thread-3");

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        processor.showInventory();
    }
}