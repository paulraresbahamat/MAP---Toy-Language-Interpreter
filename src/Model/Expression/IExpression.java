package model.expression;
import exceptions.ExpressionException;
import exceptions.CustomException;
import model.adt.IHeap;
import model.value.IValue;
import model.adt.IDict;

public interface IExpression {
    IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ExpressionException, CustomException;
    IExpression deepCopy();
}
