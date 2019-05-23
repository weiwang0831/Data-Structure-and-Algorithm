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

    private double finalResults[];
    private String[] paths;
    private int[] vertex;
    private int counter = 0;
    private int length;
    private int totalSolution;

    /**
     *
     * @param length
     */
    public Optimal(int length) {
        totalSolution = factor(length - 1);
        this.length = length;
        finalResults = new double[totalSolution];
        paths = new String[totalSolution];
        vertex = new int[length - 1];
        for (int i = 1; i < length; i++) {
            vertex[i - 1] = i;
        }
    }

    /**
     *
     * @param dis
     * @print optimal distance, optimal path
     * @call the recursive function
     */
    public void getOptimal(double[][] dis) {
        String path = "";
        //start recursive
        double distance = recurBruteForce(0, vertex, path, 0, dis);

        //go through every possible solution, get the one the same as lowest cost
        int optimal = 0;
        for (int i = 0; i < totalSolution; i++) {
            if (finalResults[i] == distance) {
                optimal = i;
            }
        }

        //print the result
        System.out.println("The best permutation");
        System.out.println(paths[optimal]);
        System.out.println();
        double optimalDis = finalResults[optimal];
        optimalDis *= 0.00018939;
        System.out.println("Optimal Cycle length = " + optimalDis + " miles");

    }

    /**
     *
     * @param index
     * @param vertex
     * @param path
     * @param curCost
     * @param dis
     * @return
     */
    public double recurBruteForce(int index, int vertex[], String path, double curCost, double[][] dis) {
        //add the new index to original path
        path = path + index + " ";
        int newlength = vertex.length;

        //when all index already evaluated
        if (newlength == 0) {
            //add the initiate node to the last, also the distance
            paths[counter] = path + 0;
            //store the distance for this route
            finalResults[counter] = curCost + dis[index][0];
            //add the counter to next possible solution
            counter++;
            return dis[index][0];
        }//when not the last index, 
        else {
            int[][] newVertex = new int[newlength][newlength - 1];
            double minCost = Double.MAX_VALUE;
            double tmpCurCost;

            //for each index
            for (int i = 0; i < newlength; i++) {
                //for each vertex that link to this index
                for (int j = 0, k = 0; j < newlength; j++, k++) {
                    //create k in case it doesn't plus 1 on unneccessary index
                    if (j == i) {
                        k--;
                        continue;
                    }
                    //everytime through this step the length is subtract
                    newVertex[i][k] = vertex[j];
                }

                //from parent to this vertex
                tmpCurCost = dis[index][vertex[i]] + curCost;
                double totalCost = recurBruteForce(vertex[i], newVertex[i], path, tmpCurCost, dis) + dis[index][vertex[i]];
                if (totalCost < minCost) {
                    minCost = totalCost;
                }
            }
            return minCost;
        }
    }

    /**
     *
     * @param n
     * @return number of solutions that for all solutions
     */
    public int factor(int n) {
        if (n <= 1) {
            return 1;
        } else {
            return n * factor(n - 1);
        }
    }
}
