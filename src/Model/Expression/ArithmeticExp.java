package model.expression;
import exceptions.ExpressionException;
import exceptions.CustomException;
import model.adt.IHeap;
import model.type.IntType;
import model.value.IValue;
import model.value.IntValue;
import model.adt.IDict;

public class ArithmeticExp implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private char operation;

    public ArithmeticExp(char operation, IExpression exp1, IExpression exp2){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable, IHeap<Integer, IValue> heap) throws ExpressionException, CustomException{
        IValue v1, v2;
        v1 = exp1.eval(symTable, heap);
        if (v1.getType().equals(new IntType())){
            v2 = exp2.eval(symTable, heap);
            if(v2.getType().equals(new IntType())){
                IntValue i1 = (IntValue) v1;
                IntValue i2 = (IntValue) v2;
                int n1, n2;
                n1 = i1.getVal();
                n2 = i2.getVal();
                return switch (operation) {
                    case '+' -> new IntValue(n1 + n2);
                    case '-' -> new IntValue(n1 - n2);
                    case '*' -> new IntValue(n1 * n2);
                    case '/' -> {
                        if (n2 == 0)
                            throw new ExpressionException("Cannot divide by 0.");
                        yield new IntValue(n1 / n2);
                    }
                    default -> throw new CustomException("Invalid arithmetic operator. Use +, -, * or / .");
                };
            } else
                throw new CustomException("Operands must be integers.");
        } else
            throw new CustomException("Operands must be integers.");
    }

    @Override
    public IExpression deepCopy(){
        return new ArithmeticExp(operation, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return exp1.toString() + " " + operation + " " + exp2.toString();
    }
}
