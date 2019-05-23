/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 */
public class TSP {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     * @throws java.text.ParseException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {
        // TODO code application logic here
        ArrayList file = readFile("CrimeLatLonXY1990.csv");

        //userInput process
        Scanner input = new Scanner(System.in);
        System.out.println("Enter start date");
        String date1 = input.nextLine();
        System.out.println("Enter end date");
        String date2 = input.nextLine();
        //get data in range
        List dataInRange = getDataInRange(file, date1, date2);
        System.out.println("Crime records between " + date1 + " and " + date2);
        for (Object line : dataInRange) {
            System.out.println(line);
        }

        //turn data into 2d-array storing distance
        double[][] distance = turnToDistance(dataInRange);
        //construct the MST tree
        MST tree = new MST(dataInRange.size());
        int[] parent = tree.primMST(distance);
        //construct tree based on parent relationship
        Tree t = new Tree(dataInRange.size());
        for (int i = 1; i < parent.length; i++) {
            t.addLink(parent[i], i);
        }
        System.out.println();
        System.out.println("Hamiltonian Cycle (not necessarily optimum): ");
        t.preOrderPrint(0);
        //calculate the length of the cycle and print the distance
        System.out.print("Length Of cycle: ");
        System.out.println(t.calTotalDistance(0, distance, t.getPath()) + " miles");
    }

    /**
     *
     * @param file
     * @return an arraylist contains data in each line of the file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList readFile(String file) throws FileNotFoundException, IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        List fileList = new ArrayList<>();

        while ((line = bufferedReader.readLine()) != null) {
            fileList.add(line);
        }
        //System.out.println("# of lines: " + fileList.size());
        return (ArrayList) fileList;
    }

    /**
     *
     * @param file
     * @param date1
     * @param date2
     * @return the data within date range that user input
     * @throws ParseException
     */
    public static List getDataInRange(ArrayList file, String date1, String date2) throws ParseException {
        //format the string date into date
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yy");
        Date startDate = format.parse(date1);
        Date endDate = format.parse(date2);

        //initate list
        List dataInRange = new ArrayList<>();
        //for each data compare it with the start and end date
        for (int i = 1; i < file.size(); i++) {
            String curData = (String) file.get(i);
            String[] data = curData.split(",");
            String tmpcurDate = data[5];
            Date curDate = format.parse(tmpcurDate);

            //compare the date with start date and end date
            if (curDate.compareTo(startDate) >= 0 && curDate.compareTo(endDate) <= 0) {
                dataInRange.add(curData);
            }
        }
        return dataInRange;
    }

    /**
     *
     * @param file
     * @return 2-d Array that include the distance of each corresponding index
     */
    public static double[][] turnToDistance(List file) {
        int length = file.size();
        double[][] distance = new double[length][length];

        //for each line, get the x and y
        for (int i = 0; i < length; i++) {
            String dataX = (String) file.get(i);
            double x1 = Double.valueOf(dataX.split(",")[0]);
            double y1 = Double.valueOf(dataX.split(",")[1]);

            //for each column, get the x and y
            for (int j = 0; j < length; j++) {
                String dataY = (String) file.get(j);
                double x2 = Double.valueOf(dataY.split(",")[0]);
                double y2 = Double.valueOf(dataY.split(",")[1]);

                //calculate distance based on x1,x2,y1,y2
                double dis = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));

                if (i == j) {
                    distance[i][j] = 0;
                } else {
                    distance[i][j] = dis;
                }
            }
        }

        return distance;
    }

}
