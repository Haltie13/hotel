package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;

import java.util.Scanner;

public class ViewCommand implements Command {
    private final Hotel hotel;
    public ViewCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        Room room = hotel.getRoom(roomNumber);
        if (room == null) {
            System.out.printf("Room %d not found%n", roomNumber);
        }
        else {
            System.out.print(room);
        }
    }

}
