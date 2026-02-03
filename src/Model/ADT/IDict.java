package model.adt;
import exceptions.DictException;

import java.util.List;
import java.util.Map;

public interface IDict<K,V> {
    //put a new key-value pair in the dict
    void put(K key, V value);

    //get value for a given key
    V get(K key) throws DictException;

    //check if a key exists
    boolean isDefined(K key);

    //update a key's value
    void update(K key, V new_value) throws DictException;

    List<V> getValues();

    IDict<K,V> deepCopy();

    Map<K, V> getContent();
}
