package model.statement;

import exceptions.DictException;
import exceptions.ExpressionException;
import exceptions.CustomException;
import model.PrgState;
import model.adt.IDict;
import model.expression.IExpression;
import model.type.BoolType;
import model.type.IType;
import model.value.BoolValue;
import model.value.IValue;

public class IfStmt implements IStmt {
    private final IExpression exp;
    private final IStmt thenStmt;
    private final IStmt elseStmt;

    public IfStmt(IExpression exp, IStmt thenStmt, IStmt elseStmt){
        this.exp = exp;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        IValue val;
        try{
            val = this.exp.eval(prg.getSymTable(), prg.getHeap());
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
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType condType = exp.typecheck(typeEnv);

        if (!condType.equals(new BoolType()))
            throw new CustomException("IF condition not boolean");

        thenStmt.typecheck(typeEnv.deepCopy());
        elseStmt.typecheck(typeEnv.deepCopy());

        return typeEnv;
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
