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
public class Optimal {

    private double optimalDis;
    private String optimalPath;
    private int length;

    /**
     *
     * @param length
     */
    public Optimal(int length) {
        this.length = length;
        optimalDis = Double.MAX_VALUE;
        optimalPath = "";
    }

    /**
     * @equation: the basic fomula will be g(i,s)=mink<-s{Cik+g(k,s-{k})}
     * @param distance
     */
    public void getOptimal(double[][] distance) {
        String path = "";
        int[] parent = new int[length - 1];
        for (int i = 1; i < length; i++) {
            parent[i - 1] = i;
        }
        //start recursive process
        recurOptimal(0, parent, path, 0, distance);

        //print the result
        System.out.println("The best permutation");
        System.out.println(optimalPath);
        System.out.println();
        optimalDis *= 0.00018939;
        System.out.println("Optimal Cycle length = " + optimalDis + " miles");
    }

    /**
     *
     * @param index
     * @param parent
     * @param path
     * @param curCost
     * @param distance
     * @return the min cost, also set the optimal distance and optimal path
     */
    public double recurOptimal(int index, int[] parent, String path, double curCost, double[][] distance) {
        //recursively add the selected index into the path
        path += index + " ";
        int newLength = parent.length;
        double newCurCost;

        //when current cost higher than optimal, don't explore
        if (curCost > optimalDis) {
            return 0;
        } //when all index already evaluated
        else if (newLength == 0) {
            //add the distance of last vertex to start index to current cost
            newCurCost = curCost + distance[index][0];
            if (newCurCost < optimalDis) {
                optimalDis = newCurCost;
                //add the start point 0 as the last vertex in the node
                optimalPath = path + "0";
            }
            return distance[index][0];
        } //else explore better route
        else {
            int[][] newParent = new int[newLength][newLength - 1];
            double minCost = Double.MAX_VALUE;

            //for each index
            for (int i = 0; i < newLength; i++) {
                //for each vertex that link to this index
                for (int j = 0, k = 0; j < newLength; j++, k++) {
                    //create k in case it doesn't plus 1 on unneccessary index
                    if (j == i) {
                        k--;
                        continue;
                    }
                    newParent[i][k] = parent[j];
                }

                //from parent to this vertex
                newCurCost = distance[index][parent[i]] + curCost;
                double totalCost = recurOptimal(parent[i], newParent[i], path, newCurCost, distance) + distance[index][parent[i]];
                if (totalCost < minCost) {
                    minCost = totalCost;
                }
            }
            return minCost;
        }
    }

}
