package view.command;
import controller.Controller;
import exceptions.CustomException;
import model.PrgState;
import model.adt.CustomDict;
import model.adt.CustomHeap;
import model.adt.CustomList;
import model.adt.CustomStack;
import model.statement.IStmt;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.Scanner;

public class RunExample extends Command {
    private final IStmt originalStmt;
    private final String logFile;

    public RunExample(String key, String desc, IStmt stmt, String logFile) {
        super(key, desc);
        this.originalStmt = stmt;
        this.logFile = logFile;
    }

    @Override
    public void execute() {
        try {
            PrgState prg = new PrgState(
                    new CustomStack<>(),
                    new CustomDict<>(),
                    new CustomList<>(),
                    originalStmt.deepCopy(),
                    new CustomDict<>(),
                    new CustomHeap<>()
            );

            IRepository repo = new Repository(prg, logFile);
            Controller controller = new Controller(repo);
            controller.allSteps();

            System.out.println("Execution finished successfully.");
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
