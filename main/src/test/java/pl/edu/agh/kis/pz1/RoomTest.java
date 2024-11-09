package pl.edu.agh.kis.pz1;

import org.apache.commons.csv.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.structure.Guest;
import pl.edu.agh.kis.pz1.structure.Room;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.agh.kis.pz1.structure.Room.fromCSVRecord;

class RoomTest {
    private Room room;
    private int roomNumber = 101;
    private BigDecimal price = new BigDecimal("100.00");
    private int capacity = 2;

    @BeforeEach
    void setUp() {
        room = new Room(roomNumber, price, capacity);
    }

    @Test
    void testCheckIn_Success() {
        List<Guest> guests = Arrays.asList(new Guest("John Doe"), new Guest("Jane Smith"));
        LocalDate checkInDate = LocalDate.now();
        int duration = 5;

        boolean result = room.checkIn(guests, checkInDate, duration);

        assertTrue(result, "Check-in should be successful with valid inputs.");
        assertTrue(room.isOccupied(), "Room should be occupied after check-in.");
        assertEquals(guests, room.getGuests(), "Guests should be correctly assigned.");
        assertEquals(checkInDate, room.getCheckInDate(), "Check-in date should be set correctly.");
        assertEquals(checkInDate.plusDays(duration), room.getPlannedCheckOutDate(), "Planned check-out date should be calculated correctly.");
    }

    @Test
    void testCheckIn_RoomAlreadyOccupied() {
        List<Guest> guests = Collections.singletonList(new Guest("John Doe"));
        room.checkIn(guests, LocalDate.now(), 3);

        List<Guest> newGuests = Collections.singletonList(new Guest("Alice"));
        boolean result = room.checkIn(newGuests, LocalDate.now(), 2);

        assertFalse(result, "Check-in should fail when room is already occupied.");
        assertEquals(guests, room.getGuests(), "Original guests should remain assigned.");
    }

    @Test
    void testCheckIn_InvalidDuration() {
        List<Guest> guests = Collections.singletonList(new Guest("John Doe"));
        boolean result = room.checkIn(guests, LocalDate.now(), 0);

        assertFalse(result, "Check-in should fail with zero or negative duration.");
        assertFalse(room.isOccupied(), "Room should not be occupied.");
    }

    @Test
    void testCheckIn_NullGuestList() {
        boolean result = room.checkIn(null, LocalDate.now(), 3);

        assertFalse(result, "Check-in should fail with null guest list.");
        assertFalse(room.isOccupied(), "Room should not be occupied.");
    }

    @Test
    void testCheckIn_EmptyGuestList() {
        boolean result = room.checkIn(Collections.emptyList(), LocalDate.now(), 3);

        assertFalse(result, "Check-in should fail with empty guest list.");
        assertFalse(room.isOccupied(), "Room should not be occupied.");
    }

    @Test
    void testCheckOut_Success() {
        Guest guest = new Guest("John Doe");
        List<Guest> guests = new ArrayList<>();
        guests.add(guest);
        room.checkIn(guests, LocalDate.now().minusDays(2), 3);

        BigDecimal totalPrice = room.checkOut();

        assertEquals(new BigDecimal("200.00"), totalPrice, "Total price should be calculated based on days stayed.");
        assertFalse(room.isOccupied(), "Room should not be occupied after check-out.");
        assertNull(room.getCheckInDate(), "Check-in date should be reset.");
        assertNull(room.getPlannedCheckOutDate(), "Planned check-out date should be reset.");
        assertTrue(room.getGuests().isEmpty(), "Guest list should be cleared.");
    }

    @Test
    void testCheckOut_NotOccupied() {
        BigDecimal totalPrice = room.checkOut();

        assertEquals(BigDecimal.ZERO, totalPrice, "Total price should be zero when room is not occupied.");
    }

    @Test
    void testGetCurrentStayDuration() {
        List<Guest> guests = Collections.singletonList(new Guest("John Doe"));
        room.checkIn(guests, LocalDate.now().minusDays(3), 5);

        int duration = room.getCurrentStayDuration();

        assertEquals(3, duration, "Current stay duration should be calculated correctly.");
    }

    @Test
    void testToString_OccupiedRoom() {
        List<Guest> guests = Arrays.asList(new Guest("John Doe"), new Guest("Jane Smith"));
        room.checkIn(guests, LocalDate.of(2023, 10, 10), 4);

        String expected = "101, occupied, [John Doe, Jane Smith], 2/2, 100.00, 2023-10-10, 2023-10-14";
        assertEquals(expected, room.toString(), "toString() should return correct string for occupied room.");
    }

    @Test
    void testToString_UnoccupiedRoom() {
        String expected = "101, unoccupied, [No guest], 0/2, 100.00, N/A, N/A";
        assertEquals(expected, room.toString(), "toString() should return correct string for unoccupied room.");
    }


    @Test
    void testFromCSVRecord_UnoccupiedRoom() throws IOException {
        String csvLine = "101, unoccupied, [No guest], 0/2, 100.00, N/A, N/A";
        CSVParser parser = CSVParser.parse(csvLine, CSVFormat.DEFAULT);
        CSVRecord csvRecord = parser.getRecords().get(0);

        Room loadedRoom = fromCSVRecord(csvRecord);

        assertEquals(101, loadedRoom.getRoomNumber());
        assertEquals(new BigDecimal("100.00"), loadedRoom.getPrice());
        assertEquals(2, loadedRoom.getCapacity());
        assertFalse(loadedRoom.isOccupied());
        assertTrue(loadedRoom.getGuests().isEmpty());
        assertNull(loadedRoom.getCheckInDate());
        assertNull(loadedRoom.getPlannedCheckOutDate());
    }

}