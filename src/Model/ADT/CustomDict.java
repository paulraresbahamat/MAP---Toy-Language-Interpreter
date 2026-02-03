package model.adt;
import exceptions.DictException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class CustomDict<K,V> implements IDict<K,V> {
    private Map<K,V> dict;

    public CustomDict(){
        dict = new HashMap<K,V>();
    }

    @Override
    public void put(K key, V value){
        dict.put(key, value);
    }

    @Override
    public V get(K key) throws DictException{
        if(!isDefined(key)) {
            throw new DictException("Key doesn't exist in the dictionary: " + key);
        }
        return dict.get(key);
    }

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public String toString(){
        return dict.toString();
    }

    @Override
    public void update(K key, V value) throws DictException{
        if(!isDefined(key)){
            throw new DictException("Key doesn't exist in the dictionary: " + key);
        }
        dict.put(key, value);
    }

    @Override
    public IDict<K, V> deepCopy() {
        CustomDict<K, V> copy = new CustomDict<>();
        for (K key : dict.keySet()) {
            copy.put(key, dict.get(key));
        }
        return copy;
    }

    @Override
    public Map<K, V> getContent() {
        return this.dict;
    }

    @Override
    public List<V> getValues(){
        return new LinkedList<V>(dict.values());
    }
}
