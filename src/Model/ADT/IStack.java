package Model.ADT;
import java.util.List;
import Exceptions.StackException;

public interface IStack<T> {
    void push(T item);
    T pop() throws StackException;
    T top() throws StackException;
    boolean isEmpty();
    int size();
    List<T> getList();
}
