package pl.edu.agh.kis.pz1;

import pl.edu.agh.kis.pz1.commands.Command;
import pl.edu.agh.kis.pz1.commands.CommandFactory;
import pl.edu.agh.kis.pz1.commands.HelpCommand;
import pl.edu.agh.kis.pz1.structure.Hotel;

import java.util.Scanner;

public class HotelManagementApplication {
    public static void main(String[] args) {
        Hotel hotel = new Hotel(); // Or load initial data
        CommandFactory commandFactory = new CommandFactory(hotel);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Hotel Management System.");

        while (true) {
            System.out.print("\nEnter command: ");
            String commandName = scanner.nextLine();

            Command command = commandFactory.createCommand(commandName);
            Command helpCommand = new HelpCommand();

            if (command != null) {
                command.execute();
            } else {
                helpCommand.execute();
            }
            if (commandName.equals("!q")) {
                break;
            }
        }
    }
}