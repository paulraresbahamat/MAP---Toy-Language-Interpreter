package Model;
import Model.ADT.IStack;
import Model.ADT.IDict;
import Model.ADT.IList;
import Model.Statement.IStmt;
import Model.Value.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    private IDict<String, IValue> symTable;

    public IDict<String, IValue> getSymTable(){
        return symTable;
    }

    private IList<IValue> output;

    public IList<IValue> getOutput(){
        return output;
    }

    private IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDict<String, IValue> symTable, IList<IValue> output, IStmt originalProgram){
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.output = output;
        this.originalProgram = originalProgram.deepCopy();
        exeStack.push(originalProgram);
    }

    @Override
    public String toString(){
        return "Program State {\n" + "exeStack=" + exeStack.getList() +
                ",\n symTable=" + symTable + ",\n output=" + output +
                ",\n originalProgram=" + originalProgram + "\n}";
    }
}
