package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.structure.Guest;
import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckInCommandTest {
    private Hotel hotel;
    private CheckInCommand checkInCommand;

    @BeforeEach
    void setUp() {
        // Create a hotel with a predefined room for testing
        MyMap<Integer, Room> rooms = new MyMap<>();
        Room room101 = new Room(101, new BigDecimal("100.00"), 2); // Room number, price, capacity
        rooms.put(101, room101);

        hotel = new Hotel(rooms);

        checkInCommand = new CheckInCommand(hotel);
    }

    @Test
    void testExecuteWithValidInput() {
        // Simulate user input for a valid check-in
        String input = "101\n2\nJohn Doe\nJane Doe\n\n3\n"; // Room number, number of guests, guest names, empty date input
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkInCommand.execute();

        // Restore System.in
        System.setIn(originalIn);

        Room room = hotel.getRoom(101);
        assertTrue(room.isOccupied(), "Room should be occupied after check-in.");
        List<Guest> guests = room.getGuests();
        assertEquals(2, guests.size(), "There should be 2 guests checked in.");
        assertEquals("John Doe", guests.get(0).getName());
        assertEquals("Jane Doe", guests.get(1).getName());
        assertEquals(LocalDate.now(), room.getCheckInDate(), "Check-in date should be today's date.");
    }

    @Test
    void testExecuteRoomNotFound() {
        // Simulate input for a room that doesn't exist
        String input = "999\n"; // Room number that doesn't exist
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkInCommand.execute();

        // Restore System.in
        System.setIn(originalIn);

        // Room 999 should not exist, so the hotel should not be modified
        assertNull(hotel.getRoom(999), "Room 999 should not exist in the hotel.");
    }

    @Test
    void testExecuteRoomOccupied() {
        // Occupy room 101 before attempting to check in
        Room room = hotel.getRoom(101);
        List<Guest> existingGuests = new ArrayList<>();
        existingGuests.add(new Guest("Existing Guest"));
        room.checkIn(existingGuests, LocalDate.now(), 10);

        // Simulate input for checking into an already occupied room
        String input = "101\n"; // Room number
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkInCommand.execute();

        // Restore System.in
        System.setIn(originalIn);

        // Room should remain occupied with the original guest
        assertTrue(room.isOccupied(), "Room should still be occupied.");
        assertEquals(1, room.getGuests().size(), "Room should still have the original guest.");
        assertEquals("Existing Guest", room.getGuests().get(0).getName());
    }

    @Test
    void testExecuteInvalidNumberOfGuestsZero() {
        // Simulate input where number of guests is zero
        String input = "101\n0\n"; // Room number, number of guests
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkInCommand.execute();

        // Restore System.in
        System.setIn(originalIn);

        Room room = hotel.getRoom(101);
        assertFalse(room.isOccupied(), "Room should not be occupied when number of guests is zero.");
    }

    @Test
    void testExecuteTooManyGuests() {
        // Simulate input where number of guests exceeds room capacity
        String input = "101\n3\nJohn Doe\nJane Doe\nJim Doe\n"; // Room number, number of guests, guest names
        InputStream originalIn = System.in;
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        checkInCommand.execute();

        // Restore System.in
        System.setIn(originalIn);

        Room room = hotel.getRoom(101);
        assertFalse(room.isOccupied(), "Room should not be occupied when guests exceed capacity.");
    }

}