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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenRFile implements IStmt{
    private final IExpression exp;
    public OpenRFile(IExpression exp){
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        try {
            IValue value = exp.eval(prg.getSymTable(), prg.getHeap());
            if (!value.getType().equals(new StringType())) {
                throw new CustomException("The expression must be a string.");
            }
            StringValue stringValue = (StringValue) value;
            if (prg.getFileTable().isDefined(stringValue)) {
                throw new CustomException("File is already open.");
            }
            BufferedReader buff = new BufferedReader(new FileReader(stringValue.getValue()));
            prg.getFileTable().put(stringValue, buff);
        } catch (ExpressionException | CustomException e) {
            throw new CustomException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new CustomException("File not found: " + e.getMessage());
        }
        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType t = exp.typecheck(typeEnv);

        if (t.equals(new StringType()))
            return typeEnv;
        else
            throw new CustomException("openRFile argument is not string");
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