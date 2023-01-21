/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;
import resources.interfaces.*;

public class DllNode<T> {

    private T data;
    private DllNode<T> prev;
    private DllNode<T> next;

    /**
     * Default DllNode class constructor
     */
    public DllNode() {
        this.next = null;
        this.prev = null;
    }

    /**
     * DllNode class constructor
     *
     * @param data
     */
    public DllNode(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

    /**
     * Getter for the data
     *
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * Setter for the data
     *
     * @param data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Getter for the previous Node.
     *
     * @return the previous Node.
     */
    public DllNode<T> getPrev() {
        return this.prev;
    }

    /**
     * Setter for the previous Node.
     *
     * @param prev the previous Node
     */
    public void setPrev(DllNode<T> prev) {
        this.prev = prev;
    }

    /**
     * Getter for the next Node.
     *
     * @return the next Node
     */
    public DllNode<T> getNext() {
        return this.next;
    }

    /**
     * Setter for the next Node.
     *
     * @param next the next Node
     */
    public void setNext(DllNode<T> next) {
        this.next = next;
    }
}
