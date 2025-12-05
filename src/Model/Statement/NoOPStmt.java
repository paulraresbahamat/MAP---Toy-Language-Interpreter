package model.statement;
import exceptions.CustomException;
import model.PrgState;

public class NoOPStmt implements IStmt {
    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        return prg;
    }

    @Override
    public String toString(){
        return "NOP";
    }

    @Override
    public IStmt deepCopy() {
        return new NoOPStmt();
    }
}
