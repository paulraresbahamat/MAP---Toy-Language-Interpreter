package Model.Expression;
import Exceptions.ExpressionException;
import Exceptions.CustomException;
import Model.Value.IValue;
import Model.ADT.IDict;

public interface IExpression {
    IValue eval(IDict<String, IValue> symTable) throws ExpressionException, CustomException;
    IExpression deepCopy();
}
