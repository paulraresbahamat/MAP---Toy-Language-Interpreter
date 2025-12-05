package model.statement;

import exceptions.CustomException;
import exceptions.ExpressionException;
import model.expression.IExpression;
import model.PrgState;
import model.value.IValue;

public class PrintStmt implements IStmt {
    private IExpression exp;

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
        return prg;
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
