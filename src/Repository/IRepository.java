package repository;
import exceptions.CustomException;
import model.PrgState;

public interface IRepository {
    void add(PrgState prg);
    PrgState getCurrent();
    public void logPrgStateExec(PrgState prg) throws CustomException;
}
