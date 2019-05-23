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
public class Neighbor {

    //pointer for nearest neighbor node
    //distance for the least distance
    //count for the node the function visits in the TwoDTree Class
    private double distance;
    private int count = 0;
    private Node pointer;

    /**
     *
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     *
     * @return
     */
    public int getCount() {
        return count;
    }

    /**
     *
     * @return
     */
    public double getDistance() {
        return distance;
    }

    /**
     *
     * @param distance
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     *
     * @return
     */
    public Node getPointer() {
        return pointer;
    }

    /**
     *
     * @param pointer
     */
    public void setPointer(Node pointer) {
        this.pointer = pointer;
    }

}
