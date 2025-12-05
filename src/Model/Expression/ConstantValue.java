package model.expression;

import exceptions.CustomException;
import model.adt.IHeap;
import model.value.IValue;
import model.adt.IDict;

public class ConstantValue implements IExpression {
    private IValue value;

    public ConstantValue(IValue value){
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws CustomException{
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
