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
public class Stack {

    private Node front;

    /**
     *
     */
    public Stack() {
        this.front = null;
    }

    /**
     *
     * @return boolean whether the stack is empty
     */
    public boolean isEmpty() {
        return this.front == null;
    }

    /**
     *
     * @param newNode
     * @return pre-condition, newNode is not null, post condition: true when
     * push success, false when not succeed
     */
    public boolean push(Node newNode) {
        if (this.front == null) {
            this.front = newNode;
        } else {
            Node frontNode = this.front;
            this.front = newNode;
            this.front.link = frontNode;
        }
        return true;
    }

    /**
     *
     * @return pre-condition: a stack is constructed post-condition: return the
     * frontNode of stack, which includes link so the whole stack can be tracked
     */
    public Node pop() {
        if (this.front == null) {
            return null;
        }
        Node frontNode = this.front;
        this.front = this.front.link;
        return frontNode;
    }
}
