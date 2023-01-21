/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;


import resources.interfaces.*;

public class ArrayStack<T> implements StackADT<T> {

    private final int DEFAULT_CAPACITY = 100;
    private int top;
    private T[] stack;

    /**
     * Constructor for the ArrayStack Class.
     *
     * @param inicialCapacity the inicial size of the array
     */
    public ArrayStack(int inicialCapacity) {
        this.top = 0;
        this.stack = (T[]) (new Object[inicialCapacity]);
    }

    /**
     * Default Constructor for the ArrayStack Class.
     *
     */
    public ArrayStack() {
        this.top = 0;
        this.stack = (T[]) (new Object[this.DEFAULT_CAPACITY]);
    }

    private void expandArray() {
        T[] tmp = (T[]) (new Object[this.stack.length * 2]);

        System.arraycopy(this.stack, 0, tmp, 0, this.top);

        this.stack = tmp;
    }

    /**
     * Adds one element to the top of this stack.
     *
     * @param element element to be pushed onto stack
     */
    @Override
    public void push(T element) {
        if (this.top == this.stack.length) {
            this.expandArray();
        }

        this.stack[this.top] = element;
        this.top++;
    }

    /**
     * Removes and returns the top element from this stack.
     *
     * @return T element removed from the top of the stack
     */
    @Override
    public T pop() {
        if (this.top > 0) {
            T tmp = this.stack[this.top - 1];
            this.stack[this.top--] = null;
            return tmp;
        }
        return null;
    }

    /**
     * Returns without removing the top element of this stack.
     *
     * @return T element on top of the stack
     */
    @Override
    public T peek() {
        if (this.top > 0) {
            return this.stack[this.top - 1];
        }
        return null;
    }

    /**
     * Returns true if this stack contains no elements.
     *
     * @return boolean whether or not this stack is empty
     */
    @Override
    public boolean isEmpty() {
        return (this.top == 0);
    }

    /**
     * Returns the number of elements in this stack.
     *
     * @return int number of elements in this stack
     */
    @Override
    public int size() {
        return this.top;
    }

    /**
     * Returns a string representation of this stack.
     *
     * @return String representation of this stack
     */
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < this.top; i++) {
            str += this.stack[i].toString() + "\n";
        }
        return str;
    }
}
