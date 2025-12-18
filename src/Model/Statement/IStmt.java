package model.statement;

import exceptions.CustomException;
import exceptions.DictException;
import exceptions.ExpressionException;
import model.PrgState;
import model.adt.IDict;
import model.type.IType;

public interface IStmt {
    PrgState execute(PrgState prg) throws CustomException;

    IDict<String, IType> typecheck(IDict<String, IType> typeEnv) throws CustomException, DictException, ExpressionException;

    IStmt deepCopy();
}
