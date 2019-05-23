/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodtreedriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author weiwang_ww5
 */
public final class TwoDTree {

    Node rootNode = new Node(null, null, null, null, null);

    /**
     *
     * @param crimeDataLocation
     * @throws IOException pre-condition: The String crimeDataLocation contains
     * the path to a file formatted in the exact same way as CrimeLatLonXY.csv
     * post-condition: The 2d tree is constructed and may be printed or queried.
     */
    public TwoDTree(String crimeDataLocation) throws IOException {
        List<String> dataList = readFile(crimeDataLocation);

        int length = dataList.size();

        //read lines as value, store seperate information in array
        rootNode.value = Arrays.asList(dataList.get(1).split(","));
        rootNode.leftNode = null;
        rootNode.rightNode = null;

        //obtain value of the current line of value
        for (int i = 2; i < length; i++) {
            String wholeValue = dataList.get(i);
            List<String> totalValue = Arrays.asList(wholeValue.split(","));
            Node newNode = new Node(null, null, totalValue, null, null);
            Node.addNode(rootNode, newNode, 0);
        }
    }

    /**
     * Empty Constructor
     */
    public TwoDTree() {
    }

    /**
     *
     * @param crimeDataLocation
     * @return
     * @throws IOException read file into fileReader and store in the list
     * pre-condition: The file is exist in project file post-condition: A string
     * list is returned with every line represent everyline in the file
     */
    public List<String> readFile(String crimeDataLocation) throws IOException {
        //Use the list to store every line of the file
        List<String> list = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(crimeDataLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            //if there are following lines, add to the end of list
            while ((line = bufferedReader.readLine()) != null) {
                list.add(line);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TwoDTree.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    /**
     * pre-condition: The 2d tree has been constructed post-condition: The 2d
     * tree is displayed with a pre-order traversal.
     *
     */
    public void preOrderPrint() {
        TwoDTree newTree = new TwoDTree();
        System.out.println(rootNode.value);
        //if leftNode is not null, get pointer point to the left and recursive
        if (rootNode.leftNode != null) {
            newTree.rootNode = rootNode.leftNode;
            newTree.preOrderPrint();
        }
        //if leftNode is not null, get pointer point to the left and recursive
        if (rootNode.rightNode != null) {
            newTree.rootNode = rootNode.rightNode;
            newTree.preOrderPrint();
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed. post-condition: The 2d
     * tree is displayed with an in-order traversal Run time: it is big theta
     * (N), every recursive only call the print, and it runs once each node
     */
    public void inOrderPrint() {
        TwoDTree newTree = new TwoDTree();
        if (rootNode.leftNode != null) {
            newTree.rootNode = rootNode.leftNode;
            newTree.inOrderPrint();
        }
        System.out.println(rootNode.value);
        if (rootNode.rightNode != null) {
            newTree.rootNode = rootNode.rightNode;
            newTree.inOrderPrint();
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed. post-condition: The 2d
     * tree is displayed with a post-order traversal.
     */
    public void postOrderPrint() {
        TwoDTree newTree = new TwoDTree();
        if (rootNode.leftNode != null) {
            newTree.rootNode = rootNode.leftNode;
            newTree.postOrderPrint();
        }
        if (rootNode.rightNode != null) {
            newTree.rootNode = rootNode.rightNode;
            newTree.inOrderPrint();
        }
        System.out.println(rootNode.value);
    }

    /**
     * pre-condition: The 2d tree has been constructed. post-condition: The 2d
     * tree is displayed with a level-order traversal. Run time: it is big theta
     * (N), every step dequeue and enqueue, still in N level operation
     */
    public void levelOrderPrint() {
        LinkedQueue queue = new LinkedQueue();
        queue.enqueue(rootNode);
        while (queue.isEmpty() == false) {
            //p get the head of the queue
            Node p = queue.dequeue();
            System.out.println(p.value);
            if (p.leftNode != null) {
                //put leftNode into the queue, and recall the method
                queue.enqueue(p.leftNode);
            }
            if (p.rightNode != null) {
                //put rightNode into the queue, and recall the method
                queue.enqueue(p.rightNode);
            }
        }
    }

    /**
     * pre-condition: The 2d tree has been constructed post-condition: The 2d
     * tree is displayed with a reverse level-order traversal.
     */
    public void reverseLevelOrderPrint() {
        //queue used to store rootNode first and child
        //stack used to get child out first instead of get parents
        LinkedQueue queue = new LinkedQueue();
        Stack stack = new Stack();

        //process to put rootNode tree into queue
        queue.enqueue(rootNode);
        while (queue.isEmpty() == false) {
            Node p = queue.dequeue();
            stack.push(p);
            if (p.leftNode != null) {
                queue.enqueue(p.leftNode);
            }
            if (p.rightNode != null) {
                queue.enqueue(p.rightNode);
            }
        }

        //after queue is in sequence, get node out from last one, 
        //which can achieve reverse print
        while (stack.isEmpty() == false) {
            Node s = stack.pop();
            System.out.println(s.value);
        }
    }

    /**
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return pre-condition: The 2d tree has been constructed post-condition: A
     * list of 0 or more crimes is returned. These crimes occurred within the
     * rectangular range specified by the four parameters. The pair (x1, y1) is
     * the left bottom of the rectangle. The pair (x2, y2) is the top right of
     * the rectangle.
     */
    public ListOfCrimes findPointsInRange(double x1, double y1, double x2, double y2) {
        ListOfCrimes list = new ListOfCrimes();
        //intrigue the recursive
        findPoint(rootNode, list, 0, x1, y1, x2, y2);
        return list;
    }

    /**
     *
     * @param x1
     * @param y1
     * @return pre-condition: the 2d tree has been constructed. The (x1,y1) pair
     * represents a point in space near Pittsburgh and in the state plane
     * coordinate system. * post-condition: the distance in feet to the nearest
     * node is returned in Neighbor. In addition, the Neighbor object contains a
     * reference to the nearest neighbor in the tree.
     */
    public Neighbor nearestNeighbor(double x1, double y1) {
        Neighbor neighbor = new Neighbor();
        findNeighbor(rootNode, neighbor, 0, x1, y1, 10000, 0);
        return neighbor;
    }

    /**
     *
     * @param x
     * @param y
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return pre-condition: 3 points with 6 value of coordinates
     * post-condition: return true if (x,y)within the range of (x1, y1) and (x2,
     * y2)
     */
    public boolean withinRange(double x, double y, double x1, double y1, double x2, double y2) {
        return (x >= x1 && x <= x2 && y >= y1 && y <= y2);
    }

    /**
     *
     * @param rootNode
     * @param list
     * @param level
     * @param x1
     * @param y1
     * @param x2
     * @param y2 pre-condition: 2d tree is constructed post-condition: the
     * ListOfCrimes are added if the node is within the range of (x1, y1) and
     * (x2, y2) where 1 represent lower left point, 2 equals right upper point
     */
    public void findPoint(Node rootNode, ListOfCrimes list, int level,
            double x1, double y1, double x2, double y2) {

        //Based on the tree level, check it is even of odd
        int evenOrOdd = 0;
        if (level % 2 == 1) {
            evenOrOdd = 1;
        }
        //if the node is not null, conduct the function
        if (rootNode != null) {
            list.setCount(list.getCount() + 1);
            double x = Double.valueOf(rootNode.value.get(0));
            double y = Double.valueOf(rootNode.value.get(1));

            //check if the point is within the range, if yes, add to the list
            Boolean flag = withinRange(x, y, x1, y1, x2, y2);
            if (flag == true) {
                list.add(rootNode);
            }
            //if the level is even number, compare x
            if (evenOrOdd == 0) {
                //when the region is on the left, search leftnode
                if (x >= x2) {
                    level++;
                    findPoint(rootNode.leftNode, list, level, x1, y1, x2, y2);
                } else if (x < x1) {
                    //when on right, search right node
                    level++;
                    findPoint(rootNode.rightNode, list, level, x1, y1, x2, y2);
                } else {
                    level++;
                    //if in the middle of x1 and x2, search both side
                    findPoint(rootNode.leftNode, list, level, x1, y1, x2, y2);
                    findPoint(rootNode.rightNode, list, level, x1, y1, x2, y2);
                }
                //if the level is odd number, compare y
            } else {
                if (y <= y1) {
                    //if the node is above the region, check the left node(below point)
                    level++;
                    findPoint(rootNode.rightNode, list, level, x1, y1, x2, y2);
                } else if (y > y2) {
                    //if the node is below the region, check the right node(above points)
                    level++;
                    findPoint(rootNode.leftNode, list, level, x1, y1, x2, y2);
                } else {
                    level++;
                    //if in the middle of y1 and y2, search both side
                    findPoint(rootNode.leftNode, list, level, x1, y1, x2, y2);
                    findPoint(rootNode.rightNode, list, level, x1, y1, x2, y2);
                }
            }
        }
    }

    /**
     *
     * @param rootNode
     * @param neighbor
     * @param level
     * @param x1
     * @param y1
     * @param nearest
     * @param absDistance pre-condition: 2d tree is constructed post-condition:
     * the neighbor object is returned with distance and nearest node，given the
     * target point is (x1, y1) The method is recursively called
     *
     */
    public void findNeighbor(Node rootNode, Neighbor neighbor, int level, double x1, double y1, double nearest, double absDistance) {
        //Based the level of the tree, if even, compare x, if odd, compare y
        int evenOrOdd = 0;
        Node nearestNode = new Node();
        if (level % 2 == 1) {
            evenOrOdd = 1;
        }
        //if the node is not null, process the following
        if (rootNode != null) {
            //start exam the node in the tree
            double currentDis = getDis(rootNode, x1, y1);

            //if current distance smaller than nearest one, put the value into nearest pointer
            if (currentDis < nearest) {
                nearest = currentDis;
                nearestNode = rootNode;
                //set the variable of neighbor
                neighbor.setDistance(nearest);
                neighbor.setPointer(nearestNode);
            }

            //get vertical distance, compare with the upper node 
            double x = Double.valueOf(rootNode.value.get(0));
            double y = Double.valueOf(rootNode.value.get(1));
            double xDis = Math.abs(x1 - x);;
            double yDis = Math.abs(y1 - y);

            //if level is even number, compare x
            if (evenOrOdd == 0) {
                if (x <= x1) {
                    level++;
                    findNeighbor(rootNode.rightNode, neighbor, level, x1, y1, nearest, xDis);
                    //compare the smallest value with vertical distance, if small, search another side
                    if (xDis < nearest) {
                        findNeighbor(rootNode.leftNode, neighbor, level, x1, y1, nearest, xDis);
                    }
                } else {
                    level++;
                    findNeighbor(rootNode.leftNode, neighbor, level, x1, y1, nearest, xDis);
                    //compare the smallest value with vertical distance, if small, search another sid
                    if (xDis < nearest) {
                        findNeighbor(rootNode.rightNode, neighbor, level, x1, y1, nearest, xDis);
                    }
                }
            } else {
                //if level is odd number. compare y
                if (y <= y1) {
                    level++;
                    findNeighbor(rootNode.rightNode, neighbor, level, x1, y1, nearest, yDis);
                    //compare the smallest value with vertical distance, if small, search another side
                    if (yDis < nearest) {
                        findNeighbor(rootNode.leftNode, neighbor, level, x1, y1, nearest, xDis);
                    }
                } else {
                    level++;
                    findNeighbor(rootNode.leftNode, neighbor, level, x1, y1, nearest, yDis);
                    //compare the smallest value with vertical distance, if small, search another side
                    if (yDis < nearest) {
                        findNeighbor(rootNode.rightNode, neighbor, level, x1, y1, nearest, xDis);
                    }
                }
            }
            neighbor.setCount(neighbor.getCount() + 1);
        }

    }

    /**
     *
     * @param thisNode
     * @param x1
     * @param y1
     * @return pre-condition: node is not null post-condition: return distance
     * between the point the node represent and the point (x1, y1)
     */
    public double getDis(Node thisNode, double x1, double y1) {
        double x = Double.valueOf(thisNode.value.get(0));
        double y = Double.valueOf(thisNode.value.get(1));
        double dis = Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
        return dis;
    }
}
