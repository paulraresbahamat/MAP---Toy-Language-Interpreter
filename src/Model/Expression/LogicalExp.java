package Model.Expression;
import Exceptions.ExpressionException;
import Exceptions.CustomException;
import Model.Type.BoolType;
import Model.Value.IValue;
import Model.Value.BoolValue;
import Model.ADT.IDict;

public class LogicalExp implements IExpression {
    private IExpression exp1;
    private IExpression exp2;
    private String operation;

    public LogicalExp(String operation, IExpression exp1, IExpression exp2){
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.operation = operation;
    }

    @Override
    public IValue eval(IDict<String, IValue> symTable) throws CustomException, ExpressionException{
        IValue v1, v2;
        v1 = exp1.eval(symTable);
        if(v1.getType().equals(new BoolType())){
            v2 = exp2.eval(symTable);
            if(v2.getType().equals(new BoolType())) {
                BoolValue b1 = (BoolValue) v1;
                BoolValue b2 = (BoolValue) v2;
                boolean n1, n2;
                n1 = Boolean.parseBoolean(b1.toString());
                n2 = Boolean.parseBoolean(b2.toString());
                if (operation.equals("&&")) {
                    return new BoolValue(n1 && n2);
                } else if (operation.equals(" ||")) {
                    return new BoolValue(n1 || n2);
                } else {
                    throw new CustomException("Invalid logical operator. Use && or || .");
                }
            } else {
                throw new CustomException("Operands must be boolean.");
            }
        } else{
            throw new CustomException("Operands must be boolean.");
        }
    }

    @Override
    public IExpression deepCopy(){
        return new LogicalExp(operation, exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public String toString(){
        return exp1.toString() + " " + operation + " " + exp2.toString();
    }
}
