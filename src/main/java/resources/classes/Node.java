/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;


public class Node<T> {
    private T data;
    private Node<T> next;

    /**
     * Default Constructor for the Node Class.
     *
     */
    public Node() {
        this.next = null;
        this.data = null;
    }

    /**
     * Constructor for the Node class.
     *
     * @param data the data to add
     */
    public Node(T data) {
        this.next = null;
        this.data = data;
    }

    /**
     * Getter for the Node data.
     *
     * @return the Node data.
     */
    public T getData() {
        return this.data;
    }

    /**
     * Setter for the Node data.
     *
     * @param data the Node data
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * Getter for the next Node.
     *
     * @return the next Node.
     */
    public Node<T> getNext() {
        return this.next;
    }

    /**
     * Setter for the next Node.
     *
     * @param next the next Node.
     */
    public void setNext(Node<T> next) {
        this.next = next;
    }
}
