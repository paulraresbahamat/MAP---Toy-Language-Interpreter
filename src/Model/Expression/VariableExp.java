package model.expression;
import exceptions.DictException;
import exceptions.CustomException;
import exceptions.ExpressionException;
import model.adt.IHeap;
import model.type.IType;
import model.value.IValue;
import model.adt.IDict;

public class VariableExp implements IExpression {
    private String id;

    public VariableExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws CustomException{
        if(symTable.isDefined(this.id)){
            try{
                return symTable.get(this.id);
            }catch(DictException e){
                throw new CustomException(e.getMessage());
            }
        } else{
            throw new CustomException("The variable " + this.id + " has not been declared.");
        }
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        return typeEnv.get(id);
    }

    @Override
    public String toString(){
        return this.id;
    }

    @Override
    public IExpression deepCopy(){
        return new VariableExp(this.id);
    }
}
