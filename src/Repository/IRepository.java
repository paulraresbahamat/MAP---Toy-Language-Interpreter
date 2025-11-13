package Repository;
import Exceptions.CustomException;
import Model.PrgState;

public interface IRepository {
    void add(PrgState prg);
    PrgState getCurrent();
}
