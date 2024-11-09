package pl.edu.agh.kis.pz1.structure;

import pl.edu.agh.kis.pz1.util.MyMap;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

import org.apache.commons.csv.*;

public class Hotel {
    private MyMap<Integer, Room> rooms;
    private static final int TOTAL_ROOMS = 100;
    private static final int MIN_ROOM_NUMBER = 101;
    private static final int MAX_CAPACITY = 8;
    private static final double MIN_PRICE = 50.00;
    private static final double MAX_PRICE = 500.00;
    private static final Logger LOGGER = Logger.getLogger(Hotel.class.getName());
    private static final String ROOM_NOT_FOUND = "Room number %d not found.";
    Random random = new SecureRandom();


    public Hotel() {
        rooms = new MyMap<>();
    }

    public Hotel(MyMap<Integer, Room> rooms) {
        this.rooms = rooms;
    }

    public  void initializeRooms() {

        for (int i = 0; i < TOTAL_ROOMS; i++) {
            int roomNumber = MIN_ROOM_NUMBER + i;

            // Generate a random capacity between 1 and 8
            int capacity = random.nextInt(MAX_CAPACITY) + 1; // Random number between 1 and 8

            // Generate a random price between MIN_PRICE and MAX_PRICE
            double priceValue = MIN_PRICE + (MAX_PRICE - MIN_PRICE) * random.nextDouble();
            BigDecimal price = BigDecimal.valueOf(priceValue).setScale(2, RoundingMode.HALF_UP);

            rooms.put(roomNumber, new Room(roomNumber, price, capacity));
        }
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public List<Room> listRooms() {
        return new ArrayList<>(rooms.values());
    }

    public void checkIn(int roomNumber, List<Guest> guests, LocalDate checkInDate, int duration) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            LOGGER.warning(String.format(ROOM_NOT_FOUND, roomNumber));
        } else {
            room.checkIn(guests, checkInDate, duration);
        }
    }

    public BigDecimal checkOut(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null)
            LOGGER.warning(String.format(ROOM_NOT_FOUND, roomNumber));
        else {
            return room.checkOut();
        }
        return null;
    }


    public MyMap<Integer, BigDecimal> listPrices() {
        MyMap<Integer, BigDecimal> prices = new MyMap<>();
        for (Room room : rooms.values()) {
            prices.put(room.getRoomNumber(), room.getPrice());
        }
        return prices;
    }

    public int getRoomCapacity(int roomNumber) {
        Room room = rooms.get(roomNumber);
        if (room == null) {
            LOGGER.warning(String.format(ROOM_NOT_FOUND, roomNumber));
            return 0;
        } else {
            return room.getCapacity();
        }
    }

    /**
     * Saves the hotel data to a CSV file at the specified file path.
     *
     * @param filePath The path to the CSV file.
     */
    public void saveData(String filePath) {
        try (Writer writer = new FileWriter(filePath);
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT)) {

            for (Room room : rooms.values()) {
                csvPrinter.printRecord(room.toCSVRecord());
            }

            csvPrinter.flush();
            LOGGER.info(String.format("Hotel data saved to %s", filePath));
        } catch (IOException e) {
            LOGGER.severe("Error saving hotel data: " + e.getMessage());
        }
    }

    /**
     * Loads hotel data from a CSV file at the specified file path.
     *
     * @param filePath The path to the CSV file.
     */
    public void loadData(String filePath) {
        rooms = new MyMap<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)) {

            for (CSVRecord csvRecord : csvParser) {
                Room room = Room.fromCSVRecord(csvRecord);
                rooms.put(room.getRoomNumber(), room);
            }

            LOGGER.info(String.format("Hotel data loaded from %s", filePath));
        } catch (IOException e) {
            LOGGER.severe("Error loading hotel data: " + e.getMessage());
        }
    }
}