package controller;

import model.adt.IHeap;
import model.value.IValue;
import repository.IRepository;
import model.adt.IStack;
import exceptions.CustomException;
import exceptions.StackException;
import model.PrgState;
import model.statement.IStmt;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;


    public Controller(IRepository repo){
        this.repo = repo;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    try {
                        return p.oneStep();
                    } catch (CustomException e) {
                        p.setNotCompleted(false);
                        System.out.println(e.getMessage());
                        return null;
                    }
                }))
                .toList();

        List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(p -> p != null && p.isNotCompleted())
                .toList();

        prgList.addAll(newPrgList);
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (CustomException e) {
                System.out.println(e.getMessage());
            }
        });

        repo.setPrgList(prgList);
    }

    public void allSteps() throws CustomException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());

        while(!prgList.isEmpty()) {
            IHeap<Integer, IValue> heap = prgList.getFirst().getHeap();

            Set<Integer> usedAddresses = prgList.stream()
                    .flatMap(p -> p.getUsedAddresses().stream())
                    .collect(Collectors.toSet());
            heap.setHeap(heap.safeGarbageCollector(usedAddresses, heap.getHeap()));
            try {
                oneStepForAllPrg(prgList);
            } catch (InterruptedException e) {
                throw new CustomException("Program execution interrupted");
            }

            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public IRepository getRepo() {
        return repo;
    }
}

