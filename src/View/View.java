package View;

import Controller.Controller;
import Exceptions.CustomException;
import Model.ADT.*;
import Model.Expression.ArithmeticExp;
import Model.Expression.ConstantValue;
import Model.Expression.VariableExp;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Repository.IRepository;
import Repository.Repository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class View {
    private static IStmt exampleOne(){
        // int a; a=5; Print(a)
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ConstantValue(new IntValue(5))),
                        new PrintStmt(new VariableExp("a")))
        );
    }

    private static IStmt exampleTwo() {
        // int x; int y; x=5+3*2; y=x+1; Print(y)
        return new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new VarDeclStmt("y", new IntType()),
                        new CompStmt(
                                new AssignStmt("x", new ArithmeticExp('+', new ConstantValue(new IntValue(5)),
                                        new ArithmeticExp('*', new ConstantValue(new IntValue(3)), new ConstantValue(new IntValue(3))))),
                                new CompStmt(
                                        new AssignStmt("y", new ArithmeticExp('+', new VariableExp("x"), new ConstantValue(new IntValue(2)))),
                                        new PrintStmt(new VariableExp("y"))))));
    }

    private static IStmt exampleThree(){
        // bool m; int n; m=true; (If m Then n=10 Else n=5); Print(v)
        return new CompStmt(
                new VarDeclStmt("m", new BoolType()),
                new CompStmt(
                        new VarDeclStmt("n", new IntType()),
                        new CompStmt(
                                new AssignStmt("m", new ConstantValue(new BoolValue(true))),
                                new CompStmt(
                                        new IfStmt(new VariableExp("m"),
                                                new AssignStmt("n", new ConstantValue(new IntValue(10))),
                                                new AssignStmt("n", new ConstantValue(new IntValue(5)))),
                                        new PrintStmt(new VariableExp("n"))))));

    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        IStmt option = null;

        while (option == null) {
            System.out.println("1. int a; a=5; Print(a)");
            System.out.println("2. int x; int y; x=5+3*2; y=x+1; Print(y)");
            System.out.println("3. bool m; int n; m=true; (If m Then n=10 Else n=5); Print(v)");
            System.out.println("\nSelect the problem you would like to execute (1-3): ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        option = exampleOne();
                        break;
                    case 2:
                        option = exampleTwo();
                        break;
                    case 3:
                        option = exampleThree();
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
            }
        }

        IStack<IStmt> stk = new CustomStack<>();
        IDict<String, IValue> symTable = new CustomDict<>();
        IList<IValue> output = new CustomList<>();

        PrgState prg = new PrgState(stk, symTable, output, option);
        IRepository repo = new Repository(prg);
        Controller ctrl = new Controller(repo);

        try {
            ctrl.allSteps();
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

        scanner.close();
    }
}
