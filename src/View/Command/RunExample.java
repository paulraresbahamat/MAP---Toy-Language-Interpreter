// src/view/command/RunExample.java
package view.command;
import controller.Controller;
import exceptions.CustomException;
import model.statement.IStmt;
import java.util.Scanner;

public class RunExample extends Command {
    private Controller controller;
    private boolean hasBeenExecuted;

    public RunExample(String key, IStmt stmt, Controller controller) {
        super(key, stmt.toString());
        this.controller = controller;
        this.hasBeenExecuted = false;
    }

    @Override
    public void execute() {
        if (hasBeenExecuted) {
            System.out.println("Program has already been executed!");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        boolean useSafe = true;
        while (true) {
            System.out.print("Use safe garbage collector? (s = safe / u = unsafe): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if ("s".equals(line) || "safe".equals(line)) {
                useSafe = true;
                break;
            }
            if ("u".equals(line) || "unsafe".equals(line)) {
                useSafe = false;
                break;
            }
            System.out.println("Invalid input. Please enter 's' or 'u'.");
        }

        try {
            controller.allSteps(useSafe);
            hasBeenExecuted = true;
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }
        // do not close scanner to avoid closing System.in for other commands
    }

    public boolean hasBeenExecuted() {
        return hasBeenExecuted;
    }
}
