package model.adt;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import exceptions.StackException;

public class CustomStack<T> implements IStack<T> {
    private Stack<T> stack;

    public CustomStack(){
        stack = new Stack<>();
    }

    @Override
    public void push(T elem){
        stack.push(elem);
    }

    @Override
    public T pop() throws StackException{
        if(isEmpty()){
            throw new StackException("The stack is empty.");
        }
        return stack.pop();
    }

    @Override
    public T top() throws StackException {
        if (isEmpty()) {
            throw new StackException("The stack is empty.");
        }
        return stack.peek();
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public String toString(){
        return stack.toString();
    }

    @Override
    public int size(){
        return stack.size();
    }

    @Override
    public List<T> getList(){
        List<T> list = new ArrayList<>(stack);
        Collections.reverse(list);
        return list;
    }
}
