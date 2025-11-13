package Model.Expression;

import Exceptions.CustomException;
import Model.Value.IValue;
import Model.ADT.IDict;

public class ConstantValue implements IExpression {
    private IValue value;

    public ConstantValue(IValue value){
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws CustomException{
        return this.value;
    }

    @Override
    public IExpression deepCopy(){
        return new ConstantValue(this.value.deepCopy());
    }

    @Override
    public String toString(){
        return this.value.toString();
    }
}
