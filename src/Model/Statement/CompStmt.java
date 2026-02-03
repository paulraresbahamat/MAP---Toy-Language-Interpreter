package model.statement;
import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.adt.IDict;
import model.adt.IStack;
import model.type.IType;

public class CompStmt implements IStmt {
    private final IStmt first;
    private final IStmt second;

    public CompStmt(IStmt first, IStmt second){
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        IStack<IStmt> stk = prg.getExeStack();

        stk.push(second);
        stk.push(first);
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        return second.typecheck(first.typecheck(typeEnv));
    }


    @Override
    public String toString(){
        return "(" + first.toString() + ";" + second.toString() + ")";
    }

    @Override
    public IStmt deepCopy(){
        return new CompStmt(this.first.deepCopy(), this.second.deepCopy());
    }
}
