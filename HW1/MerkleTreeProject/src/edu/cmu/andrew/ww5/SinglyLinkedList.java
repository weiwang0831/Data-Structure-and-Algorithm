/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.cmu.andrew.ww5;

/**
 *
 * @author ww5_weiwang
 */
public class SinglyLinkedList {

    ObjectNode head;
    ObjectNode tail;
    ObjectNode current;

    /**
     * Constructs a new SinglyLiinkedList object
     */
    public SinglyLinkedList() {

    }

    /**
     *
     * @param c Add a node containing the Object c to the end of the linked
     * list. No searching of the list is required. The tail pointer is used to
     * access the last node in O(1) time.
     */
    public void addAtEndNode(Object c) {
        ObjectNode newNode = new ObjectNode(c, null);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.setLink(newNode);
            tail = tail.getLink();
        }
        current = head;
    }

    /**
     *
     * @param c Add a node containing the Object c to the head of the linked
     * list.
     */
    public void addAtFrontNode(Object c) {
        if (head == null) {
            ObjectNode newNode = new ObjectNode(c, null);
            head = newNode;
            tail = newNode;
        } else {
            head = new ObjectNode(c, head);
        }
        current = head;
    }

    /**
     *
     * @return Counts the number of nodes in the list
     */
    public int countNodes() {
        int count = 0;
        ObjectNode temp = head;
        while (temp != null) {
            temp = temp.getLink();
            count++;
        }
        return count;
    }

    /**
     *
     * @return Returns the data in the tail of the list
     */
    public Object getLast() {
        return tail.getData();
    }

    /**
     *
     * @param i
     * @return Returns a reference (0 based) to the object with list index i. *
     */
    public Object getObjectAt(int i) {
        ObjectNode temp = head;
        for (int j = 0; j < i; j++) {
            temp = temp.getLink();
        }
        return temp.getData();
    }

    /**
     *
     * @return
     */
    public boolean hasNext() {
        if (current.getLink() != null) {
            return true;
        }
        return false;
    }

    /**
     *
     * @return
     */
    public Object next() {
        if (current.getLink() != null) {
            current = current.getLink();
        } else {
            current = null;
            System.out.println("No next node");
        }
        return current.getData();
    }

    /**
     *
     */
    public void reset() {
        current = head;
    }

    /**
     *
     * @return Returns the list as a String
     */
    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < this.countNodes(); i++) {
            result = result + " "+this.getObjectAt(i);
        }
        return result;
    }

    /**
     *
     * @param args
     */
    /* Comment for this application
    public static void main(String[] args) {
        //create new list
        SinglyLinkedList list = new SinglyLinkedList();

        //Add node into list
        for (int i = 0; i < 4; i++) {
            list.addAtFrontNode((char) ('D' - i));
        }
        for (int i = 0; i < 4; i++) {
            list.addAtEndNode((char) ('E' + i));
        }

        //next
        System.out.print("The node from next: ");
        while (list.hasNext()) {
            System.out.print(list.next());
        }
        System.out.println();

        //toString
        System.out.println("The list is: " + list.toString());
        System.out.println("The last node is: " + list.getLast());
        System.out.println("Node at i is: " + list.getObjectAt(2));
        System.out.println("Total Nodes: " + list.countNodes());

        //reset the list
        list.reset();
        System.out.println("After reset the next node is: " + list.next());
    }*/

}
