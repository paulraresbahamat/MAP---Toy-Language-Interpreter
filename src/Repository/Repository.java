package Repository;

import java.util.List;
import java.util.ArrayList;
import Exceptions.CustomException;
import Model.PrgState;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    public Repository(PrgState prg){
        prgList = new ArrayList<>();
        prgList.add(prg);
    }

    @Override
    public void add(PrgState prg){
        prgList.add(prg);
    }

    @Override
    public PrgState getCurrent(){
        return prgList.getFirst();
    }

}
