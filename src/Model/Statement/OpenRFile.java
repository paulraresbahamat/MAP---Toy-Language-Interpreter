package Model.Statement;

import Exceptions.CustomException;
import Exceptions.ExpressionException;
import Model.Expression.IExpression;
import Model.PrgState;
import Model.Type.IType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    private IExpression exp;
    public OpenRFile(IExpression exp){
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
            throw new CustomException("The expression must be a string.");
        }
        StringValue stringValue = (StringValue) value;
        if (prg.getFileTable().isDefined(stringValue)) {
            throw new CustomException("File is already open.");
        }
        try {
            BufferedReader buff = new BufferedReader(new FileReader(stringValue.getValue()));
            prg.getFileTable().put(stringValue, buff);
        } catch (FileNotFoundException e) {
            throw new CustomException("File not found: " + e.getMessage());
        }
        return prg;
    }

    @Override
    public IStmt deepCopy(){
        return new OpenRFile(exp.deepCopy());
    }

    @Override
    public String toString(){
        return "openRFile(" + exp.toString() + ")";
    }
}
