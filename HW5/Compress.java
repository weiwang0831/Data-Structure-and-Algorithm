/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzwcompression;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author weiwang_ww5
 */
public class Compress {

    DataInputStream in;
    DataOutputStream out;
    HashMap<String, Integer> hashTable = new HashMap<>(); //hashTable store the symbol

    public Compress(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void compress() throws IOException {
        int count = 0;
        boolean first = true;

        //enter all symbols in the table;    
        for (int i = 0; i < 256; i++) {
            hashTable.put("" + (char) i, i);
            count++;
        }

        String s = "";
        byte[] buffer = new byte[3];

        try {
            //while(any input left)
            while (true) {

                //read character c
                char c = (char) in.readByte();
                //if s+c is in the hashTable, s=s+c
                if (hashTable.contains(s + c)) {
                    s = s + c;
                } else {
                    int bits = 0;
                    //whole process of output codeWord(s)
                    if (hashTable.contains(s)) {
                        bits = hashTable.get(s);
                    } else {
                        bits = (int) c;
                    }
                    //process convert data into correct 12 bit format
                    if (first) {
                        //Right shift 4 bits for first bit, become 12 bits
                        //add 4 zero on the left
                        buffer[0] = (byte) (bits >> 4);
                        //Left shift 4 bits for second bit, become 12 bits
                        //add 4 zero on the right
                        buffer[1] = (byte) (bits << 4);
                    } else {
                        //if not the first time to read
                        //Right shift become 12, 4 zero on the left:(byte) (bits >> 4);
                        //Left shift become 12, 4 zero on the right:(byte) (bits << 4);
                        //add 4 bits and deal with the -ve sign by & it with 15(11110000)
                        buffer[1] = (byte) (buffer[1] + (byte) (((byte) (bits >> 4) >> 4) & 15));
                        //taking the first 4 bit and adding it to 8 bit of the true buffer
                        buffer[2] = (byte) (((byte) (bits >> 4) << 4) + (byte) (((byte) (bits << 4) >> 4) & 15));
                        //output codeword(s);
                        out.write(buffer);
                    }
                    first = !first;
                    //Enter s+c into the table
                    //if count smaller than 2^12=4096, input s+c as increment value of count
                    if (count < 4096) {
                        hashTable.put(s + c, count++);
                    }
                    s = "" + c;
                }
            }
        } catch (IOException E) {
            int bits = 0;
            if (hashTable.contains(s)) {
                bits = hashTable.get(s);
            }
            if (first) {
                //Right shift 4 bits for first bit, become 12 bits
                //add 4 zero on the left
                buffer[0] = (byte) (bits >> 4);
                //Left shift 4 bits for second bit, become 12 bits
                //add 4 zero on the right
                buffer[1] = (byte) (bits << 4);
            } else {
                //if not the first time to read
                //Right shift become 12, 4 zero on the left: (byte) (bits >> 4);
                //Left shift become 12, 4 zero on the right: (byte) (bits << 4);
                //add 4 bits and deal with the -ve sign by & it with 15(11110000)
                buffer[1] = (byte) (buffer[1] + (byte) (((byte) (bits >> 4) >> 4) & 15));
                //taking the first 4 bit and adding it to 8 bit of the true buffer
                buffer[2] = (byte) (((byte) (bits >> 4) << 4) + (byte) (((byte) (bits << 4) >> 4) & 15));
            }
            //output codeword(s);
            out.write(buffer);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ex) {
                Logger.getLogger(Compress.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    /*
    LZW_Compress(){    
    enter all symbols in the table;    
    read(first character from w into string s);    
    while(any input left){
        read(character c);       
        if(s + c is in the table)        
            s = s + c;       
        else {                  
            output codeword(s);                  
            Enter s + c into the table;                  
            s = c;       
             } // end if/else    
    } // end while    
    output codeword(s); } 
     */
}
