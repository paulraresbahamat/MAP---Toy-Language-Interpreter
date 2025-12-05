package model.statement;

import exceptions.CustomException;
import model.PrgState;

public interface IStmt {
    PrgState execute(PrgState prg) throws CustomException;
    IStmt deepCopy();
}
