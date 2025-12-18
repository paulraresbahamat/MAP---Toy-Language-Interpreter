package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.adt.IDict;
import model.expression.IExpression;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class WriteHeapStmt implements IStmt {
    private String varName;
    private IExpression expression;

    public WriteHeapStmt(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        if (!prg.getSymTable().isDefined(varName)) {
            throw new CustomException("Variable " + varName + " doesn't exist.");
        }

        try {
            IValue varValue = prg.getSymTable().get(varName);
            if (!(varValue instanceof RefValue)) {
                throw new CustomException("Variable " + varName + " is not of RefType.");
            }

            RefValue refValue = (RefValue) varValue;
            Integer address = refValue.getAddress();
            if (!prg.getHeap().isDefined(address)) {
                throw new CustomException("Address " + address + " is not allocated in heap.");
            }

            IValue value = expression.eval(prg.getSymTable(), prg.getHeap());

            if (!value.getType().equals(((RefType) refValue.getType()).getInner())) {
                throw new CustomException("Type of expression and type of variable do not match.");
            }

            prg.getHeap().put(address, value);

        } catch (DictException | ExpressionException e) {
            throw new CustomException(e.getMessage());
        } catch (CustomException e) {
            throw e;
        }

        return prg;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, DictException, ExpressionException {
        IType varType = typeEnv.get(varName);

        if (!(varType instanceof RefType))
            throw new CustomException("wH target not RefType");

        IType expType = expression.typecheck(typeEnv);
        IType inner = ((RefType) varType).getInner();

        if (inner.equals(expType))
            return typeEnv;
        else
            throw new CustomException("wH type mismatch");
    }

    @Override
    public IStmt deepCopy(){
        return new WriteHeapStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "WriteHeapStmt(" + varName + ", " + expression + ")";
    }
}
