package Model.Type;
import Model.Value.IValue;
import Model.Value.BoolValue;

public class BoolType implements IType {
    String type = "boolean";

    @Override
    public boolean equals(Object o){
        return o instanceof BoolType;
    }

    @Override
    public String toString(){
        return this.type;
    }

    @Override
    public IType deepCopy(){
        return new BoolType();
    }

    @Override
    public IValue defaultValue(){
        return new BoolValue(false);
    }
}
