import java.util.*;

/**
 * =============================================================================
 * CLASS - CancellationService
 * =============================================================================
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Description:
 * Manages the cancellation of confirmed bookings and ensures
 * inventory is restored safely using a Stack-based rollback.
 *
 * @version 10.0
 */
class CancellationService {
    // Stack to store recently released reservation IDs (LIFO)
    private Stack<String> releasedReservationIds;

    public CancellationService() {
        this.releasedReservationIds = new Stack<>();
    }

    /**
     * Cancels a booking and restores the inventory.
     * Matches the output format seen in the system logs.
     */
    public void processCancellation(String reservationId, String roomType, RoomInventory inventory) {
        System.out.println("Booking Cancellation");

        // 1. Restore Inventory
        inventory.addRoom(roomType);
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        // 2. Track for Rollback History
        releasedReservationIds.push(reservationId);

        // 3. Show History (Most Recent First)
        System.out.println("\nRollback History (Most Recent First):");
        if (!releasedReservationIds.isEmpty()) {
            System.out.println("Released Reservation ID: " + releasedReservationIds.peek());
        }

        // 4. Show Updated Availability
        System.out.println("\nUpdated " + roomType + " Room Availability: " + inventory.getAvailability(roomType));
    }
}

/**
 * Manages room counts and availability.
 */
class RoomInventory {
    private Map<String, Integer> inventoryMap;

    public RoomInventory() {
        this.inventoryMap = new HashMap<>();
        inventoryMap.put("Single", 5); // Starting count
    }

    public void addRoom(String roomType) {
        inventoryMap.put(roomType, inventoryMap.getOrDefault(roomType, 0) + 1);
    }

    public int getAvailability(String roomType) {
        return inventoryMap.getOrDefault(roomType, 0);
    }
}

/**
 * =============================================================================
 * MAIN CLASS - UseCase10BookingCancellation
 * =============================================================================
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initialize Components
        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        // Perform the cancellation as shown in the screenshot
        cancellationService.processCancellation("Single-1", "Single", inventory);
    }
}