package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.expression.IExpression;
import model.type.IntType;
import model.type.StringType;
import model.value.IValue;
import model.value.IntValue;
import model.value.StringValue;

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

        try {
            IValue varValue = prg.getSymTable().get(varName);
            if (!varValue.getType().equals(new IntType())) {
                throw new CustomException("Variable " + varName + " must be of type Int.");
            }

            IValue fileNameValue = exp.eval(prg.getSymTable(), prg.getHeap());
            if (!fileNameValue.getType().equals(new StringType())) {
                throw new CustomException("Expression does not evaluate to a string.");
            }
            StringValue fileName = (StringValue) fileNameValue;

            BufferedReader buff = prg.getFileTable().get(fileName);
            if (buff == null) {
                throw new CustomException("File " + fileName.getValue() + " is not open.");
            }

            String line = buff.readLine();

            IntValue val;
            if (line == null) {
                val = new IntValue(0);
            } else {
                val = new IntValue(Integer.parseInt(line));
            }

            // 6. Update variable
            prg.getSymTable().update(varName, val);

        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        } catch (ExpressionException e) {
            throw new CustomException(e.getMessage());
        } catch (NumberFormatException e) {
            throw new CustomException("Invalid integer format in file");
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