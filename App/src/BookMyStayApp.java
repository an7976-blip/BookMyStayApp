import java.util.ArrayList;
import java.util.List;

/**
 * --- DATA MODEL ---
 * Represents a single reservation entity to be stored in history.
 */
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

/**
 * Maintains records of confirmed reservations.
 * Provides ordered storage for reporting.
 * @version 8.0
 */
class BookingHistory {
    // List to store confirmed reservations
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        this.confirmedReservations = new ArrayList<>();
    }

    // Method to add a new reservation to history
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    // Method to retrieve all stored reservations for reporting
    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * Service for generating reports from history data.
 * Follows separation of storage and reporting logic.
 */
class BookingReportService {
    /**
     * Use Case 8: Generates a summary report from booking history.
     * @param history The BookingHistory object containing confirmed reservations.
     */
    public void generateReport(BookingHistory history) {
        System.out.println("Booking History and Reporting\n");
        System.out.println("Booking History Report");

        // Iterating through the history to display records
        for (Reservation res : history.getConfirmedReservations()) {
            System.out.println("Guest: " + res.getGuestName() +
                    ", Room Type: " + res.getRoomType());
        }
    }
}

/**
 * Use Case 8: Booking History and Reporting.
 * Application entry point.
 */
public class BookMyStayApp {
    /**
     * Main method to demonstrate the Booking History and Reporting flow.
     */
    public static void main(String[] args) {
        // 1. Initialize the storage and service components
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // 2. Populate the history with the data shown in the report image
        history.addReservation(new Reservation("Abhi", "Single"));
        history.addReservation(new Reservation("Subha", "Double"));
        history.addReservation(new Reservation("Vanmathi", "Suite"));

        // 3. Request the report to be generated
        reportService.generateReport(history);
    }
}