package Model.Type;
import Model.Value.IValue;

public interface IType {
    boolean equals(Object o);
    IType deepCopy();
    IValue defaultValue();
}
