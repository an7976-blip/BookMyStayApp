import java.util.*;

/**
 * ============================================================
 * BASE CLASS - Room
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * Represents a generic room with basic attributes
 * such as type, size, price, and availability.
 *
 * @author Hari
 * @version 4.1
 */
class Room {

    protected String type;
    protected int size;
    protected double pricePerNight;
    protected boolean available;

    /**
     * Parameterized constructor to initialize room details.
     */
    public Room(String type, int size, double pricePerNight, boolean available) {
        this.type = type;
        this.size = size;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public int getSize() {
        return size;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    /**
     * Displays room details.
     */
    public void display() {
        System.out.println("Room Type: " + type);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + available);
        System.out.println();
    }
}


/**
 * ============================================================
 * CLASS - Suite (Inheritance)
 * ============================================================
 *
 * Represents a Suite room type.
 *
 * Demonstrates inheritance from base Room class.
 *
 * @author Hari
 * @version 4.1
 */
class Suite extends Room {

    public Suite(String type, int size, double pricePerNight, boolean available) {
        super(type, size, pricePerNight, available);
    }
}


/**
 * ============================================================
 * CLASS - RoomInventory
 * ============================================================
 *
 * Description:
 * Stores all room objects using ArrayList.
 *
 * Acts as in-memory database for rooms.
 *
 * @author Hari
 * @version 4.1
 */
class RoomInventory {

    private List<Room> rooms;

    /**
     * Initializes empty room list.
     */
    public RoomInventory() {
        rooms = new ArrayList<>();
    }

    /**
     * Adds a room to inventory.
     */
    public void addRoom(Room room) {
        rooms.add(room);
    }

    /**
     * Returns all rooms.
     */
    public List<Room> getAllRooms() {
        return rooms;
    }
}


/**
 * ============================================================
 * MAIN CLASS - BookMyStayApp
 * ============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * - Allows users to search rooms based on filters
 * - Implements validation using conditions
 * - Uses ArrayList for storage
 *
 * Key Concepts:
 * - ArrayList
 * - Filtering logic
 * - User input handling
 * - Conditional checks
 *
 * @author Hari
 * @version 4.1
 */
public class BookMyStayApp {

    /**
     * Searches rooms based on type, size and price.
     *
     * @param inventory room inventory
     * @param type required room type
     * @param minSize minimum size
     * @param maxPrice maximum price
     * @return list of matching rooms
     */
    public static List<Room> searchAvailableRooms(
            RoomInventory inventory,
            String type,
            int minSize,
            double maxPrice) {

        List<Room> result = new ArrayList<>();

        List<Room> allRooms = inventory.getAllRooms();

        // Filtering logic
        for (Room room : allRooms) {

            if (!room.isAvailable()) continue;

            if (!room.getType().equalsIgnoreCase(type)) continue;

            if (room.getSize() < minSize) continue;

            if (room.getPricePerNight() > maxPrice) continue;

            result.add(room);
        }

        return result;
    }

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Create inventory
        RoomInventory inventory = new RoomInventory();

        // Add sample data
        inventory.addRoom(new Room("Single", 100, 1500.0, true));
        inventory.addRoom(new Room("Double", 200, 2500.0, false));
        inventory.addRoom(new Suite("Suite", 350, 5000.0, true));

        // User input
        System.out.print("Enter room type: ");
        String type = sc.nextLine();

        System.out.print("Enter minimum size: ");
        int minSize = sc.nextInt();

        System.out.print("Enter max price: ");
        double maxPrice = sc.nextDouble();

        // Perform search
        List<Room> results = searchAvailableRooms(inventory, type, minSize, maxPrice);

        // Display results
        System.out.println("\n--- Available Rooms ---\n");

        if (results.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            for (Room room : results) {
                room.display();
            }
        }

        sc.close();
    }
}