package Model.Type;
import Model.Value.IntValue;
import Model.Value.IValue;

public class IntType implements IType {
    String type = "Int";

    @Override
    public boolean equals(Object o){
        return o instanceof IntType;
    }

    @Override
    public String toString(){
        return this.type;
    }

    @Override
    public IType deepCopy(){
        return new IntType();
    }

    @Override
    public IValue defaultValue(){
        return new IntValue(0);
    }
}
