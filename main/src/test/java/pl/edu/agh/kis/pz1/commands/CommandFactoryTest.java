package pl.edu.agh.kis.pz1.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.kis.pz1.structure.Hotel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CommandFactoryTest {

    private CommandFactory commandFactory;

    @BeforeEach
    void setUp() {
        Hotel mockHotel = mock(Hotel.class);
        commandFactory = new CommandFactory(mockHotel);
    }

    @Test
    void testCreateCommand_Prices() {
        Command command = commandFactory.createCommand("prices");
        assertInstanceOf(PricesCommand.class, command);
    }

    @Test
    void testCreateCommand_List() {
        Command command = commandFactory.createCommand("list");
        assertInstanceOf(ListCommand.class, command);
    }

    @Test
    void testCreateCommand_View() {
        Command command = commandFactory.createCommand("view");
        assertInstanceOf(ViewCommand.class, command);
    }

    @Test
    void testCreateCommand_CheckIn() {
        Command command = commandFactory.createCommand("checkin");
        assertInstanceOf(CheckInCommand.class, command);
    }

    @Test
    void testCreateCommand_CheckOut() {
        Command command = commandFactory.createCommand("checkout");
        assertInstanceOf(CheckOutCommand.class, command);
    }

    @Test
    void testCreateCommand_Save() {
        Command command = commandFactory.createCommand("save");
        assertInstanceOf(SaveCommand.class, command);
    }

    @Test
    void testCreateCommand_Load() {
        Command command = commandFactory.createCommand("load");
        assertInstanceOf(LoadCommand.class, command);
    }

    @Test
    void testCreateCommand_RandomInitialize() {
        Command command = commandFactory.createCommand("random");
        assertInstanceOf(RandomInicializeCommand.class, command);
    }

    @Test
    void testCreateCommand_Exit() {
        Command command = commandFactory.createCommand("exit");
        assertInstanceOf(ExitCommand.class, command);
    }

    @Test
    void testCreateCommand_Help() {
        Command command = commandFactory.createCommand("help");
        assertInstanceOf(HelpCommand.class, command);
    }

    @Test
    void testCreateCommand_UnknownCommand() {
        Command command = commandFactory.createCommand("unknown");
        assertNull(command);
    }

    @Test
    void testCreateCommand_CaseInsensitive() {
        Command command = commandFactory.createCommand("LiSt");
        assertInstanceOf(ListCommand.class, command);
    }
}