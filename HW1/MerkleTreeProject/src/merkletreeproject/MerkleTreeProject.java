/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merkletreeproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import edu.cmu.andrew.ww5.SinglyLinkedList;
import java.io.IOException;

/**
 *
 * @author ww5_weiwang
 */
public class MerkleTreeProject {

    /**
     * @param args the command line arguments
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.FileNotFoundException
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        // TODO code application logic here

        //input all files name
        String[] fileName = new String[4];
        fileName[0] = "smallFile.txt";
        fileName[1] = "CrimeLatLonXY.csv";
        fileName[2] = "CrimeLatLonXY1990_Size2.csv";
        fileName[3] = "CrimeLatLonXY1990_Size3.csv";

        //loop based on file name
        for (String fileName1 : fileName) {
            //read file
            SinglyLinkedList lineList = readFile(fileName1);
            //check if node length is even, if not, then copy the last node and add to the end
            lineList = checkEven(lineList);
            SinglyLinkedList hashBaseList = new SinglyLinkedList();
            String s1, s2, s;
            //convert base hashList into several nodes of hash value, for future calculation
            for (int k = 0; k < lineList.countNodes(); k += 2) {
                s1 = h((String) lineList.getObjectAt(k));
                s2 = h((String) lineList.getObjectAt(k + 1));
                s = h(concatenateH(s1, s2));
                hashBaseList.addAtEndNode(s);
            }
            //The last SinglyLinkedList contains two nodes, hash these two nodes.
            String root;
            //if the base list is already only 1 node with hash value, no need for following steps
            if (hashBaseList.countNodes() > 1) {
                //call generateRoot to recursively run hash
                lineList = generateRoot(hashBaseList);
                root = h((concatenateH((String) lineList.getObjectAt(0), ((String) lineList.getObjectAt(1)))));
            } else {
                root = hashBaseList.getObjectAt(0).toString();
            }
            System.out.println("The root is: " + root);
            //check if the generated root equals to the designated root.
            if ("A5A74A770E0C3922362202DAD62A97655F8652064CCCBE7D3EA2B588C7E07B58".equals(root)) {
                System.out.println("The file " + fileName1 + " is what we are looking for!");
                System.out.println();
            } else {
                System.out.println("The file " + fileName1 + " is not useful!");
                System.out.println();
            }
        }

    }

    /**
     *
     * @param file
     * @return a SinglyLinkedList with several nodes, each node represent each line.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static SinglyLinkedList readFile(String file) throws FileNotFoundException, IOException {
        //Open the file and store it into SinglyLinkedList
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        SinglyLinkedList lineList = new SinglyLinkedList();

        //if there are following lines, add to the end of list
        while ((line = bufferedReader.readLine()) != null) {
            lineList.addAtEndNode(line);
        }
        //get number of lines(nodes) stored in the list
        System.out.println("The total line of file is: " + lineList.countNodes());

        return lineList;
    }

    /**
     *
     * @param lineList
     * @return a list with final two hash value
     * @throws NoSuchAlgorithmException
     * run recursively to generate hash value
     */
    public static SinglyLinkedList generateRoot(SinglyLinkedList lineList) throws NoSuchAlgorithmException {
        String s1, s2, s;
        //stop when the list contains 2 final hash node
        while (lineList.countNodes() != 2) {
            SinglyLinkedList hashList = new SinglyLinkedList();
            //for every hashList, go through each node by group into 2, hash them and add the next node
            for (int i = 0; i < lineList.countNodes(); i += 2) {
                s1 = (String) lineList.getObjectAt(i);
                s2 = (String) lineList.getObjectAt(i + 1);
                //concatenante two generated hash node
                s = h(concatenateH(s1, s2));
                hashList.addAtEndNode(s);
            }
            //check the new hashlist node number, if not even, copy the last one and add to the end
            lineList = checkEven(hashList);
            lineList = generateRoot(lineList);
        }
        return lineList;
    }

    /**
     *
     * @param h1
     * @param h2
     * @return String concatenate h1 and h2
     */
    public static String concatenateH(String h1, String h2) {
        String result = h1 + h2;
        return result;
    }

    /**
     *
     * @param lineList
     * @return SinglyLinkedList with even number of nodes
     */
    public static SinglyLinkedList checkEven(SinglyLinkedList lineList) {
        int nodeNumber = lineList.countNodes();
        //if node number is not even, copy the last one and copy the the end
        if (nodeNumber % 2 == 1) {
            lineList.addAtEndNode(lineList.getLast());
        }
        return lineList;
    }

    /**
     *
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String h(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <= 31; i++) {
            byte b = hash[i];
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }

}
