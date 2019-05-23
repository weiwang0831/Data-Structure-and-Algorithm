/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodtreedriver;

import java.util.List;

/**
 *
 * @author weiwang_ww5
 */
public class Node {

    /**
     * leftNode
     */
    public Node leftNode = null;

    /**
     * rightNode
     */
    public Node rightNode = null;

    /**
     * value of all information for this node, in array
     */
    public List<String> value = null;

    /**
     * link point to the next node in queue
     */
    public Node link = null;

    /**
     * pointer point to next node in stack
     */
    public Node stackNext=null;

    /**
     *
     * @param left
     * @param right
     * @param value
     * @param link
     * @param stackNext
     * pre-condition: a 2d tree is used here, node is element of tree
     * post-condition: initiate the node
     */
    public Node(Node left, Node right, List<String> value, Node link, Node stackNext) {
        this.leftNode = left;
        this.rightNode = right;
        this.value = value;
        this.link = link;
        this.stackNext = stackNext;
    }

    /**
     * empty constructor for Node Class
     */
    public Node() {
    }

    /**
     *
     * @param rootNode
     * @param newNode
     * @param level
     * @return
     * pre-condition: new data exist, represent newNode
     * post-condition: Node returned with the new Node added
     */
    public static Node addNode(Node rootNode, Node newNode, int level) {
        //Based the level of the tree, if even, compare x, if odd, compare y
        int evenOrOdd = 0;
        if (level % 2 == 1) {
            evenOrOdd = 1;
        }
        //if new value smaller than current node value, goes to the left
        if (Double.valueOf(newNode.value.get(evenOrOdd))
                <= Double.valueOf(rootNode.value.get(evenOrOdd))) {
            if (rootNode.leftNode == null) {
                rootNode.leftNode = newNode;
            } else {
                level++;
                //if leftNode is not null, recursively call until null left node.
                rootNode = addNode(rootNode.leftNode, newNode, level);
            }
        } else {
            //if new value larger than current node value, goes to the right
            if (rootNode.rightNode == null) {
                rootNode.rightNode = newNode;
            } else {
                level++;
                //if rightNode is not null, recursively call until null right node.
                rootNode = addNode(rootNode.rightNode, newNode, level);
            }
        }

        return rootNode;
    }

}
