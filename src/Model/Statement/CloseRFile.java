package Model.Statement;

import Exceptions.CustomException;
import Exceptions.DictException;
import Exceptions.ExpressionException;
import Model.Expression.IExpression;
import Model.PrgState;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

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
            value = exp.eval(prg.getSymTable());
        } catch (ExpressionException | CustomException e) {
            throw new CustomException(e.getMessage());
        }
        if (!value.getType().equals(new StringType())) {
            throw new CustomException("Expression must be a string.");
        }
        StringValue stringValue = (StringValue) value;

        BufferedReader buff;
        try {
            buff = prg.getFileTable().get(stringValue);
        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        }
        if (buff == null) {
            throw new CustomException("File " + stringValue.getValue() + " is not open");
        }

        try {
            buff.close();
        } catch (IOException e) {
            throw new CustomException("Error closing file: " + e.getMessage());
        }

        prg.getFileTable().put(stringValue, null);

        return prg;
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