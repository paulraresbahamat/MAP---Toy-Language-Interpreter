package Model.Statement;

import Exceptions.DictException;
import Exceptions.ExpressionException;
import Exceptions.CustomException;
import Model.PrgState;
import Model.Expression.IExpression;
import Model.Value.IValue;
import Model.ADT.IDict;

public class AssignStmt implements IStmt {
    private String id;
    private IExpression exp;

    public AssignStmt(String id, IExpression exp){
        this.id = id;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        IDict<String, IValue> symTable = prg.getSymTable();
        if(symTable.isDefined(id)){
            IValue val;
            try{
                val = this.exp.eval(symTable);
            }catch(ExpressionException | CustomException e){
                throw new CustomException(e.getMessage());
            }
            try{
                if(val.getType().equals(symTable.get(id).getType())){
                    symTable.put(id,val);
                }else{
                    throw new CustomException("Declared type of variable " + id + " and type of the assigned expression do not match");
                }
            } catch(DictException | CustomException e){
                throw new CustomException(e.getMessage());
            }
        } else{
            throw new CustomException("The variable " + id + " has not been declared.");
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
