package View;

import Controller.Controller;
import Model.ADT.*;
import Model.PrgState;
import Model.Statement.IStmt;
import Model.Value.IValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.Command.ExitCommand;
import View.Command.RunExample;

import java.io.BufferedReader;

import static View.View.createPrgState;

public class Interpreter {

    public static void main(String[] args) {
        IStmt ex1 = View.exampleOne();
        PrgState prg1 = createPrgState(ex1);
        IRepository repo1 = new Repository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        IStmt ex2 = View.exampleTwo();
        PrgState prg2 = createPrgState(ex2);
        IRepository repo2 = new Repository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        IStmt ex3 = View.exampleThree();
        PrgState prg3 = createPrgState(ex3);
        IRepository repo3 = new Repository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        IStmt ex4 = View.exampleFour();
        PrgState prg4 = createPrgState(ex4);
        IRepository repo4 = new Repository(prg4, "log4.txt");
        Controller ctr4 = new Controller(repo4);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1, ctr1));
        menu.addCommand(new RunExample("2", ex2, ctr2));
        menu.addCommand(new RunExample("3", ex3, ctr3));
        menu.addCommand(new RunExample("4", ex4, ctr4));

        menu.show();
    }
}
