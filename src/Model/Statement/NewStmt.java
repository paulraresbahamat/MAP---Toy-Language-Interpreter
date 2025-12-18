package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.adt.IDict;
import model.expression.IExpression;
import model.type.BoolType;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class NewStmt implements IStmt {
    private String varName;
    private IExpression expression;

    public NewStmt(String varName, IExpression expression){
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        if (!prg.getSymTable().isDefined(varName)) {
            throw new CustomException("Variable " + varName + " not declared");
        }

        try {
            IType type = prg.getSymTable().get(varName).getType();
            if (!(type instanceof RefType)) {
                throw new CustomException("Variable " + varName + " is not of type RefType");
            }

            IValue value = expression.eval(prg.getSymTable(), prg.getHeap());
            if (!value.getType().equals(((RefType) type).getInner())) {
                throw new CustomException("Incorrect type - expected " +
                        ((RefType) type).getInner() + " but got " + value.getType() + " instead.");
            }

            Integer newAddress = prg.getHeap().allocate();
            prg.getHeap().put(newAddress, value);
            prg.getSymTable().put(varName, new RefValue(newAddress, value.getType()));

        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        } catch (ExpressionException e) {
            throw new CustomException(e.getMessage());
        } catch (CustomException e) {
            throw e;
        }

        return prg;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType varType = typeEnv.get(varName);
        IType expType = expression.typecheck(typeEnv);

        if (varType.equals(new RefType(expType)))
            return typeEnv;
        else
            throw new CustomException("NEW statement type mismatch");
    }

    @Override
    public IStmt deepCopy(){
        return new NewStmt(varName, expression.deepCopy());
    }

    @Override
    public String toString(){
        return "NewStmt(" + varName + ", " + expression + ")";
    }
}
