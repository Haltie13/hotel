package pl.edu.agh.kis.pz1.commands;

public class HelpCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Available commands: prices, list, checkin, checkout, save, load, random, exit, help");
    }
}