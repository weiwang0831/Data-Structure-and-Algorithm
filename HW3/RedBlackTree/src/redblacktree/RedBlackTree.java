/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author weiwang_ww5
 */
public class RedBlackTree {

    private Node root;

    private Node nil = null;
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        RedBlackTree tree = new RedBlackTree();

        //create the map for keyset and values
        Map<String, BigInteger> test = new HashMap<String, BigInteger>();
        for (int i = 0; i < 20; i++) {
            String key = String.valueOf(i);
            String value = String.valueOf(i * 120000);
            test.put(key, new BigInteger(value));
        }
        //insert key into tree based on values
        for (int i = 1; i < 16; i++) {
            String key = String.valueOf(i);
            tree.insert(key, test.get(key));
        }
        //print inOrder
        System.out.println("in order traversal");
        tree.inOrderTraversal(tree.root);
        //print preOrder
        System.out.println("pre order traversal");
        tree.preOrderTraversal(tree.root);

    }

    /**
     *
     * @param key
     * @param value insert (key, value) into the tree
     */
    public void insert(String key, BigInteger value) {
        int cmpr; //variable for comparison
        //create new node for insert key and value
        Node z = new Node(key, value, nil, nil, RED, null);
        Node y = nil;
        Node x = this.root; //set x as root node

        if (root == nil) {
            this.root = z;
        } else {
            //insert into tree as binary tree
            while (x != nil) {
                y = x;
                cmpr = z.getKey().compareTo(x.getKey());
                //if new value small, go to left
                if (cmpr < 0) {
                    x = x.getLeftNode();
                } else {
                    //if new value bigger, go to right
                    x = x.getRightNode();
                }
            }

            z.setParent(y);

            if (y == null) {
                this.root = z;
            } else {
                cmpr = key.compareTo(y.getKey());
                if (cmpr < 0) {
                    y.setLeftNode(z);
                } else if (cmpr > 0) {
                    y.setRightNode(z);
                } else {
                    y.setValue(z.getValue());
                }
            }
        }

        z.setColor(RED);
        insertFixup(z);
    }

    /**
     *
     * @param node
     * @param key
     * @return search based on the key value, return the corresponding node
     * value The insert() method places a data item into the tree. It executes
     * the following algorithm from CLR
     */
    public BigInteger search(Node node, String key) {
        while (node != nil) {
            int cmp = key.compareTo(node.getKey());
            if (cmp < 0) {
                node = node.getLeftNode();
            } else if (cmp > 0) {
                node = node.getRightNode();
            } else {
                return node.getValue();
            }
        }
        return null;
    }

    /**
     *
     * @param z fix the node into the tree that is not violate red black tree
     * standard Fixing up the tree so that Red Black Properties are preserved
     */
    public void insertFixup(Node z) {
        //if parent exist and the grandparent is red
        while (z.getParent() != null && isRed(z.getParent())) {
            Node parent = z.getParent();

            if (parent == parent.getParent().getLeftNode() && parent.getParent() != nil) {
                //if parent is left child of grandparent
                Node uncle = parent.getParent().getRightNode();

                //CASE 1: if uncle is red
                if (uncle != null && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(parent.getParent());
                    z = parent.getParent();
                    continue;
                }

                //CASE 2: uncle is black
                //new node is on the right of parent
                if (parent.getRightNode() == z) {
                    z = parent;
                    leftRotate(z);
                }
                //CASE 3: uncle is black
                //new node is on the left of parent
                setBlack(parent);
                setRed(parent.getParent());
                rightRotate(parent.getParent());

            } else {
                //parent is grandoarent's right child
                //CASE 1: uncle is red
                Node uncle = parent.getParent().getLeftNode();
                if (uncle != null && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(parent.getParent());
                    z = parent.getParent();
                    continue;
                }
                //CASE 2: uncle is black, node is left of parent
                if (parent.getLeftNode() == z) {
                    z = parent;
                    rightRotate(z);
                }
                //CASE 3: uncle is clack, node is right of parent
                setBlack(parent);
                setRed(parent.getParent());
                leftRotate(parent.getParent());

            }
        }
        setBlack(this.root);
    }

    /**
     *
     * @param tree Perfrom an inorder traversal of the tree. The
     * inOrderTraversal(RedBlackNode) method is recursive and displays the
     * content of the tree. It makes calls on System.out.println(). This method
     * would normally be private.
     */
    public void inOrderTraversal(Node tree) {
        if (tree.getLeftNode() != nil) {
            inOrderTraversal(tree.getLeftNode());
        }
        System.out.println("Key: " + tree.getKey() + " value: " + tree.getValue());
        if (tree.getRightNode() != nil) {
            inOrderTraversal(tree.getRightNode());
        }
    }

    /**
     *
     * @param tree Perform a reverseOrder traversal of the tree. The
     * reverseOrderTraversal(RedBlackNode) method is recursive and displays the
     * content of the tree in reverse order. This method would normally be
     * private.
     */
    public void preOrderTraversal(Node tree) {
        if (tree != nil) {
            System.out.println("Key: " + tree.getKey() + " value: " + tree.getValue());
            preOrderTraversal(tree.getLeftNode());
            preOrderTraversal(tree.getRightNode());
        }
    }

    /**
     *
     * @param x leftRotate tree The insert() method places a data item into the
     * tree. It executes the following algorithm from CLR
     */
    public void leftRotate(Node x) {
        if (x.getRightNode() != nil && this.root.getParent() == null) {
            //set right child of x is y
            Node y = x.getRightNode();

            //set y.left to x.right
            //if y.left is not empty, let x be y.left's parent
            x.setRightNode(y.getLeftNode());
            if (y.getLeftNode() != nil) {
                y.getLeftNode().setParent(x);
            }
            //set x's parent to y's parent
            y.setParent(x.getParent());

            if (x.getParent() == nil) {
                //if x's parent is nil, set y as root
                this.root = y;
            } else {
                //if x isparent.left, let y be parents' left
                if (x.getParent().getLeftNode() == x) {
                    x.getParent().setLeftNode(y);
                } else {
                    //if x is right child, let y be x's parents' tight child
                    x.getParent().setRightNode(y);
                }
            }

            y.setLeftNode(x);
            x.setParent(y);
        }

    }

    /**
     *
     * @param n right Rotate tree The insert() method places a data item into
     * the tree. It executes the following algorithm from CLR
     */
    public void rightRotate(Node n) {
        //set x be n's left child
        Node x = n.getLeftNode();

        //set n.left=x.right
        //if x.right is not nil, let n be x's right child's parent
        n.setLeftNode(x.getRightNode());
        if (x.getRightNode() != nil) {
            x.getRightNode().setParent(n);
        }
        //let x's parent equal n's parent
        x.setParent(n.getParent());

        //if n's fater is nil, set x as root
        if (n.getParent() == nil) {
            this.root = x;
        } else {
            //if n is on the right, set x be n's parent's right child
            if (n == n.getParent().getLeftNode()) {
                n.getParent().setLeftNode(x);
            } else {
                //if n is on the left, set x be n's parent left child
                n.getParent().setRightNode(x);
            }
        }
        x.setRightNode(n);
        n.setParent(x);
    }

    /**
     *
     * @param n
     */
    public void setBlack(Node n) {
        n.setColor(BLACK);
    }

    /**
     *
     * @param n
     */
    public void setRed(Node n) {
        n.setColor(RED);
    }

    /**
     *
     * @param n
     * @return
     */
    public boolean isRed(Node n) {
        return ((n != null) && (n.isColor() == RED));
    }

    /**
     *
     * @return
     */
    public Node getRoot() {
        return root;
    }

    /**
     *
     * @param root
     */
    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     *
     * @return
     */
    public Node getNil() {
        return nil;
    }

    /**
     *
     * @param nil
     */
    public void setNil(Node nil) {
        this.nil = nil;
    }

}

class Node {

    private String key;
    private BigInteger value;
    private Node leftNode;
    private Node rightNode;
    private boolean color;
    private Node parent;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public BigInteger getValue() {
        return value;
    }

    public void setValue(BigInteger value) {
        this.value = value;
    }

    public Node(String key, BigInteger value, Node leftNode, Node rightNode, boolean color, Node parent) {
        this.key = key;
        this.value = value;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.color = color;
        this.parent = parent;
    }

}
