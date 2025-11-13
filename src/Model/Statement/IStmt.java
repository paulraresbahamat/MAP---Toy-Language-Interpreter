package Model.Statement;

import Exceptions.CustomException;
import Model.PrgState;

public interface IStmt {
    PrgState execute(PrgState prg) throws CustomException;
    IStmt deepCopy();
}
