import java.util.*;

/**
 * ============================================================
 * CLASS - Reservation
 * ============================================================
 *
 * Use Case 7: Add-On Services for Reservations
 *
 * Description:
 * Represents a hotel reservation made by a guest.
 * Stores guest details and assigned room ID.
 *
 * @author Hari
 * @version 7.0
 */
class Reservation {

    private String guestName;
    private String roomType;
    private String roomId;

    /**
     * Constructor to initialize reservation details.
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    /**
     * Displays reservation details.
     */
    public void displayReservation() {
        System.out.println("Guest: " + guestName +
                ", Room Type: " + roomType +
                ", Room ID: " + roomId);
    }
}


/**
 * ============================================================
 * CLASS - Service
 * ============================================================
 *
 * Description:
 * Represents an add-on service (e.g., Breakfast, Spa).
 *
 * Each service has a name and cost.
 *
 * @author Hari
 * @version 7.0
 */
class Service {

    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    /**
     * Displays service details.
     */
    public void displayService() {
        System.out.println("Service: " + serviceName + ", Cost: " + cost);
    }
}


/**
 * ============================================================
 * CLASS - AddOnServiceManager
 * ============================================================
 *
 * Description:
 * Manages add-on services linked to reservations.
 *
 * Uses HashMap:
 * Key   -> Room ID
 * Value -> List of services
 *
 * Demonstrates:
 * - Map + ArrayList usage
 * - One-to-many relationship
 *
 * @author Hari
 * @version 7.0
 */
class AddOnServiceManager {

    private Map<String, List<Service>> reservationServices;

    /**
     * Initializes service manager.
     */
    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /**
     * Adds a service to a reservation.
     */
    public void addService(String roomId, Service service) {
        reservationServices.putIfAbsent(roomId, new ArrayList<>());
        reservationServices.get(roomId).add(service);

        System.out.println("Added service " + service.getServiceName()
                + " to reservation " + roomId);
    }

    /**
     * Displays all services for a reservation.
     */
    public void displayServices(String roomId) {

        List<Service> services =
                reservationServices.getOrDefault(roomId, new ArrayList<>());

        if (services.isEmpty()) {
            System.out.println("No add-on services selected for reservation " + roomId);
        } else {
            System.out.println("\n--- Add-On Services for Reservation " + roomId + " ---");

            double totalCost = 0;

            for (Service s : services) {
                s.displayService();
                totalCost += s.getCost();
            }

            System.out.println("Total Add-On Cost: " + totalCost);
        }
    }
}


/**
 * ============================================================
 * MAIN CLASS - BookMyStayApp
 * ============================================================
 *
 * Use Case 7: Add-On Services Management
 *
 * Description:
 * - Demonstrates adding services to a reservation
 * - Shows mapping between reservation and services
 * - Calculates total add-on cost
 *
 * Key Concepts:
 * - HashMap
 * - ArrayList
 * - Object Mapping (1-to-many)
 *
 * @author Hari
 * @version 7.0
 */
public class BookMyStayApp {

    /**
     * Application entry point.
     */
    public static void main(String[] args) {

        System.out.println("Welcome to Book My Stay App");
        System.out.println("Hotel Booking System v7.0\n");

        // Create reservation
        Reservation reservation = new Reservation("Alice", "Single Room");
        reservation.setRoomId("S-12");
        reservation.displayReservation();

        // Create services
        Service breakfast = new Service("Breakfast", 200);
        Service spa = new Service("Spa Session", 500);
        Service airportPickup = new Service("Airport Pickup", 300);

        // Service manager
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Add services
        serviceManager.addService(reservation.getRoomId(), breakfast);
        serviceManager.addService(reservation.getRoomId(), spa);
        serviceManager.addService(reservation.getRoomId(), airportPickup);

        // Display services
        serviceManager.displayServices(reservation.getRoomId());
    }
}