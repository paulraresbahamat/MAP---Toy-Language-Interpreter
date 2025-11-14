package Repository;
import Exceptions.CustomException;
import Model.PrgState;

public interface IRepository {
    void add(PrgState prg);
    PrgState getCurrent();
    public void logPrgStateExec(PrgState prg) throws CustomException;
}
