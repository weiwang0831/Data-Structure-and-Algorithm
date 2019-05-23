/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package merklehellman.knapsack.crypto.project;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import edu.cmu.andrew.ww5.*;
import java.math.BigInteger;

/**
 *
 * @author ww5_weiwang contains method for encryption and decryption
 */
public class MerkleHellmanKnapsackCryptoProject {

    /**
     * @param args the command line arguments
     * @throws java.io.UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnsupportedEncodingException {
        // TODO code application logic here

        //create new object to avoid methods not being used in static context
        MerkleHellmanKnapsackCryptoProject newObject = new MerkleHellmanKnapsackCryptoProject();

        //User input String
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a String and I will encrypt it as a large Integer");
        String userInput = input.nextLine();
        //check the if the input is larger than 80
        if (userInput.length() > 80) {
            System.out.println("Please re-intput length less than 80");
            userInput = input.nextLine();
        }
        //Show the input result again
        System.out.println("Clear text: ");
        System.out.println(userInput);
        //Show the number of bits
        System.out.println("number of clear text bits are: " + userInput.length());

        //Turn String into ACII-code
        byte[] bytes = userInput.getBytes();

        //Generate Original w list, will be splited into several w list for encryption (not for decryption)
        SinglyLinkedList wOriginal = newObject.generateList();
        //Private key
        BigInteger wSum = newObject.getSumW(wOriginal);
        BigInteger q = newObject.getQ(wSum);
        BigInteger r = newObject.getR(q, wSum);
        //Public key
        SinglyLinkedList b = newObject.getPublicKey(wOriginal, r, q);

        //Initialize print out parameters
        String encryptCode = "";
        String decryptText = "";

        //Loop based on user input String, one by one
        for (int i = 0; i < bytes.length; i++) {
            byte newByte = bytes[i];
            //format byte into appropriate String
            String binary = String.format("%8s", Integer.toBinaryString(newByte)).replace(" ", "0");
            //create subset of w list, used for this character
            SinglyLinkedList w = new SinglyLinkedList();
            for (int k = 0; k < 8; k++) {
                w.addAtEndNode(wOriginal.getObjectAt(i * 8 + k));
            }

            //encrypt
            BigInteger encrypt = newObject.encrypt(binary, b);
            encryptCode += encrypt;
            //Decrypt
            BigInteger rMod = r.modInverse(q);
            //get the binary string, which can be covert into char.
            //note here the w is the original one not the subset
            String decryptResult = newObject.decrypt(wOriginal, encrypt, rMod, q);
            decryptText += decryptResult;
        }
        System.out.println(userInput + " is encrypted as " + encryptCode);
        System.out.println("Result of Decryption: " + decryptText);

    }

    /**
     *
     * @param bytes
     * @return String contains binary of the original bytes
     */
    public String binaryFrom(byte bytes) {
        String binary = "";
        if (bytes <= 0) {
            return "";
        } else {
            while (bytes > 0) {
                binary += bytes % 2;
                bytes /= 2;
            }
            return binary;
        }
    }

    /**
     *
     * @return super-increasing list each element is multiple by 2 to the
     * previous one guarantee each element is larger than the sum of all
     * previous values
     */
    public SinglyLinkedList generateList() {
        SinglyLinkedList list = new SinglyLinkedList();
        //add the first element
        BigInteger number = BigInteger.valueOf(1);
        //the mutiplier
        BigInteger n = BigInteger.valueOf(2);

        for (int i = 0; i < 640; i++) {
            list.addAtEndNode(number);
            number = number.multiply(n);
        }
        return list;
    }

    /**
     *
     * @param w is a SinglyLinkedList, super-increasing
     * @return BigInteger with the sum of all values in w
     */
    public BigInteger getSumW(SinglyLinkedList w) {
        w.reset();
        BigInteger sum = BigInteger.valueOf(0);
        for (int i = 0; i < w.countNodes(); i++) {
            sum = sum.add((BigInteger) w.getObjectAt(i));
        }
        return sum;
    }

