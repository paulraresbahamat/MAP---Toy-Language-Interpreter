package model.value;
import model.type.StringType;
import model.type.IType;

public class StringValue implements IValue {
    private String value;

    public StringValue(String value){
        this.value = value;
    }

    @Override
    public IType getType(){
        return new StringType();
    }

    public String getValue(){
        return value;
    }
    @Override
    public IValue deepCopy(){
        return new StringValue(value);
    }

    @Override
    public boolean equals(IValue o){
        return o instanceof StringValue && ((StringValue) o).value.equals(value);
    }

    @Override
    public String toString(){
        return value;
    }
}
