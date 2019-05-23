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
public class LZWCompression {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        /**
         * *******************************************
         * Important! My application is run by setting: right click on the
         * LZWCompression project-->properties-->Run-->set the Argument as: 
         * c v words.html compress.txt || d v compress.txt original.html || 
         * c words.html compress.txt || d compress.txt original.html ||
         * ******************************************
         */
        String command1 = args[0];
        String command2 = "";
        String inFile = "";
        String outFile = "";

        //if the user input as -c shortwords.txt zippedFile.txt
        switch (args.length) {
            //specify input and output file, they are at different index when args are different
            case 3:
                inFile = args[1];
                outFile = args[2];
                break;
            case 4:
                command2 = args[1];
                inFile = args[2];
                outFile = args[3];
                break;
            default:
                System.out.println("Please enter valid command!");
                break;
        }

        switch (command1) {
            //when user input -c, start the compress
            case "c":
                DataInputStream in = null;
                DataOutputStream out = null;
                try {
                    in = new DataInputStream(
                            new BufferedInputStream(new FileInputStream(inFile)));
                    out = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(outFile)));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(Compress.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Build Compress object using inputStream and outputStream Parameters
                Compress c = new Compress(in, out);
                System.out.println("Start compression");
                c.compress(); //compress process
                System.out.println("Compression Finished!");
                break;
            //when user input -d, start the decompress
            case "d":
                DataInputStream in1 = null;
                DataOutputStream out1 = null;
                try {
                    in1 = new DataInputStream(
                            new BufferedInputStream(new FileInputStream(inFile)));
                    out1 = new DataOutputStream(
                            new BufferedOutputStream(new FileOutputStream(outFile)));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LZWCompression.class.getName()).log(Level.SEVERE, null, ex);
                }
                DeCompress dc = new DeCompress(in1, out1);
                System.out.println("Start decompression");
                dc.decompress(); //decompress process
                System.out.println("Decompression Finished!");
                break;
            default:
                System.out.println("Please enter c or d indicates compress or decompress");
                break;
        }

        //if the second args equals to v, calculate the byte of the input and output file
        if (command2.equals("v")) {
            try {
                File file = new File(inFile);
                File file1 = new File(outFile);
                System.out.println("bytes read = " + file.length() + " bytes written = " + file1.length());
            } catch (Exception e) {
            }
        }
    }

}
