/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodtreedriver;

/**
 *
 * @author weiwang_ww5
 */
public class LinkedQueue {

    //two pointer for head and tail
    private Node front, rear;
    private int size;

    /**
     *
     */
    public LinkedQueue() {
        this.front = this.rear = null;
    }

    /**
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return front == null && rear == null;
    }

    /**
     *
     * @param data
     * @return pre-condition: queue constructed post-condition: return true when
     * data successfully added
     */
    public boolean enqueue(Node data) {
        if (this.front == null) {
            this.front = data;
        } else {
            this.rear.link = data;
        }
        this.rear = data;
        size++;
        return true;
    }

    /**
     *
     * @return pre-condition: queue constructed post-condition: return Node that
     * delete the front element
     */
    public Node dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        Node frontNode = this.front;
        this.front = this.front.link;
        if (this.front == null) {
            this.rear = null;
        }
        size--;
        return frontNode;
    }
}
