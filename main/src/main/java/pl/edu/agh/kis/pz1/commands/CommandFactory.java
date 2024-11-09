package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;

public class CommandFactory {
    private final Hotel hotel;

    public CommandFactory(Hotel hotel) {
        this.hotel = hotel;
    }

    public Command createCommand(String commandName) {
        switch (commandName.toLowerCase()) {
            case "prices":
                return new PricesCommand(hotel);
            case "list":
                return new ListCommand(hotel);
            case "view":
                return new ViewCommand(hotel);
            case "checkin":
                return new CheckInCommand(hotel);
            case "checkout":
                return new CheckOutCommand(hotel);
            case "save":
                return new SaveCommand(hotel);
            case "load":
                return new LoadCommand(hotel);
            case "random":
                return new RandomInicializeCommand(hotel);
            case "exit":
                return new ExitCommand();
            case "help":
                return new HelpCommand();
            default:
                System.out.printf("Unknown command: %s%n", commandName);
                return null;
        }
    }
}