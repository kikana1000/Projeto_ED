/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package resources.classes;
import resources.interfaces.*;


public class CircularArrayQueue<T> implements QueueADT<T> {

    private final T[] arrayQueue;
    private final int DEFAULT_CAPACITY = 5;
    private int front;
    private int rear;
    private int count;

    public CircularArrayQueue(int inicalSize) {
        this.arrayQueue = (T[]) (new Object[inicalSize]);
        this.count = 0;
        this.rear = 0;
        this.front = 0;
    }

    public CircularArrayQueue() {
        this.arrayQueue = (T[]) (new Object[this.DEFAULT_CAPACITY]);
        this.count = 0;
        this.rear = 0;
        this.front = 0;
    }

    @Override
    public void enqueue(T element) {
        this.arrayQueue[this.rear] = element;
        this.rear = (this.rear + 1) % this.arrayQueue.length;
        this.count++;
    }

    @Override
    public T dequeue() {
        if (this.count == 0) {
            return null;
        }

        T oldFront = this.arrayQueue[this.front];

        this.arrayQueue[this.front] = null;
        this.front = (this.front + 1) % this.arrayQueue.length;
        this.count--;

        return oldFront;
    }

    @Override
    public T first() {
        if (count == 0) {
            return null;
        }
        return this.arrayQueue[this.front];
    }

    @Override
    public boolean isEmpty() {
        return (this.count == 0);
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public String toString() {
        String tmp = "";

        for (int i = this.front; i < this.rear; i++) {
            tmp += this.arrayQueue[i].toString();
        }
        return tmp;
    }
}
