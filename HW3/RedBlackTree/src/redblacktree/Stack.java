/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

/**
 *
 * @author weiwang_ww5
 */
public class Stack {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Stack stack = new Stack(200);
        //push 500 object into stack
        for (int i = 1; i <= 500; i++) {
            stack.arr = stack.push(i);
        }
        //display the stack
        stack.display();
    }

    private Object[] arr = null;
    private int top;
    private int size;

    /**
     *
     * @param size
     */
    public Stack(int size) {
        this.arr = new Object[size];
        this.top = -1;
        this.size = size;
    }

    /**
     *
     * @return boolean if the stack is full true for full false for not full
     */
    public boolean isFull() {
        if (this.size == this.top + 1) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     *
     * @return Object that is on the top of the stack and remove it from the
     * stack
     */
    public Object pop() {
        if (!isEmpty()) {
            Object result = this.arr[top];
            this.arr[top] = null;
            this.top--;
            return result;
        } else {
            //System.out.println("The stack is already empty, cannot pop");
            return null;
        }
    }

    /**
     *
     * @param o
     * @return Object array that contains result after pushing new element in to
     * the stack
     * @worstcase: when pushing the object, the array continuously needs to
     * enlarge, the complexity will be Big Theta(2^N)
     * @bestcase: every time the array length is enough, there will be N
     * operations, therefore the theta will be Big Theta(N)
     */
    public Object[] push(Object o) {
        //if array is not full, put new value on the top of the stack
        if (!isFull()) {
            this.arr[++top] = o;
            return this.arr;
        } else {
            //if array is full, double size and copy the original values into the new one
            System.out.println("Stack is full, the array will double sized");
            size = size * 2;
            Object[] newArr = new Object[size];
            for (int i = 0; i < size / 2; i++) {
                newArr[i] = arr[i];
            }
            //return new array
            return newArr;
        }
    }

    /**
     * display the stack content from the top
     */
    public void display() {
        for (int i = 0; i <= top; i++) {
            System.out.println(arr[i] + " ");
        }
        System.out.println();
    }

    /**
     *
     * @return
     */
    public Object[] getArr() {
        return arr;
    }

    /**
     *
     * @param arr
     */
    public void setArr(Object[] arr) {
        this.arr = arr;
    }

    /**
     *
     * @return
     */
    public int getTop() {
        return top;
    }

    /**
     *
     * @param top
     */
    public void setTop(int top) {
        this.top = top;
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @param size
     */
    public void setSize(int size) {
        this.size = size;
    }

}
