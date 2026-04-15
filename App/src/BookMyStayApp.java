import java.util.*;

/**
 * =============================================================================
 * CLASS - ConcurrentBookingProcessor
 * =============================================================================
 * Use Case 11: Concurrent Booking Processing
 *
 * Description:
 * Implements Runnable to allow multiple threads to process
 * a shared booking queue simultaneously.
 *
 * @version 11.0
 */
class ConcurrentBookingProcessor implements Runnable {
    private final Queue<String> bookingQueue;
    private final RoomInventory inventory;
    private final RoomAllocationService allocationService;

    public ConcurrentBookingProcessor(Queue<String> queue, RoomInventory inventory, RoomAllocationService service) {
        this.bookingQueue = queue;
        this.inventory = inventory;
        this.allocationService = service;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " started processing...");

        while (true) {
            String request = null;

            // 1. Synchronize access to the shared queue (Critical Section)
            synchronized (bookingQueue) {
                if (bookingQueue.isEmpty()) break;
                request = bookingQueue.poll();
            }

            if (request != null) {
                // 2. Synchronize access to shared inventory during allocation
                synchronized (inventory) {
                    allocationService.allocate(request, inventory);
                }
            }

            // Artificial delay to simulate processing time
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " was interrupted.");
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + " execution finished.");
    }
}

/**
 * Shared Inventory Management
 */
class RoomInventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public RoomInventory() {
        rooms.put("Single", 5);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    public boolean hasRooms(String type) { return rooms.getOrDefault(type, 0) > 0; }

    public void decrement(String type) {
        rooms.put(type, rooms.get(type) - 1);
    }

    public void displayFinalInventory() {
        System.out.println("\nRemaining Inventory:");
        rooms.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}

/**
 * Service to handle the logic of room allocation
 */
class RoomAllocationService {
    public void allocate(String request, RoomInventory inventory) {
        // Extract room type from request (e.g., "Guest: Abhi, Type: Single")
        String type = request.split(":")[1].trim();
        String guest = request.split(":")[0].trim();

        if (inventory.hasRooms(type)) {
            inventory.decrement(type);
            System.out.println("Booking confirmed for Guest: " + guest + ", Room Type: " + type);
        } else {
            System.out.println("Booking failed for Guest: " + guest + " | " + type + " out of stock.");
        }
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase11ConcurrentSimulation
 * =============================================================================
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation\n");

        // Initialize shared resources
        Queue<String> bookingQueue = new LinkedList<>(Arrays.asList(
                "Abhi: Single", "Vanmathi: Double", "Kural: Suite", "Subha: Single"
        ));
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();

        // Create threads (As seen in UC11 Main Class image)
        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService),
                "Thread-1"
        );

        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService),
                "Thread-2"
        );

        // Start concurrent processing
        t1.start();
        t2.start();

        // Join threads and handle potential interruptions
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted.");
        }

        // Display final counts as per the output image
        inventory.displayFinalInventory();
    }
}