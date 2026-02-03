package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.adt.IDict;
import model.expression.IExpression;
import model.PrgState;
import model.type.IType;
import model.value.IValue;

public class PrintStmt implements IStmt {
    private final IExpression exp;

    public PrintStmt(IExpression exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        IValue val;
        try {
            val = this.exp.eval(prg.getSymTable(), prg.getHeap());
        } catch (ExpressionException | CustomException e){
            throw new CustomException(e.getMessage());
        }
        prg.getOutput().add(val);
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        exp.typecheck(typeEnv);
        return typeEnv;
    }

    @Override
    public String toString(){
        return "print(" + this.exp.toString() + ")";
    }

    @Override
    public IStmt deepCopy(){
        return new PrintStmt(this.exp.deepCopy());
    }
}
