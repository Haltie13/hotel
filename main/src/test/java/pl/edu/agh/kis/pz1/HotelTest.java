package pl.edu.agh.kis.pz1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.structure.Guest;
import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;
import pl.edu.agh.kis.pz1.util.MyMap;

import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class HotelTest {
    private Hotel hotel;
    private MyMap<Integer, Room> roomMap;
    private Room mockRoom;

    @BeforeEach
    void setUp() {
        roomMap = new MyMap<>();
        hotel = new Hotel(roomMap);
        mockRoom = mock(Room.class);
    }

    @Test
    void getRoom_found() {
        roomMap.put(101, mockRoom);
        assertSame(mockRoom, hotel.getRoom(101));
    }

    @Test
    void getRoom_notFound() {
        assertNull(hotel.getRoom(101));
    }

    @Test
    void listRooms_emptyHotel() {
        assertTrue(hotel.listRooms().isEmpty());
    }

    @Test
    void listRooms_withRooms() {
        roomMap.put(101, mockRoom);
        roomMap.put(102, mockRoom);
        List<Room> rooms = hotel.listRooms();
        assertEquals(2, rooms.size());
        assertTrue(rooms.contains(mockRoom));
    }

    @Test
    void checkIn_roomExists() {
        roomMap.put(101, mockRoom);
        LocalDate checkInDate = LocalDate.now();
        List<Guest> guests = new ArrayList<>();
        hotel.checkIn(101, guests, checkInDate, 5);
        verify(mockRoom).checkIn(guests, checkInDate, 5);
    }


    @Test
    void checkOut_roomExists() {
        roomMap.put(101, mockRoom);
        when(mockRoom.checkOut()).thenReturn(new BigDecimal("200.00"));
        hotel.checkOut(101);
        verify(mockRoom).checkOut();
    }

    @Test
    void checkOut_roomDoesNotExist() {
        hotel.checkOut(101);
        verify(mockRoom, never()).checkOut();
    }
}