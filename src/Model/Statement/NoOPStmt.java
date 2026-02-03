package model.statement;
import exceptions.CustomException;
import model.PrgState;
import model.adt.IDict;
import model.type.IType;

public class NoOPStmt implements IStmt {
    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        return null;
    }

    @Override
    public String toString(){
        return "NOP";
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) {
        return typeEnv;
    }

    @Override
    public IStmt deepCopy() {
        return new NoOPStmt();
    }
}
