package view;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import view.command.Command;
import view.command.RunExample;

public class TextMenu {
    private final Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command c) {
        commands.put(c.getKey(), c);
    }

    public void printMenu() {
        System.out.println("\n==================================================");
        System.out.println("          TOY LANGUAGE INTERPRETER MENU           ");
        System.out.println("==================================================");
        for (Command c : commands.values()) {
            System.out.printf("%4s : %s%n", c.getKey(), c.getDescription());
        }
        System.out.println("==================================================");
    }

    public void show() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                printMenu();
                System.out.println("\nPlease select an option >: ");
                String key = scanner.nextLine();
                Command c = commands.get(key);
                if (c == null) {
                    System.out.println("\nInvalid option. Try again (0-10).");
                } else {
                    c.execute();
                }
            }
        }
    }
}
