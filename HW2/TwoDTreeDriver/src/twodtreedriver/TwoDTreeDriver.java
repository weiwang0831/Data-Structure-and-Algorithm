/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twodtreedriver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 */
public class TwoDTreeDriver {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        //remind user input their options
        Scanner in = new Scanner(System.in);
        int userInput = 0;
        //while the userinput is not 8, keep providing options
        while (userInput != 8) {
            System.out.println();
            System.out.println("Crime file loaded into 2d tree with 27218 records. ");
            System.out.println("What would you like to do?");
            System.out.println("1: inorder \n2: preorder \n3: levelorder \n"
                    + "4: postorder \n5: reverseLevelOrder \n6: search for points within rectangle \n"
                    + "7: search for nearest neighbor \n8: quit ");
            System.out.println("Please enter the number below: ");
            userInput = in.nextInt();

            //Construct tree based on CSV file, file locate in the project
            TwoDTree tree = new TwoDTree("CrimeLatLonXY.csv");

            //Based on option, provide corresponding print and present functions
            switch (userInput) {
                case 1:
                    tree.inOrderPrint();
                    break;
                case 2:
                    tree.preOrderPrint();
                    break;
                case 3:
                    tree.levelOrderPrint();
                    break;
                case 4:
                    tree.postOrderPrint();
                    break;
                case 5:
                    tree.reverseLevelOrderPrint();
                    break;
                case 6:
                    //remind user input four double represent range
                    System.out.println("Enter a rectangle bottom left (X1,Y1) and "
                            + "top right (X2, Y2) as four doubles each separated by a space");
                    Scanner input = new Scanner(System.in);
                    String region = input.nextLine();

                    //seperate userinput by space
                    String[] splited = region.split(" ");
                    double x1 = Double.valueOf(splited[0]);
                    double y1 = Double.valueOf(splited[1]);
                    double x2 = Double.valueOf(splited[2]);
                    double y2 = Double.valueOf(splited[3]);

                    //generate crime list based on findPointsInRange function
                    ListOfCrimes list1 = tree.findPointsInRange(x1, y1, x2, y2);
                    ListOfCrimes list2 = tree.findPointsInRange(x1, y1, x2, y2);

                    //show number of nodes counted
                    System.out.println("Examed " + list1.getCount() + " Nodes during search:");
                    System.out.println("Found " + list1.countNodes() + " crimes");
                    System.out.println(list1.toString());

                    //export file into KML format, which can show on Google Earth
                    FileWriter fw = new FileWriter("PGHCrimes.KML");
                    BufferedWriter writeFileBuffer = new BufferedWriter(fw);
                    writeFileBuffer.write(list2.toKML());
                    writeFileBuffer.close();
                    break;
                case 7:
                    //remind user input 2 double represent the target point
                    System.out.println("Enter a rectangle bottom left (X1,Y1) and "
                            + "top right (X2, Y2) as four doubles each separated by a space");
                    Scanner input2 = new Scanner(System.in);
                    String region2 = input2.nextLine();

                    //sperate string based on space
                    String[] splited2 = region2.split(" ");
                    double x3 = Double.valueOf(splited2[0]);
                    double y3 = Double.valueOf(splited2[1]);

                    //generate neighbor object contains distance and nearest node
                    Neighbor neighbor = tree.nearestNeighbor(x3, y3);
                    System.out.println("Looked at " + neighbor.getCount() + " nodes in tree. Found the nearest crime at: ");
                    System.out.println(neighbor.getPointer().value);
                    System.out.println("The distance is: " + neighbor.getDistance() + " feet");
                    break;
                case 8:
                    //leave the application
                    System.out.println("Thanks for using this application!");
                    break;

            }
        }
    }
}
