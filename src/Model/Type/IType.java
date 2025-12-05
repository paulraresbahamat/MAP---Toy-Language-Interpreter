package model.type;
import model.value.IValue;

public interface IType {
    boolean equals(Object o);
    IType deepCopy();
    IValue defaultValue();
}