    /**
     *
     * @param wSum the sum of w list
     * @return BigInteger q, a prime larger than wSum
     */
    public BigInteger getQ(BigInteger wSum) {
        BigInteger q = wSum.nextProbablePrime();
        return q;
    }

    /**
     *
     * @param q a prime larger than wSum
     * @param wSum the sum of list w
     * @return BigInteger r that is coprime with q, range between 1 and q
     */
    public BigInteger getR(BigInteger q, BigInteger wSum) {
        //divided by 2 to ensure it is a lot smaller than q, get the next prime of it.
        BigInteger r = wSum.divide(BigInteger.valueOf(2)).nextProbablePrime();
        //check if it is coprime and smaller than q, if not, regenerate
        while ((Integer.parseInt(r.gcd(q).toString()) != 1) && r.compareTo(q) > 0) {
            r = r.nextProbablePrime();
        }
        return r;
    }

    /**
     *
     * @param w super-increasing list
     * @param r BigInteger coprime to q and between 1 and q
     * @param q BigInteger, prime larger than wSum
     * @return a list contains public in sequence after calculation
     */
    public SinglyLinkedList getPublicKey(SinglyLinkedList w, BigInteger r, BigInteger q) {
        //reset w to ensure the current pointer is point to head
        w.reset();
        SinglyLinkedList b = new SinglyLinkedList();

        //loop w and do calculation
        for (int i = 0; i < w.countNodes(); i++) {
            Object add = new BigInteger(w.getObjectAt(i).toString()).multiply(r).mod(q);
            b.addAtEndNode(add);
        }
        return b;
    }

    /**
     *
     * @param binaryResult
     * @return char based on ASCII
     */
    public String binaryToChar(String binaryResult) {
        String result = "";
        char nextChar;
        nextChar = (char) Integer.parseInt(binaryResult.substring(0, 8), 2);
        result += nextChar;

        return result;
    }

    /**
     *
     * @param binary
     * @param b
     * @return encrypt code that can be sent to receive.
     */
    public BigInteger encrypt(String binary, SinglyLinkedList b) {
        BigInteger encrypt = BigInteger.valueOf(0);
        for (int j = 0; j < binary.length(); j++) {
            //Calculation: b multiple every unit of binary generated previously
            BigInteger b1 = (BigInteger) b.getObjectAt(j);
            BigInteger subresult = b1.multiply(BigInteger.valueOf(Long.valueOf(String.valueOf(binary.charAt(j)))));
            //add every result so the encrypt code can generated
            encrypt = encrypt.add(subresult);
        }
        return encrypt;
    }

    /**
     *
     * @param w
     * @param encrypt
     * @param rMod
     * @param q
     * @return based on the encrypt code provided, turn it back into binary code, and convert it to original text
     */
    public String decrypt(SinglyLinkedList w, BigInteger encrypt, BigInteger rMod, BigInteger q) {
        BigInteger plainTextBI = encrypt.multiply(rMod).mod(q);
        //From the end of w to the start
        w.reset();
        SinglyLinkedList decryptBinary = new SinglyLinkedList();
        //subtract element in w list and get the largest number smaller than plainTextBI
        //each time the subtract result will be given to plainTextBI
        for (int j = w.countNodes(); j > 0; j--) {
            BigInteger tmp = plainTextBI.subtract((BigInteger) w.getObjectAt(j - 1));
            //if tmp smaller than 0, add to decrypt binary as 0
            if (tmp.compareTo(BigInteger.valueOf(0)) == -1) {
                decryptBinary.addAtFrontNode(0);
            } else {
                decryptBinary.addAtFrontNode(1);
                plainTextBI = tmp;
            }
        }
        //get every element of binary result and append them
        String binaryResult = "";
        for (int j = 0; j < decryptBinary.countNodes(); j++) {
            binaryResult += decryptBinary.getObjectAt(j);
        }
        //convert binary back to user input element
        String decryptResult = binaryToChar(binaryResult);

        return decryptResult;
    }
}
