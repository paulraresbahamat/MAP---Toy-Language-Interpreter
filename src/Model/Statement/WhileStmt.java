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

public class WhileStmt implements IStmt {
    private IExpression expression;
    private IStmt statement;

    public WhileStmt(IExpression expression, IStmt statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        IValue value;
        try {
            value = expression.eval(prg.getSymTable(), prg.getHeap());
        } catch (ExpressionException | CustomException e) {
            throw new CustomException(e.getMessage());
        }
        if (!value.getType().equals(new BoolType())) {
            throw new CustomException("Expression is not of BoolType");
        }
        BoolValue boolValue = (BoolValue) value;
        if (boolValue.getVal()) {
            prg.getExeStack().push(this);
            prg.getExeStack().push(statement);
        }
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType condType = expression.typecheck(typeEnv);

        if (!condType.equals(new BoolType()))
            throw new CustomException("WHILE condition not boolean");

        statement.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }


    @Override
    public IStmt deepCopy() {
        return new WhileStmt(expression.deepCopy(), statement.deepCopy());
    }

    @Override
    public String toString() {
        return "WhileStmt(" + expression + ", " + statement + ")";
    }
}