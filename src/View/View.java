package View;

import Controller.Controller;
import Model.ADT.*;
import Model.Expression.ArithmeticExp;
import Model.Expression.ConstantValue;
import Model.Expression.VariableExp;
import Model.PrgState;
import Model.Statement.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.IRepository;
import Repository.Repository;
import View.Command.ExitCommand;
import View.Command.RunExample;

import java.io.BufferedReader;

public class View {
    public static IStmt exampleOne(){
        // int a; a=5; Print(a)
        return new CompStmt(
                new VarDeclStmt("a", new IntType()),
                new CompStmt(
                        new AssignStmt("a", new ConstantValue(new IntValue(5))),
                        new PrintStmt(new VariableExp("a")))
        );
    }

    public static IStmt exampleTwo() {
        // int x; int y; x=5+3*2; y=x+1; Print(y)
        return new CompStmt(
                new VarDeclStmt("x", new IntType()),
                new CompStmt(
                        new VarDeclStmt("y", new IntType()),
                        new CompStmt(
                                new AssignStmt("x", new ArithmeticExp('+', new ConstantValue(new IntValue(5)),
                                        new ArithmeticExp('*', new ConstantValue(new IntValue(3)), new ConstantValue(new IntValue(2))))),
                                new CompStmt(
                                        new AssignStmt("y", new ArithmeticExp('+', new VariableExp("x"), new ConstantValue(new IntValue(1)))),
                                        new PrintStmt(new VariableExp("y"))))));
    }

    public static IStmt exampleThree(){
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

    public static IStmt exampleFour() {
        // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf,
        // varc); Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
        return new CompStmt(
                new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ConstantValue(new StringValue("test.in"))),
                        new CompStmt(new OpenRFile(new VariableExp("varf")),
                                new CompStmt(new VarDeclStmt("varc", new IntType()),
                                        new CompStmt(new ReadFile(new VariableExp("varf"), "varc"),
                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                        new CompStmt(new ReadFile(new VariableExp("varf"), "varc"),
                                                                new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                                        new CloseRFile(new VariableExp("varf"))))))))));
    }

    public static PrgState createPrgState(IStmt originalProgram) {
        IStack<IStmt> exeStack = new CustomStack<>();
        IDict<String, IValue> symTable = new CustomDict<>();
        IList<IValue> output = new CustomList<>();
        IDict<StringValue, BufferedReader> fileTable = new CustomDict<>();

        return new PrgState(exeStack, symTable, output, originalProgram, fileTable);
    }

    public static Controller createController(IStmt stmt, String logFilePath) {
        PrgState prgState = createPrgState(stmt);
        IRepository repo = new Repository(prgState, logFilePath);
        return new Controller(repo);
    }
}
