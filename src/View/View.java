package view;

import controller.Controller;
import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.adt.*;
import model.expression.*;
import model.PrgState;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;
import view.command.RunExample;

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

    public static IStmt exampleFive() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp(
                                                        "v")),
                                                new PrintStmt(new VariableExp(
                                                        "a")))))));
    }

    public static IStmt exampleSix() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new PrintStmt(new ReadHeapExp(
                                                        new VariableExp("v"))),
                                                new PrintStmt(new ArithmeticExp(
                                                        '+',
                                                        new ReadHeapExp(new ReadHeapExp(
                                                                new VariableExp("a"))),
                                                        new ConstantValue(
                                                                new IntValue(5)))))))));
    }

    public static IStmt exampleSeven() {
        // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new PrintStmt(new ReadHeapExp(new VariableExp("v"))),
                                new CompStmt(
                                        new WriteHeapStmt("v",
                                                new ConstantValue(
                                                        new IntValue(30))),
                                        new PrintStmt(new ArithmeticExp('+',
                                                new ReadHeapExp(new VariableExp(
                                                        "v")),
                                                new ConstantValue(
                                                        new IntValue(5))))))));
    }

    public static IStmt exampleEight() {
        // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        return new CompStmt(
                new VarDeclStmt("v", new RefType(new IntType())),
                new CompStmt(
                        new NewStmt("v", new ConstantValue(new IntValue(20))),
                        new CompStmt(
                                new VarDeclStmt("a",
                                        new RefType(new RefType(
                                                new IntType()))),
                                new CompStmt(
                                        new NewStmt("a", new VariableExp("v")),
                                        new CompStmt(
                                                new NewStmt("v", new ConstantValue(
                                                        new IntValue(30))),
                                                new PrintStmt(new ReadHeapExp(
                                                        new ReadHeapExp(new VariableExp(
                                                                "a")))))))));
    }

    public static IStmt exampleNine() {
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        return new CompStmt(
                new VarDeclStmt("v", new IntType()),
                new CompStmt(
                        new AssignStmt("v", new ConstantValue(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(
                                        new RelExp(
                                                new VariableExp("v"),
                                                new ConstantValue(
                                                        new IntValue(0)),
                                                ">"),
                                        new CompStmt(
                                                new PrintStmt(new VariableExp(
                                                        "v")),
                                                new AssignStmt("v",
                                                        new ArithmeticExp('-',
                                                                new VariableExp("v"),
                                                                new ConstantValue(
                                                                        new IntValue(1)))))),
                                new PrintStmt(new VariableExp("v")))));
    }

    public static PrgState createPrgState(IStmt originalProgram) {
        IStack<IStmt> exeStack = new CustomStack<>();
        IDict<String, IValue> symTable = new CustomDict<>();
        IList<IValue> output = new CustomList<>();
        IDict<StringValue, BufferedReader> fileTable = new CustomDict<>();
        IHeap<Integer, IValue> heap = new CustomHeap<>();

        try {
            IDict<String, IType> typeEnv = new CustomDict<>();
            originalProgram.typecheck(typeEnv);

            return new PrgState(exeStack, symTable, output, originalProgram, fileTable, heap);

        } catch (CustomException e) {
            System.out.println("Typecheck error: " + e.getMessage());
            throw new RuntimeException("Program failed typecheck: " + e.getMessage());
        } catch (DictException | ExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static Controller createController(IStmt stmt, String logFilePath) {
        PrgState prgState = createPrgState(stmt);
        IRepository repo = new Repository(prgState, logFilePath);
        return new Controller(repo);
    }
}
