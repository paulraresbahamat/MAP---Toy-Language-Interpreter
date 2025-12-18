package model.expression;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.adt.IDict;
import model.adt.IHeap;
import model.type.IType;
import model.type.RefType;
import model.value.IValue;
import model.value.RefValue;

public class ReadHeapExp implements IExpression{
    private IExpression exp;
    public ReadHeapExp(IExpression exp){
        this.exp = exp;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws CustomException, ExpressionException{
        //evaluate inner expression
        IValue value = exp.eval(symTable, heap);
        if(!(value instanceof RefValue))
            throw new CustomException("ReadHeap expects RefValue");

        int address = ((RefValue) value).getAddress();
        if(!heap.isDefined(address))
            throw new CustomException("Invalid heap address: " + address);
        try {
            return heap.get(address);
        }catch(Exception e){
            throw new CustomException(e.getMessage());
        }
    }

    @Override
    public IType typecheck(IDict<String, IType> typeEnv) throws CustomException, ExpressionException, DictException {
        IType t = exp.typecheck(typeEnv);

        if (t instanceof RefType)
            return ((RefType) t).getInner();
        else
            throw new CustomException("rH argument is not a RefType");
    }

    @Override
    public IExpression deepCopy() {
        return new ReadHeapExp(exp.deepCopy());
    }

    @Override
    public String toString() { return "ReadHeapExp(" + exp + ")"; }
}
