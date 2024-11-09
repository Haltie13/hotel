package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class CheckOutCommandTest {

    private Hotel mockHotel;
    private CheckOutCommand command;

    @BeforeEach
    void setUp() {
        mockHotel = mock(Hotel.class);
        command = new CheckOutCommand(mockHotel);
    }

    @Test
    void testExecute_RoomNotFound() {
        // Simulate user input
        String input = "101\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        when(mockHotel.getRoom(101)).thenReturn(null);

        command.execute();

        verify(mockHotel).getRoom(101);
    }

    @Test
    void testExecute_RoomNotOccupied() {
        String input = "102\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Room mockRoom = mock(Room.class);
        when(mockHotel.getRoom(102)).thenReturn(mockRoom);
        when(mockRoom.isOccupied()).thenReturn(false);

        command.execute();

        verify(mockHotel).getRoom(102);
        verify(mockRoom).isOccupied();
    }

    @Test
    void testExecute_SuccessfulCheckOut() {
        String input = "103\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Room mockRoom = mock(Room.class);
        when(mockHotel.getRoom(103)).thenReturn(mockRoom);
        when(mockRoom.isOccupied()).thenReturn(true);
        when(mockRoom.getCurrentStayDuration()).thenReturn(3);
        when(mockHotel.checkOut(103)).thenReturn(new BigDecimal("300.00"));

        command.execute();

        verify(mockHotel).getRoom(103);
        verify(mockRoom).isOccupied();
        verify(mockRoom).getCurrentStayDuration();
        verify(mockHotel).checkOut(103);
    }
}