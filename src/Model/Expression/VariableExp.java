package Model.Expression;
import Exceptions.DictException;
import Exceptions.CustomException;
import Model.Value.IValue;
import Model.ADT.IDict;

public class VariableExp implements IExpression {
    private String id;

    public VariableExp(String id){
        this.id = id;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws CustomException{
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
    public String toString(){
        return this.id;
    }

    @Override
    public IExpression deepCopy(){
        return new VariableExp(this.id);
    }
}
