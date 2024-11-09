package pl.edu.agh.kis.pz1.commands;

import pl.edu.agh.kis.pz1.structure.Hotel;

import java.math.BigDecimal;
import java.util.List;

import pl.edu.agh.kis.pz1.util.MyMap;

public class PricesCommand implements Command {
    private final Hotel hotel;
    public PricesCommand(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public void execute() {
        MyMap<Integer, BigDecimal> roomsPrices = hotel.listPrices();
        List<Integer> roomNumbers = roomsPrices.keys();
        List<BigDecimal> roomPrices = roomsPrices.values();
        for (int i=0; i<roomNumbers.size(); i++) {
            int roomNumber = roomNumbers.get(i);
            BigDecimal price = roomPrices.get(i);
            int roomCapacity = hotel.getRoomCapacity(roomNumber);
            System.out.printf("Room number: %d, price: %s, capacity: %d%n", roomNumber, price, roomCapacity);
        }
    }

}
