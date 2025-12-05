package controller;

import model.adt.IHeap;
import model.value.IValue;
import repository.IRepository;
import model.adt.IStack;
import exceptions.CustomException;
import exceptions.StackException;
import model.PrgState;
import model.statement.IStmt;

import java.util.Map;

public class Controller {
    private IRepository repo;
    private boolean useSafeGarbageCollector = true;

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

    public void allSteps() throws CustomException {
        PrgState currentPrg = repo.getCurrent();
        repo.logPrgStateExec(currentPrg);
        while (!currentPrg.getExeStack().isEmpty()) {
            oneStep(currentPrg);
            repo.logPrgStateExec(currentPrg);

            IHeap<Integer, IValue> heap = currentPrg.getHeap();
            Map<Integer, IValue> cleaned = useSafeGarbageCollector
                    ? heap.safeGarbageCollector(currentPrg.getUsedAddresses(), heap.getHeap())
                    : heap.unsafeGarbageCollector(currentPrg.getUsedAddresses(), heap.getHeap());
            heap.setHeap(cleaned);
        }
    }

    public void allSteps(boolean useSafe) throws CustomException {
        this.useSafeGarbageCollector = useSafe;
        allSteps();
    }

}
