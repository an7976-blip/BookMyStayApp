mport java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ==============================================================
 * CLASS - InvalidBookingException
 * ==============================================================
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * This custom exception represents invalid booking scenarios.
 * Using a domain-specific exception makes error handling clearer.
 *
 * @version 9.0
 */
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

/**
 * Entity representing a single booking record.
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
 * Use Case 8: Maintains records of confirmed reservations.
 * Provides ordered storage for reporting.
 * @version 8.0
 */
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        this.confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }

    public List<Reservation> getConfirmedReservations() {
        return confirmedReservations;
    }
}

/**
 * ==============================================================
 * CLASS - ReservationValidator
 * ==============================================================
 * Use Case 9: Error Handling & Validation
 *
 * Description:
 * This class is responsible for validating booking requests.
 */
class ReservationValidator {
    public void validate(String guestName, String roomType) throws InvalidBookingException {
        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty.");
        }

        // Strict Validation: Matches the "Invalid room type selected" error from image
        if (!roomType.equals("Single") && !roomType.equals("Double") && !roomType.equals("Suite")) {
            throw new InvalidBookingException("Invalid room type selected.");
        }
    }
}

/**
 * Service for generating reports from history data.
 */
class BookingReportService {
    public void generateReport(BookingHistory history) {
        System.out.println("\nBooking History and Reporting");
        System.out.println("-----------------------------");
        List<Reservation> records = history.getConfirmedReservations();

        if (records.isEmpty()) {
            System.out.println("No confirmed bookings found.");
        } else {
            for (Reservation res : records) {
                System.out.println("Guest: " + res.getGuestName() + ", Room Type: " + res.getRoomType());
            }
        }
    }
}

/**
 * ==============================================================
 * MAIN CLASS - HotelBookingSystem
 * ==============================================================
 * Use Case 9: Application entry point with try-catch-finally.
 */
public class BookMyStayApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize Components
        BookingHistory history = new BookingHistory();
        ReservationValidator validator = new ReservationValidator();
        BookingReportService reportService = new BookingReportService();

        System.out.println("Booking Validation");

        try {
            // Interactive User Input
            System.out.print("Enter guest name: ");
            String name = scanner.nextLine();

            System.out.print("Enter room type (Single/Double/Suite): ");
            String type = scanner.nextLine();

            // Validate Input
            validator.validate(name, type);

            // Process and Store if valid
            history.addReservation(new Reservation(name, type));
            System.out.println("Booking processed successfully!");

        } catch (InvalidBookingException e) {
            // Handle validation failures specifically
            System.out.println("Booking failed: " + e.getMessage());
        } finally {
            // Ensure resource closure
            scanner.close();
        }

        // Generate the final history report
        reportService.generateReport(history);
    }
}