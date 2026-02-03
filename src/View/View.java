package view;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.adt.*;
import model.expression.*;
import model.statement.*;
import model.type.*;
import model.value.BoolValue;
import model.value.IntValue;
import model.value.StringValue;
import view.command.ExitCommand;
import view.command.RunExample;

public class View {

    public static IStmt ex1 =
            // int a; a=5; Print(a)
            new CompStmt(
                    new VarDeclStmt("a", new IntType()),
                    new CompStmt(
                            new AssignStmt("a", new ConstantValue(new IntValue(5))),
                            new PrintStmt(new VariableExp("a")))
            );

    public static IStmt ex2 =
            // int a; int b; a=2+3*5; b=a+1; Print(b)
            new CompStmt(
                    new VarDeclStmt("x", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("y", new IntType()),
                            new CompStmt(
                                    new AssignStmt("x", new ArithmeticExp('+', new ConstantValue(new IntValue(5)),
                                            new ArithmeticExp('*', new ConstantValue(new IntValue(3)), new ConstantValue(new IntValue(2))))),
                                    new CompStmt(
                                            new AssignStmt("y", new ArithmeticExp('+', new VariableExp("x"), new ConstantValue(new IntValue(1)))),
                                            new PrintStmt(new VariableExp("y"))))));

    public static IStmt ex3 =
            // bool m; int n; m=true; (If m Then n=10 Else n=5); Print(v)
            new CompStmt(
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

    public static IStmt ex4 =
            // string varf; varf = "test.in"; openRFile(varf); int varc; readFile(varf,
            // varc); Print(varc); readFile(varf, varc); Print(varc); closeRFile(varf);
            new CompStmt(
                    new VarDeclStmt("varf", new StringType()),
                    new CompStmt(new AssignStmt("varf", new ConstantValue(new StringValue("test.in"))),
                            new CompStmt(new OpenRFile(new VariableExp("varf")),
                                    new CompStmt(new VarDeclStmt("varc", new IntType()),
                                            new CompStmt(new ReadFile(new VariableExp("varf"), "varc"),
                                                    new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                            new CompStmt(new ReadFile(new VariableExp("varf"), "varc"),
                                                                    new CompStmt(new PrintStmt(new VariableExp("varc")),
                                                                            new CloseRFile(new VariableExp("varf"))))))))));

    public static IStmt ex5 =
            // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
            new CompStmt(
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

    public static IStmt ex6 =
            // Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
            new CompStmt(
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

    public static IStmt ex7 =
            // Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
            new CompStmt(
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

    public static IStmt ex8 =
            // Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
            new CompStmt(
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

    public static IStmt ex9 =
            // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
            new CompStmt(
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

    public static IStmt ex10 =
            // int v; Ref int a; v=10; new(a,22);
            //        // fork(wH(a,30);v=32;print(v);print(rH(a)));
            //        // print(v);print(rH(a))
            new CompStmt(
                    new VarDeclStmt("v", new IntType()),
                    new CompStmt(
                            new VarDeclStmt("a", new RefType(new IntType())),
                            new CompStmt(
                                    new AssignStmt("v", new ConstantValue(new IntValue(10))),
                                    new CompStmt(
                                            new NewStmt("a", new ConstantValue(new IntValue(22))),
                                            new CompStmt(
                                                    new ForkStmt(
                                                            new CompStmt(
                                                                    new WriteHeapStmt("a",
                                                                            new ConstantValue(new IntValue(30))),
                                                                    new CompStmt(
                                                                            new AssignStmt("v",
                                                                                    new ConstantValue(new IntValue(32))),
                                                                            new CompStmt(
                                                                                    new PrintStmt(new VariableExp("v")),
                                                                                    new PrintStmt(new ReadHeapExp(
                                                                                            new VariableExp("a"))))))),
                                                    new CompStmt(
                                                            new PrintStmt(new VariableExp("v")),
                                                            new PrintStmt(new ReadHeapExp(new VariableExp("a")))))))));

    static void main(String[] args) {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        addExample(menu, "1", ex1, "log1.txt");
        addExample(menu, "2", ex2, "log2.txt");
        addExample(menu, "3", ex3, "log3.txt");
        addExample(menu, "4", ex4, "log4.txt");
        addExample(menu, "5", ex5, "log5.txt");
        addExample(menu, "6", ex6, "log6.txt");
        addExample(menu, "7", ex7, "log7.txt");
        addExample(menu, "8", ex8, "log8.txt");
        addExample(menu, "9", ex9, "log9txt");
        addExample(menu, "10", ex10, "log10.txt");
        menu.show();
    }

    public static void addExample(TextMenu menu, String key, IStmt stmt, String logFile) {
        try {
            IDict<String, IType> typeEnv = new CustomDict<>();
            stmt.typecheck(typeEnv);

            menu.addCommand(new RunExample(key, stmt.toString(), stmt, logFile));

        } catch (CustomException e) {
            System.out.println("Typecheck error: " + e.getMessage());
            throw new RuntimeException("Program failed typecheck: " + e.getMessage());
        } catch (DictException | ExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
