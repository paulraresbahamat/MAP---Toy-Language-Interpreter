package view;

import controller.Controller;
import model.PrgState;
import model.statement.IStmt;
import repository.IRepository;
import repository.Repository;
import view.command.ExitCommand;
import view.command.RunExample;

import static view.View.createPrgState;

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

        IStmt ex5 = View.exampleFive();
        PrgState prg5 = createPrgState(ex5);
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        IStmt ex6 = View.exampleSix();
        PrgState prg6 = createPrgState(ex6);
        IRepository repo6 = new Repository(prg6, "log6.txt");
        Controller ctr6 = new Controller(repo6);

        IStmt ex7 = View.exampleSeven();
        PrgState prg7 = createPrgState(ex7);
        IRepository repo7 = new Repository(prg7, "log7.txt");
        Controller ctr7 = new Controller(repo7);

        IStmt ex8 = View.exampleEight();
        PrgState prg8 = createPrgState(ex8);
        IRepository repo8 = new Repository(prg8, "log8.txt");
        Controller ctr8 = new Controller(repo8);

        IStmt ex9 = View.exampleNine();
        PrgState prg9 = createPrgState(ex9);
        IRepository repo9 = new Repository(prg9, "log9.txt");
        Controller ctr9 = new Controller(repo9);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1, ctr1));
        menu.addCommand(new RunExample("2", ex2, ctr2));
        menu.addCommand(new RunExample("3", ex3, ctr3));
        menu.addCommand(new RunExample("4", ex4, ctr4));
        menu.addCommand(new RunExample("5", ex5, ctr5));
        menu.addCommand(new RunExample("6", ex6, ctr6));
        menu.addCommand(new RunExample("7", ex7, ctr7));
        menu.addCommand(new RunExample("8", ex8, ctr8));
        menu.addCommand(new RunExample("9", ex9, ctr9));

        menu.show();
    }
}
