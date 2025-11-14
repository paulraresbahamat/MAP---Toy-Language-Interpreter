package Controller;

import Repository.IRepository;
import Model.ADT.IStack;
import Exceptions.CustomException;
import Exceptions.StackException;
import Model.PrgState;
import Model.Statement.IStmt;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo){
        this.repo = repo;
    }

    public PrgState oneStep(PrgState prg) throws CustomException {
        IStack<IStmt> stk = prg.getExeStack();
        if (stk.isEmpty()) {
            throw new CustomException("Empty execution stack... :(");
        }

        IStmt currentStmt;
        try {
            currentStmt = stk.pop();
        } catch (StackException e) {
            throw new CustomException(e.getMessage());
        }
        return currentStmt.execute(prg);
    }

    public void allSteps() throws CustomException{
        PrgState currentPrg = repo.getCurrent();
        repo.logPrgStateExec(currentPrg);
        while(!currentPrg.getExeStack().isEmpty()){
            oneStep(currentPrg);
            repo.logPrgStateExec(currentPrg);
        }
    }
}
