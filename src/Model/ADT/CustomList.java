package model.adt;

import java.util.List;
import java.util.ArrayList;
import exceptions.ListException;

public class CustomList<T> implements IList<T> {
    private List<T> list;

    public CustomList(){
        list = new ArrayList<T>();
    }

    @Override
    public void add(T elem){
        list.add(elem);
    }

    @Override
    public T get(int index) throws ListException{
        if(index<0 || index >= size()){
            throw new ListException("Index is out of bounds: " + index);
        }
        return list.get(index);
    }

    private int size(){
        return list.size();
    }

    @Override
    public String toString(){
        return list.toString();
    }
}
