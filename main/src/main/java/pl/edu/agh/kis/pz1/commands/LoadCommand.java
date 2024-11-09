package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;
import java.util.Scanner;

public class LoadCommand implements Command {
    private final Hotel hotel;

    public LoadCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path to load hotel data: ");
        String filePath = scanner.nextLine();

        hotel.loadData(filePath);
        System.out.println("Hotel data loaded successfully.");
    }
}