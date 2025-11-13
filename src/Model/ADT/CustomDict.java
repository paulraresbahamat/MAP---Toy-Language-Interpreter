package Model.ADT;
import Exceptions.DictException;
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
}
