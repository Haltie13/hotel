package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;

import java.util.List;

public class ListCommand implements Command {
    private final Hotel hotel;
    public ListCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        List<Room> roomList = hotel.listRooms();
        if (roomList.isEmpty()) {
            System.out.println("There are no rooms in the hotel");
        }
        else {
            for (Room room : roomList) {
                System.out.println(room);
            }
        }

    }
}
