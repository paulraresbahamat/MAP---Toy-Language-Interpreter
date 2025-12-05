package model.expression;

import exceptions.ExpressionException;
import exceptions.CustomException;
import model.adt.IHeap;
import model.type.IntType;
import model.value.BoolValue;
import model.value.IValue;
import model.value.IntValue;
import model.adt.IDict;

public class RelExp implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private String operation;

    public RelExp(IExpression exp1, IExpression exp2, String operation) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws CustomException, ExpressionException {
        IValue v1, v2;
        v1 = exp1.eval(symTable, heap);
        v2 = exp2.eval(symTable, heap);

        if (v1.getType().equals(new IntType()) && v2.getType().equals(new IntType())) {
            IntValue i1 = (IntValue) v1;
            IntValue i2 = (IntValue) v2;
            int n1, n2;
            n1 = i1.getVal();
            n2 = i2.getVal();
            return switch (operation) {
                case "<" -> new BoolValue(n1 < n2);
                case "<=" -> new BoolValue(n1 <= n2);
                case ">" -> new BoolValue(n1 > n2);
                case ">=" -> new BoolValue(n1 >= n2);
                case "==" -> new BoolValue(n1 == n2);
                case "!=" -> new BoolValue(n1 != n2);
                default -> throw new CustomException("Invalid relational operator");
            };
        }

        throw new CustomException("Operands are not of type int");
    }

    @Override
    public IExpression deepCopy() {
        return new RelExp(exp1.deepCopy(), exp2.deepCopy(), operation);
    }

    @Override
    public String toString() {
        return "(" + exp1.toString() + " " + operation + " " + exp2.toString() + ")";
    }
}
