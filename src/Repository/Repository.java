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
    public Repository(PrgState prg){
        prgList = new ArrayList<>();
        prgList.add(prg);
    }

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
    public void add(PrgState prg){
        prgList.add(prg);
    }

    @Override
    public PrgState getCurrent(){
        return prgList.getFirst();
    }

    @Override
    public void logPrgStateExec(PrgState prg) throws CustomException {
        if(logFilePath == null){
            setLogFilePath();
        }
        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));

            logFile.println(prg.toString());
            logFile.close();
        }catch(IOException e){
            throw new CustomException("There was a problem when opening the log file.");
        }
    }

}
