package model.statement;

import exceptions.DictException;
import exceptions.ExpressionException;
import exceptions.CustomException;
import model.PrgState;
import model.adt.IHeap;
import model.expression.IExpression;
import model.value.IValue;
import model.adt.IDict;

public class AssignStmt implements IStmt {
    private String id;
    private IExpression exp;

    public AssignStmt(String id, IExpression exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException {
        IDict<String, IValue> symTable = prg.getSymTable();
        IHeap<Integer, IValue> heap = prg.getHeap();

        if (!symTable.isDefined(id)) {
            throw new CustomException("The variable " + id + " has not been declared.");
        }

        try {
            IValue val = exp.eval(symTable, heap);
            IValue oldVal = symTable.get(id);

            if (!val.getType().equals(oldVal.getType())) {
                throw new CustomException("Declared type of variable " + id +
                        " and type of the assigned expression do not match");
            }

            symTable.put(id, val);

        } catch (ExpressionException e) {
            throw new CustomException(e.getMessage());
        } catch (DictException e) {
            throw new CustomException(e.getMessage());
        } catch (CustomException e) {
            throw e;
        }

        return prg;
    }


    @Override
    public String toString(){
        return id + " = " + exp.toString();
    }

    @Override
    public IStmt deepCopy(){
        return new AssignStmt(this.id, this.exp.deepCopy());
    }
}