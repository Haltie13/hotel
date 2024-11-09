package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;

public class RandomInicializeCommand implements Command {
    private final Hotel hotel;

    public RandomInicializeCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        hotel.initializeRooms();
    }
}
