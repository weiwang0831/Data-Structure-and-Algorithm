/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lzwcompression;

import java.io.*;

/**
 *
 * @author weiwang_ww5
 */
public class DeCompress {

    private DataInputStream in;
    private DataOutputStream out;
    HashMap<Integer, String> hashTable = new HashMap<>();

    public DeCompress(DataInputStream in, DataOutputStream out) {
        this.in = in;
        this.out = out;
    }

    public void decompress() throws IOException {
        int count = 0;
        int priorCodeWord = 0;
        int codeWord = 0;
        boolean Rfirst = false;

        //enter all symbols into the table;
        for (int i = 0; i < 256; i++) {
            hashTable.put(i, "" + (char) i);
            count++;
        }
        //read(priorcodeword)
        //read 2 byte in(16bits)-->12bits
        try {
            byte first = in.readByte();
            byte second = in.readByte();

            priorCodeWord = (first << 4 | ((second >> 4) & 15));
            //and output its corresponding character
            out.writeBytes(hashTable.get(priorCodeWord));

            //codewords are still left to be input
            while (true) {
                //read(codeword)
                if (Rfirst) {
                    //read(codeword);
                    first = in.readByte();
                    second = in.readByte();
                    //Source and reference of bit manipulation using string: Stack Overflow 
                    String tmp1st = Integer.toBinaryString(first);
                    if (tmp1st.length() < 8) {
                        tmp1st = String.format("%0" + (8 - tmp1st.length()) + "d%s", 0, tmp1st);
                    }
                    String tmp2nd = Integer.toBinaryString(second);
                    if (tmp2nd.length() < 8) {
                        tmp2nd = String.format("%0" + (8 - tmp2nd.length()) + "d%s", 0, tmp2nd);
                    }
                    codeWord = Integer.parseInt(tmp1st.substring(tmp1st.length() - 8, tmp1st.length()) + tmp2nd.substring(tmp2nd.length() - 8, tmp2nd.length()).substring(0, 4), 2); //parsing the int equivalent of binary
                } else {
                    byte third = in.readByte();
                    //Source and reference of bit manipulation using string: Stack Overflow 
                    String tmp2nd = Integer.toBinaryString(second);
                    if (tmp2nd.length() < 8) {
                        tmp2nd = String.format("%0" + (8 - tmp2nd.length()) + "d%s", 0, tmp2nd);
                    }
                    String tmp3rd = Integer.toBinaryString(third);
                    if (tmp3rd.length() < 8) {
                        tmp3rd = String.format("%0" + (8 - tmp3rd.length()) + "d%s", 0, tmp3rd);
                    }
                    codeWord = Integer.parseInt(tmp2nd.substring(tmp2nd.length() - 8, tmp2nd.length()).substring(4, 8) + tmp3rd.substring(tmp3rd.length() - 8, tmp3rd.length()), 2);//parsing the int equivalent of binary

                }
                Rfirst = !Rfirst;
                //if codeword is not in table
                if (codeWord >= count) {
                    if (hashTable.contains(priorCodeWord)) {
                        if (count < 4096) {
                            //enter string(priorcodeword) + firstChar(string(priorcodeword)) into the table;
                            hashTable.put(count, hashTable.get(priorCodeWord) + hashTable.get(priorCodeWord).charAt(0));
                            count++;
                        }
                        //output string(pc)+firstChar[String(pc]
                        out.writeBytes(hashTable.get(priorCodeWord) + hashTable.get(priorCodeWord).charAt(0));
                    } else {
                        out.write(priorCodeWord);
                    }
                }//if the codeWord is in the table
                else {
                    //enter string(pc) + 1st(str(cw)) into the table
                    if (hashTable.contains(priorCodeWord) && hashTable.contains(codeWord)) {
                        if (count < 4096) {
                            hashTable.put(count, hashTable.get(priorCodeWord) + hashTable.get(codeWord).charAt(0));
                            count++;
                        }
                        //output codeword;
                        out.writeBytes(hashTable.get(codeWord));
                    } else {
                        out.write(codeWord);
                    }
                }
                priorCodeWord = codeWord;
            }
        } catch (EOFException exception) {
            in.close();
            out.close();

        } catch (IOException exception) {
            in.close();
            out.close();
        }

    }

}
/*
LZW_Decompress()
        {
            enter all symbols into the table;
            read(priorcodeword) and output its corresponding character;
            while (codewords are still left to be input
            
                ){       read(codeword);
                if (codeword 
                    not 
                in the table
                
                    )  {          enter string(priorcodeword
                    ) + firstChar(string(priorcodeword)) into the table;
                    output string(priorcodeword
                    ) + firstChar(string(priorcodeword));
                }else {
                            enter string(priorcodeword
                            ) + firstChar(string(codeword)) into the table;
                            output string(codeword);       }
                priorcodeword = codeword;
            }
        }
 */
