import java.util.HashMap;
import java.util.Map;
/**
 * =========================================================================
 * CLASS - RoomInventory
 * =========================================================================
 * Description:
 * This class acts as the single source of truth for room availability.
 */
class RoomInventory {

    /**
     * Key - Room Type name
     * Value - Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory with default values.
     */
    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     */
    private void initializeInventory() {
        // Initializing with 0; counts will be updated in the main class
        roomAvailability.put("Single", 0);
        roomAvailability.put("Double", 0);
        roomAvailability.put("Suite", 0);
    }

    /**
     * Returns the current availability map.
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type.
     */
    public void updateRoomAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

/**
 * =========================================================================
 * MAIN CLASS - UseCase3InventorySetup
 * =========================================================================
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class demonstrates how room availability is managed using a
 * centralized inventory. Room objects (represented here via print statements)
 * provide characteristics while inventory provides counts.
 *
 * @version 3.0
 */
public class BookMyStayApp {

    public static void main(String[] args) {
        // 1. Initialize the Centralized Inventory
        RoomInventory inventory = new RoomInventory();

        // 2. Setup the counts as shown in the requirement terminal output
        inventory.updateRoomAvailability("Single", 5);
        inventory.updateRoomAvailability("Double", 3);
        inventory.updateRoomAvailability("Suite", 2);

        // 3. Display the "Hotel Room Inventory Status"
        System.out.println("Hotel Room Inventory Status");
        System.out.println("---------------------------");

        // Single Room Section
        System.out.println("Single Room:");
        System.out.println("Beds: 1");
        System.out.println("Size: 250 sqft");
        System.out.println("Price per night: 1500.0");
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Single"));
        System.out.println();

        // Double Room Section
        System.out.println("Double Room:");
        System.out.println("Beds: 2");
        System.out.println("Size: 400 sqft");
        System.out.println("Price per night: 2500.0");
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Double"));
        System.out.println();

        // Suite Room Section
        System.out.println("Suite Room:");
        System.out.println("Beds: 3");
        System.out.println("Size: 750 sqft");
        System.out.println("Price per night: 5000.0");
        System.out.println("Available Rooms: " + inventory.getRoomAvailability().get("Suite"));
    }
}