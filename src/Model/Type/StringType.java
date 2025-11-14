package Model.Type;
import Model.Value.IValue;
import Model.Value.StringValue;

public class StringType implements IType {
    @Override
    public boolean equals(Object o){
        return o instanceof StringType;
    }

    @Override
    public IType deepCopy(){
        return new StringType();
    }

    @Override
    public IValue defaultValue(){
        return new StringValue("");
    }

    @Override
    public String toString(){
        return "String";
    }
}
