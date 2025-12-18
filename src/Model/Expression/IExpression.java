package model.expression;
import exceptions.DictException;
import exceptions.ExpressionException;
import exceptions.CustomException;
import model.adt.IHeap;
import model.type.IType;
import model.value.IValue;
import model.adt.IDict;

public interface IExpression {
    IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ExpressionException, CustomException;
    IType typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException;
    IExpression deepCopy();
}
