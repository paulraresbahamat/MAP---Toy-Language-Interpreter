package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.adt.CustomStack;
import model.adt.IDict;
import model.type.IType;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt statement){
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState currentPrg) throws CustomException {
        return new PrgState(
                new CustomStack<>(),
                currentPrg.getSymTable().deepCopy(),
                currentPrg.getOutput(),
                statement,
                currentPrg.getFileTable(),
                currentPrg.getHeap()
        );
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public IStmt deepCopy(){
        return new ForkStmt(statement.deepCopy());
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}


