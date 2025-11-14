package Model.Statement;

import Exceptions.ExpressionException;
import Exceptions.CustomException;
import Model.PrgState;
import Model.Expression.IExpression;
import Model.Type.BoolType;
import Model.Value.BoolValue;
import Model.Value.IValue;

public class IfStmt implements IStmt {
    private IExpression exp;
    private IStmt thenStmt;
    private IStmt elseStmt;

    public IfStmt(IExpression exp, IStmt thenStmt, IStmt elseStmt){
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        IValue val;
        try{
            val = this.exp.eval(prg.getSymTable());
        } catch (ExpressionException | CustomException e){
            throw new CustomException(e.getMessage());
        }
        if(val.getType().equals(new BoolType())){
            if(((BoolValue) val).getVal()){
                prg.getExeStack().push(this.thenStmt);
            }else{
                prg.getExeStack().push(this.elseStmt);
            }
        } else{
            throw new CustomException("The condition in the if statement is not a boolean.");
        }
        return prg;
    }

    @Override
    public String toString(){
        return "if (" + this.exp.toString() + ") then (" + this.thenStmt.toString() + ") else (" + this.elseStmt.toString() + ")";
    }

    @Override
    public IStmt deepCopy(){
        return new IfStmt(this.exp.deepCopy(), this.thenStmt.deepCopy(), this.elseStmt.deepCopy());
    }
}
