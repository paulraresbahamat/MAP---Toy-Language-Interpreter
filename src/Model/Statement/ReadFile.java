package Model.Statement;

import Exceptions.CustomException;
import Exceptions.DictException;
import Exceptions.ExpressionException;
import Model.PrgState;
import Model.Expression.IExpression;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.IValue;
import Model.Value.IntValue;
import Model.Value.StringValue;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFile implements IStmt {
    private IExpression exp;
    private String varName;

    public ReadFile(IExpression exp, String varName) {
        this.exp = exp;
        this.varName = varName;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        if (!prg.getSymTable().isDefined(varName)) {
            throw new CustomException("Variable " + varName + " is not defined.");
        }

        IValue varValue;
        try {
            varValue = prg.getSymTable().get(varName);
        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        }
        if (!varValue.getType().equals(new IntType())) {
            throw new CustomException("Variable " + varName + " must be of type Int.");
        }

        IValue fileNameValue;
        try {
            fileNameValue = exp.eval(prg.getSymTable());
        } catch (ExpressionException | CustomException e) {
            throw new CustomException(e.getMessage());
        }
        if (!fileNameValue.getType().equals(new StringType())) {
            throw new CustomException("Expression does not evaluate to a string.");
        }

        StringValue fileName = (StringValue) fileNameValue;
        BufferedReader buff;
        try {
            buff = prg.getFileTable().get(fileName);
        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        }
        if (buff == null) {
            throw new CustomException("File " + fileName.getValue() + " is not open.");
        }

        try {
            String line = buff.readLine();
            IntValue val;
            if (line == null) {
                val = new IntValue(0);
            } else {
                try {
                    val = new IntValue(Integer.parseInt(line));
                } catch (NumberFormatException e) {
                    throw new CustomException("Invalid integer format in file");
                }
            }
            try {
                prg.getSymTable().update(varName, val);
            } catch (DictException e) {
                throw new CustomException(e.getMessage());
            }
        } catch (IOException e) {
            throw new CustomException("Error reading from file: " + e.getMessage());
        }

        return prg;
    }

    @Override
    public IStmt deepCopy() {
        return new ReadFile(exp.deepCopy(), varName);
    }

    @Override
    public String toString() {
        return "readFile(" + exp.toString() + ", " + varName + ")";
    }
}