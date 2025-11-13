package Model.Statement;

import Exceptions.CustomException;
import Exceptions.ExpressionException;
import Model.Expression.IExpression;
import Model.PrgState;
import Model.Value.IValue;

public class PrintStmt implements IStmt {
    private IExpression exp;

    public PrintStmt(IExpression exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        IValue val;
        try {
            val = this.exp.eval(prg.getSymTable());
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
