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

    private double[] allDistanceResults;
    private String[] allPaths;
    private int[] vertex;
    private int resultIndex = 0;
    private int length;
    private int totalSolution;

    /**
     *
     * @param length
     */
    public Optimal(int length) {
        totalSolution = factor(length - 1);
        this.length = length;
        allDistanceResults = new double[totalSolution];
        allPaths = new String[totalSolution];
        vertex = new int[length - 1];
        for (int i = 1; i < length; i++) {
            vertex[i - 1] = i;
        }
    }

    /**
     *
     * @param dis
     * @return the array that contains the best route as array
     */
    public int[] getOptimal(double[][] dis) {
        String path = "";
        double distance = recurBruteForce(0, vertex, path, 0, dis);

        //go through every possible solution, get the one the same as lowest cost
        int optimal = 0;
        for (int i = 0; i < totalSolution; i++) {
            if (allDistanceResults[i] == distance) {
                optimal = i;
            }
        }
        int[] route=new int[length+1];
        String[] split=allPaths[optimal].split(" ");
        for(int i=0;i<length+1;i++){
            route[i]=Integer.valueOf(split[i]);
        }

        //print the result
        System.out.println("The best permutation");
        System.out.println(allPaths[optimal]);
        System.out.println();
        double optimalDis = allDistanceResults[optimal];
        optimalDis *= 0.00018939;
        System.out.println("Optimal Cycle length = " + optimalDis + " miles");
        return route;
    }

    /**
     *
     * @param index
     * @param vertex
     * @param path
     * @param curCost
     * @param dis
     * @return the optimal distance of this line
     */
    public double recurBruteForce(int index, int vertex[], String path, double curCost, double[][] dis) {
        //add the new index to original path
        path = path + index + " ";
        int newlength = vertex.length;

        //when all index already evaluated
        if (newlength == 0) {
            //add the initiate node to the last, also the distance
            allPaths[resultIndex] = path + 0;
            allDistanceResults[resultIndex] = curCost + dis[index][0];
            resultIndex++;
            return dis[index][0];
        }//when not the last index, 
        else {
            int[][] newParent = new int[newlength][newlength - 1];
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
                    newParent[i][k] = vertex[j];
                }

                //from parent to this vertex
                tmpCurCost = dis[index][vertex[i]] + curCost;
                double totalCost = recurBruteForce(vertex[i], newParent[i], path, tmpCurCost, dis) + dis[index][vertex[i]];
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
