package repository;
import exceptions.CustomException;
import model.PrgState;
import java.util.List;

public interface IRepository {
    void addPrg(PrgState prg);
    void logPrgStateExec(PrgState prg) throws CustomException;
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> prgList);
}
