/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author weiwang_ww5
 */
public class Tree {

    private int length;
    //Adjacency List Representation
    private LinkedList<Integer> adj[];
    //route that record how the vertex are visited
    private int[] route;

    /**
     *
     * @return
     */
    public int[] getRoute() {
        return route;
    }
    private String path;

    /**
     *
     * @return
     */
    public String getPath() {
        return path;
    }

    /**
     * constructor of the graph
     *
     * @param length
     * @initiate the adjancency list
     */
    public Tree(int length) {
        this.length = length;
        adj = new LinkedList[length];
        path = "";
        route=new int[length + 1];
        for (int i = 0; i < length; ++i) {
            adj[i] = new LinkedList();
        }
    }

    /**
     * @param startIndex
     * @construct the pre-order print of the adjecency list
     */
    public void preOrderPrint(int startIndex) {
        boolean[] visit = new boolean[length];
        for (int i = 0; i < visit.length; i++) {
            visit[i] = false;
        }
        //start to call the recursive function
        preOrderRecur(startIndex, visit, 0);
        path += startIndex;
        System.out.println(startIndex);
    }

    /**
     * @param i
     * @construct the recursive function for each vertex
     * @param l
     * @param visit
     */
    public void preOrderRecur(int l, boolean visit[], int i) {
        //print process
        visit[l] = true;
        path += l + " ";
        System.out.print(l + " ");

        //Recursive process
        //for all linked vertice to this vertex
        Iterator<Integer> iterator = adj[l].listIterator();
        while (iterator.hasNext()) {
            int index = iterator.next();
            if (visit[index] == false) {
                i++;
                preOrderRecur(index, visit, i);
            }
        }
    }

    /**
     *
     * @param vertex
     * @param addVertex
     */
    public void addLink(int vertex, int addVertex) {
        adj[vertex].add(addVertex);
    }

    /**
     *
     * @param l
     * @param distance
     * @param path1
     * @return total distance of the route
     */
    public double calTotalDistance(int l, double[][] distance, String path1) {
        //turn the path String to int[] array
        String[] split = path1.split(" ");
        for (int k = 0; k < split.length; k++) {
            route[k] = Integer.valueOf(split[k]);
        }
        //calculate the total distance
        double total = 0;
        for (int i = 0; i < route.length - 1; i++) {
            total += distance[route[i]][route[i + 1]];
        }
        return total * 0.00018939;
    }

}
