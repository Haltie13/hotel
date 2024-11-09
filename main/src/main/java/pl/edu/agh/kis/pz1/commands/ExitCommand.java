package pl.edu.agh.kis.pz1.commands;

public class ExitCommand implements Command {

    @Override
    public void execute() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}
