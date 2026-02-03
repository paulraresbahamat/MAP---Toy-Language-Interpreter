package model.statement;

import exceptions.CustomException;
import model.adt.IDict;
import model.PrgState;
import model.type.IType;
import model.value.IValue;

public class VarDeclStmt implements IStmt{
    private final String id;
    private final IType type;

    public VarDeclStmt(String id, IType type){
        this.id = id;
        this.type = type;
    }

    @Override
    public PrgState execute(PrgState prg) throws CustomException{
        IDict<String, IValue> symTable = prg.getSymTable();
        if(symTable.isDefined(id)){
            throw new CustomException("Variable " + id + " already declared");
        }
        symTable.put(id, type.defaultValue());

        return null;
    }

    @Override
    public IDict<String, IType> typecheck(IDict<String, IType> typeEnv) {
        typeEnv.put(id, type);
        return typeEnv;
    }

    @Override
    public String toString(){
        return type.toString() + " " + id;
    }

    @Override
    public IStmt deepCopy(){
        return new VarDeclStmt(id, type.deepCopy());
    }
}
