package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;
import java.util.Scanner;

public class SaveCommand implements Command {
    private final Hotel hotel;

    public SaveCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter file path to save hotel data: ");
        String filePath = scanner.nextLine();

        hotel.saveData(filePath);
        System.out.println("Hotel data saved successfully.");
    }
}