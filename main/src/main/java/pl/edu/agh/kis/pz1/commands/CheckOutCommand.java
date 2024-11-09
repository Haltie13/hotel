package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;

import java.math.BigDecimal;
import java.util.Scanner;

public class CheckOutCommand implements Command {
    private final Hotel hotel;

    public CheckOutCommand(Hotel hotel) {
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
            return;
        }
        if (!room.isOccupied()) {
            System.out.printf("Room %d is not occupied and cannot be checked out%n", roomNumber);
        }
        else {
            int totalStayDuration = room.getCurrentStayDuration();
            BigDecimal totalPrice =  hotel.checkOut(roomNumber);
            System.out.printf("You have checked out of Room %d with total price of %s. Total stay duration: %d%n",
                    roomNumber, totalPrice, totalStayDuration);
        }
    }
}
