/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

/**
 *
 * @author weiwang_ww5
 */
public class MST {

    private int length;

    /**
     *
     * @param length
     */
    public MST(int length) {
        this.length = length;
    }

    /**
     * construct the MST through prim method, generate adjacency matrix
     * representation
     *
     * @param distance
     * @return 
     */
    public int[] primMST(double distance[][]) {
        //construct 3 array to store correspond values
        //store parent of each node
        int[] parent = new int[length];
        //store distance between points
        double[] minDis = new double[length];
        //store mark for each point represent fixed or not
        Boolean[] fix = new Boolean[length];

        //initate minDis to infinite,all index not fixed
        for (int i = 0; i < length; i++) {
            minDis[i] = Integer.MAX_VALUE;
            fix[i] = false;
        }
        //choose the first node as first vertice
        minDis[0] = 0;
        parent[0] = -1;
        //need to confirm length-1 edges, the last one will be there
        for (int i = 0; i < length - 1; i++) {
            //get the min value in the line, mark the index to be fixed
            int index = getMinIndex(minDis, fix);
            fix[index] = true;

            //for all points correspond to this index point
            //if the distance is not 0(not itself)
            //if the poitns has not been fixed
            //if the distance of that point is smaller than min Distance at that position
            //then this point can be updated its minimum distance
            for (int j = 0; j < length; j++) {
                if (distance[index][j] != Double.valueOf("0")
                        && fix[j] == false
                        && distance[index][j] < minDis[j]) {
                    parent[j] = index;
                    minDis[j] = distance[index][j];
                }
            }
        }
        //printMST(parent,length,distance);
        return parent;
    }

    /**
     *
     * @param minDis
     * @param fix
     * @return the index of the line that has the minimum distance
     */
    public int getMinIndex(double[] minDis, Boolean[] fix) {
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i = 0; i < length; i++) {
            if (fix[i] == false && minDis[i] < min) {
                min = minDis[i];
                index = i;
            }
        }
        return index;
    }

    /**
     *
     * @param parent
     * @param n
     * @param distance
     */
    public void printMST(int parent[], int n, double distance[][]) {
        System.out.println("Edge \tDistance");
        for (int i = 1; i < length; i++) {
            System.out.println(parent[i] + " - " + i + "\t"
                    + distance[i][parent[i]]);
        }
    }

}
