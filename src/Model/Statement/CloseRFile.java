package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.adt.IDict;
import model.expression.IExpression;
import model.PrgState;
import model.type.IType;
import model.type.StringType;
import model.value.IValue;
import model.value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFile implements IStmt {
    private IExpression exp;

    public CloseRFile(IExpression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        IValue value;
        try {
            value = exp.eval(prg.getSymTable(), prg.getHeap());

            if (!value.getType().equals(new StringType())) {
                throw new CustomException("Expression must be a string.");
            }
            StringValue stringValue = (StringValue) value;

            BufferedReader buff = prg.getFileTable().get(stringValue);

            if (buff == null) {
                throw new CustomException("File " + stringValue.getValue() + " is not open");
            }

            buff.close();

            prg.getFileTable().put(stringValue, null);

        } catch (ExpressionException | CustomException | DictException e) {
            throw new CustomException(e.getMessage());
        } catch (IOException e) {
            throw new CustomException("Error closing file: " + e.getMessage());
        }

        return prg;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType t = exp.typecheck(typeEnv);

        if (t.equals(new StringType()))
            return typeEnv;
        else
            throw new CustomException("closeRFile argument is not string");
    }

    @Override
    public IStmt deepCopy() {
        return new CloseRFile(exp.deepCopy());
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}