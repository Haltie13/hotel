package pl.edu.agh.kis.pz1.structure;

import org.apache.commons.csv.CSVRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.*;

/**
 * Represents a hotel room. This class manages room details such as room number, pricing,
 * occupancy status, guests, check-in date, and planned check-out date. It provides functionality
 * to check guests in and out, with automatic logging of significant actions, such as failed check-ins
 * due to the room already being occupied, or check-outs with calculation of the total stay cost.
 *
 * @author Jakub
 * @version 1.1
 */
public class Room {
    private int roomNumber;
    private BigDecimal price;
    private final int capacity;
    private List<Guest> roomGuests = new ArrayList<>();
    private LocalDate checkInDate;
    private LocalDate plannedCheckOutDate; // Added field
    private boolean isOccupied;
    private static final Logger logger = Logger.getLogger(Room.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;


    /**
     * Constructs a Room instance with the specified room number, price, and capacity.
     * The room is initially not occupied.
     *
     * @param roomNumber The unique number assigned to the room.
     * @param price      The cost per day of staying in the room.
     * @param capacity   The maximum number of guests the room can accommodate.
     */
    public Room(int roomNumber, BigDecimal price, int capacity) {
        this.roomNumber = roomNumber;
        this.price = price;
        isOccupied = false;
        this.capacity = capacity;
    }

    /**
     * Checks in specified list of guests. The first guest in the list is considered the main guest.
     * If the room is already occupied, logs a warning. If the check-in date is not provided,
     * the current date is used as the check-in date. The planned check-out date is calculated
     * based on the duration of stay provided.
     *
     * @param guests      The list of guests checking into the room.
     * @param checkInDate The date on which the guests are checking in; if null, today's date is used.
     * @param duration    The planned duration of the stay in days.
     * @return            Returns true if checking in was successful; false otherwise.
     */
    public boolean checkIn(List<Guest> guests, LocalDate checkInDate, int duration) {
        if (isOccupied) {
            logger.log(Level.WARNING, String.format("Attempt to check in to an occupied room: %d", this.roomNumber));
            return false;
        } else {
            if (checkInDate == null) {
                this.checkInDate = LocalDate.now();
            } else {
                this.checkInDate = checkInDate;
            }
            if (guests == null || guests.isEmpty()) {
                logger.log(Level.WARNING, String.format("Attempt to check in with null or empty guest list: %d", this.roomNumber));
                return false;
            } else {
                if (duration <= 0) {
                    logger.log(Level.WARNING, String.format("Invalid duration provided for check-in: %d days.", duration));
                    return false;
                }
                this.roomGuests = guests;
                isOccupied = true;
                this.plannedCheckOutDate = this.checkInDate.plusDays(duration); // Set planned check-out date
                logger.log(Level.INFO, String.format("Room %d is now occupied until %s.", this.roomNumber, this.plannedCheckOutDate));
                return true;
            }
        }
    }

    /**
     * Checks out the guests from the room and calculates the total cost based on the number of days stayed.
     * If the room is not occupied, logs a warning and returns a zero value. Otherwise, logs the check-out
     * event with the total days stayed and the total price calculated. Resets the room's occupancy status.
     *
     * @return The total cost for the duration of the stay, or 'BigDecimal.ZERO' if no one was checked in.
     */
    public BigDecimal checkOut() {
        if (!isOccupied) {
            logger.log(Level.WARNING, String.format("Attempt to check out a room that is not occupied: %d", this.roomNumber));
            return BigDecimal.ZERO;
        } else {
            int daysStayed = (int) ChronoUnit.DAYS.between(checkInDate, LocalDate.now());
            BigDecimal totalPrice = price.multiply(new BigDecimal(daysStayed));
            logger.log(Level.INFO, String.format("Room %d is checked out. Total days: %d Total price: %s", this.roomNumber, daysStayed, totalPrice));
            isOccupied = false;
            roomGuests.clear();
            checkInDate = null;
            plannedCheckOutDate = null; // Reset planned check-out date
            return totalPrice;
        }
    }

    public int getCurrentStayDuration() {
        return (int) ChronoUnit.DAYS.between(checkInDate, LocalDate.now());
    }

    /**
     * Converts the Room object into a list of values suitable for CSV output.
     *
     * @return A list of strings representing the room's data.
     */
    public List<String> toCSVRecord() {
        String status = this.isOccupied() ? "occupied" : "unoccupied";
        String guestsNames = "[No guest]";
        int guestCount = 0;

        if (this.isOccupied() && this.getGuests() != null && !this.getGuests().isEmpty()) {
            guestCount = this.getGuests().size();
            StringBuilder guestsNamesBuilder = new StringBuilder("[");
            for (int i = 0; i < guestCount; i++) {
                guestsNamesBuilder.append(this.getGuests().get(i).getName());
                if (i != guestCount - 1) {
                    guestsNamesBuilder.append(", ");
                }
            }
            guestsNamesBuilder.append("]");
            guestsNames = guestsNamesBuilder.toString();
        }

        String guestInfo = guestCount + "/" + this.getCapacity();
        String priceString = this.price.toString();
        String checkIn = this.checkInDate != null ? checkInDate.format(DATE_FORMATTER) : "N/A";
        String checkOut = this.plannedCheckOutDate != null ? plannedCheckOutDate.format(DATE_FORMATTER) : "N/A";

        return Arrays.asList(
                String.valueOf(roomNumber),
                status,
                guestsNames,
                guestInfo,
                priceString,
                checkIn,
                checkOut
        );
    }

    /**
     * Creates a Room object from a CSVRecord.
     *
     * @param csvRecord The CSVRecord containing room data.
     * @return A Room object populated with data from the CSVRecord.
     */
    public static Room fromCSVRecord(CSVRecord csvRecord) {
        int roomNumber = Integer.parseInt(csvRecord.get(0).trim());
        String status = csvRecord.get(1).trim();
        String guestsNames = csvRecord.get(2).trim();
        String guestInfo = csvRecord.get(3).trim();
        BigDecimal price = new BigDecimal(csvRecord.get(4).trim());
        String checkIn = csvRecord.get(5).trim();
        String checkOut = csvRecord.get(6).trim();

        String[] guestInfoParts = guestInfo.split("/");
        if (guestInfoParts.length != 2) {
            throw new IllegalArgumentException(String.format("Invalid guest info format: %s", guestInfo));
        }
        int capacity = Integer.parseInt(guestInfoParts[1].trim());

        Room room = new Room(roomNumber, price, capacity);

        if ("occupied".equalsIgnoreCase(status)) {
            // Parse guests
            List<Guest> guests = new ArrayList<>();
            if (!"[No guest]".equals(guestsNames)) {
                String names = guestsNames.substring(1, guestsNames.length() - 1); // Remove brackets
                String[] namesArray = names.split(", ");
                for (String name : namesArray) {
                    guests.add(new Guest(name));
                }
            }
            room.roomGuests = guests;
            room.isOccupied = true;

            // Parse dates
            if (!"N/A".equals(checkIn)) {
                room.checkInDate = LocalDate.parse(checkIn, DATE_FORMATTER);
            }
            if (!"N/A".equals(checkOut)) {
                room.plannedCheckOutDate = LocalDate.parse(checkOut, DATE_FORMATTER);
            }
        }

        return room;
    }

    /**
     * Returns 'true' if the room is occupied, 'false' otherwise.
     *
     * @return True if the room is occupied; false otherwise.
     */
    public boolean isOccupied() {
        return isOccupied;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Guest> getGuests() {
        return roomGuests;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Gets the planned check-out date.
     *
     * @return The planned check-out date.
     */
    public LocalDate getPlannedCheckOutDate() {
        return plannedCheckOutDate;
    }

    /**
     * Sets the planned check-out date.
     *
     * @param plannedCheckOutDate The planned check-out date to set.
     */
    public void setPlannedCheckOutDate(LocalDate plannedCheckOutDate) {
        this.plannedCheckOutDate = plannedCheckOutDate;
    }

    @Override
    public String toString() {
        String status = this.isOccupied() ? "occupied" : "unoccupied";
        StringBuilder guestsNames = new StringBuilder("[No guest]");
        String priceString = this.price.toString();
        int guestCount = 0;

        if (this.isOccupied() && this.getGuests() != null && !this.getGuests().isEmpty()) {
            guestCount = this.getGuests().size();
            guestsNames = new StringBuilder("[");
            for (int i = 0; i < guestCount; i++) {
                guestsNames.append(this.getGuests().get(i).getName());
                if (i != guestCount - 1) {
                    guestsNames.append(", ");
                }
            }
            guestsNames.append("]");
        }

        String guestInfo = guestCount + "/" + this.getCapacity();
        String checkIn = this.checkInDate != null ? checkInDate.toString() : "N/A";
        String checkOut = this.plannedCheckOutDate != null ? plannedCheckOutDate.toString() : "N/A";

        return String.format("%d, %s, %s, %s, %s, %s, %s", roomNumber, status, guestsNames, guestInfo, priceString, checkIn, checkOut);
    }


}