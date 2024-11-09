package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Guest;
import pl.edu.agh.kis.pz1.structure.Hotel;
import pl.edu.agh.kis.pz1.structure.Room;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckInCommand implements Command {
    private final Hotel hotel;

    public CheckInCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter room number: ");
        int roomNumber = scanner.nextInt();
        scanner.nextLine();

        Room room = hotel.getRoom(roomNumber);
        if (room == null) {
            System.out.printf("Room with number %d not found%n", roomNumber);
            return;
        }
        if (room.isOccupied()) {
            System.out.printf("Room with number %d is occupied%n", roomNumber);
            return;
        }

        List<Guest> guests = new ArrayList<>();

        System.out.printf("Enter number of guests (Room capacity: %d)%n", room.getCapacity());
        int numberOfGuests = scanner.nextInt();
        scanner.nextLine();

        if (numberOfGuests <= 0) {
            System.out.println("Number of guests must be greater than 0");
            return;
        }
        if (numberOfGuests > room.getCapacity()) {
            System.out.println("Too many guests. Maximum capacity is " + room.getCapacity());
            return;
        }

        for (int i = 0; i < numberOfGuests; i++) {
            System.out.printf("Enter name of guest number %d: ", i+1);
            String name = scanner.nextLine();
            Guest guest = new Guest(name);
            guests.add(guest);
        }

        System.out.print("Enter check-in date (YYYY-MM-DD) or leave empty for today: ");
        String dateInput = scanner.nextLine();
        LocalDate checkInDate;
        if (dateInput.isEmpty()) {
            checkInDate = LocalDate.now();
        }
        else {
            checkInDate = LocalDate.parse(dateInput);
        }

        System.out.print("Enter stay duration: ");
        int stayDuration = scanner.nextInt();
        scanner.nextLine();
        if(stayDuration <= 0) {
            System.out.println("Stay duration must be greater than 0");
            return;
        }

        hotel.checkIn(roomNumber, guests, checkInDate, stayDuration);
        System.out.printf("Room with number %d has been checked in with date of %s%n", roomNumber, checkInDate);

    }
}
