package model.value;

import model.type.IType;
import model.type.IntType;

public class IntValue implements IValue {
    private int val;

    public IntValue(int val){
        this.val = val;
    }

    @Override
    public IType getType(){
        return new IntType();
    }

    @Override
    public String toString(){
        return String.valueOf(val);
    }

    @Override
    public IValue deepCopy(){
        return new IntValue(this.val);
    }

    public int getVal(){
        return this.val;
    }

    @Override
    public boolean equals(IValue o){
        return o instanceof IntValue && ((IntValue)o).val == this.val;
    }
}
