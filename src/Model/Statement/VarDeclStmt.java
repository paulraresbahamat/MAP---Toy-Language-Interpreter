package Model.Statement;

import Exceptions.CustomException;
import Model.ADT.IDict;
import Model.PrgState;
import Model.Type.IType;
import Model.Value.IValue;

public class VarDeclStmt implements IStmt{
    private String id;
    private IType type;

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

        return prg;
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
