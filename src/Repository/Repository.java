package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import exceptions.CustomException;
import model.PrgState;

public class Repository implements IRepository {
    private List<PrgState> prgList;
    private String logFilePath;

    public Repository(PrgState prg, String logFilePath){
        this.prgList = new ArrayList<>();
        this.prgList.add(prg);
        this.logFilePath = logFilePath;
    }

    public void setLogFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the log's file path: ");
        this.logFilePath = scanner.nextLine();
        scanner.close();
    }

    @Override
    public void addPrg(PrgState prg){
        prgList.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws CustomException {
        if (logFilePath == null) {
            setLogFilePath();
        }
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)))) {
            logFile.println(prg.toString());
        } catch (IOException e) {
            throw new CustomException("There was a problem when opening the log file: " + e.getMessage());
        }
    }

    @Override
    public List<PrgState> getPrgList(){
        return prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgList){
        this.prgList = prgList;
    }

}
