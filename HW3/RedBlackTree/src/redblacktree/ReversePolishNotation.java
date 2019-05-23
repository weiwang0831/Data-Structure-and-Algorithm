/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redblacktree;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 */
public class ReversePolishNotation {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        //build tree store insert character information
        RedBlackTree tree = new RedBlackTree();

        //user input expression
        System.out.println("Please enter the operations below, Enter nothing and press enter to quit");
        Scanner input = new Scanner(System.in);
        String userInput = "a";

        //while input is not "", continue execute
        while (!"".equals(userInput)) {
            userInput = input.nextLine();
            //if user input is nothing, stop the program
            if ("".equals(userInput)) {
                break;
            }
            //store user input into String array
            String[] storage = userInput.split(" ");
            BigInteger result;
            //if only one character, show its value in the tree
            if (storage.length == 1) {
                if (Character.isLetter(storage[0].charAt(0))) {
                    result = tree.search(tree.getRoot(), storage[0]);
                } else {
                    //if user input is not a character, show it original value
                    result = new BigInteger(storage[0]);
                }
            } else {
                //if the user input is longer that 1 character, start calculation
                result = calculate(storage, tree);
                //if the result give back 0, which indicate error, stop the program
                if (result == BigInteger.ZERO) {
                    break;
                }
            }
            System.out.println(result);
        }
    }

    /**
     *
     * @param tokens
     * @param tree
     * @return BigInteger of operating result
     */
    public static BigInteger calculate(String[] tokens, RedBlackTree tree) {

        //initate result = 0
        BigInteger result = BigInteger.ZERO;
        String operators = "+-*/%=#~";
        Stack stack = new Stack(5);

        //iterate string in the user input
        for (String t : tokens) {
            //if the string is not operator, put in the stack
            if (!operators.contains(t)) {
                stack.push(t);
            } else {
                //if the string is an operator, start operation
                BigInteger a = null;
                BigInteger b = null;
                String tmpA = "";
                String tmpB = "";
                //token ~ only need to pop one value, so put in another situation
                if (!"~".equals(t)) {
                    //if the token is an operator, pop numbers from String array.
                    tmpA = (String) stack.pop();
                    tmpB = (String) stack.pop();
                    //digit+digit
                    //let a b be the value of themselves
                    if (Character.isDigit(tmpA.charAt(0)) && Character.isDigit(tmpB.charAt(0))) {
                        a = new BigInteger(tmpA);
                        b = new BigInteger(tmpB);
                    } else if (Character.isLetter(tmpB.charAt(0)) && Character.isDigit(tmpA.charAt(0))) {
                        //letter+digit
                        //get the value of that key in trees
                        b = tree.search(tree.getRoot(), tmpB);
                        a = new BigInteger(tmpA);
                    } else if (Character.isLetter(tmpA.charAt(0)) && Character.isDigit(tmpB.charAt(0))) {
                        //digit+letter
                        //get the value of that key in tree
                        a = tree.search(tree.getRoot(), tmpA);
                        b = new BigInteger(tmpB);
                    } else {
                        //letter+letter
                        //both at value from the tree
                        a = tree.search(tree.getRoot(), tmpA);
                        b = tree.search(tree.getRoot(), tmpB);
                    }
                } else {
                    //if the operator is "~", only pop one number from the String
                    tmpA = (String) stack.pop();
                    a = new BigInteger(tmpA);
                }
                digit(tree, stack, t, a, b, tmpA, tmpB);
            }
        }
        //only if the stack has values in, pop
        //if empty, dont pass it to result
        Object flag = stack.pop();
        if (flag != null) {
            result = new BigInteger((String) flag);
        }
        return result;
    }

    /**
     *
     * @param tree
     * @param stack
     * @param t
     * @param a
     * @param b
     * @param tmpA
     * @param tmpB execute all the operation put the generated result into the
     * stack
     */
    public static void digit(RedBlackTree tree, Stack stack, String t,
            BigInteger a, BigInteger b, String tmpA, String tmpB) {
        switch (t) {
            //case add
            case "+":
                try {
                    stack.push(String.valueOf(a.add(b)));
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case substract
            case "-":
                try {
                    stack.push(String.valueOf(b.subtract(a)));
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case multiple
            case "*":
                try {
                    stack.push(String.valueOf(b.multiply(a)));
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case divided
            case "/":
                try {
                    stack.push(String.valueOf(b.divide(a)));
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case modulus
            case "%":
                try {
                    stack.push(String.valueOf(b.mod(a)));
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case assigning values
            case "=":
                try {
                    //letter + valid number, insert into tree and push result into stack
                    if (Character.isLetter(tmpB.charAt(0)) && a != null) {
                        tree.insert(tmpB, a);
                        stack.push(String.valueOf(a));
                    } else if (Character.isDigit(tmpB.charAt(0))) {
                        //digit + digit/number, remind it is not a variable
                        System.out.println(tmpB + " is not a variable");
                    } else if (a == null) {
                        //if the second input cannot get value from tree, remind no variables
                        System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + "no variable " + tmpA + " and " + tmpB);
                    }
                } catch (Exception e) {
                    String error = exception(a, b, tmpA, tmpB);
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: " + error);
                }
                break;
            //case powMod: x^y%z
            case "#":
                try {
                    //pop one more variable from the stack
                    BigInteger c = new BigInteger((String) stack.pop());
                    stack.push(String.valueOf(c.modPow(b, a)));
                } catch (Exception e) {
                    System.out.println("Exception in thread \"main\" java.lang.Exception: error: stack underflow exception ");
                }
                break;
            //case unary minus
            case "~":
                stack.push(String.valueOf(a.negate()));
                break;
        }
    }

    /**
     *
     * @param a
     * @param b
     * @param tmpA
     * @param tmpB
     * @return String with the exception message
     */
    public static String exception(BigInteger a, BigInteger b, String tmpA, String tmpB) {
        String result = "";
        //if second operand is null
        if (a == null) {
            //if first operand is null as well
            if (b == null) {
                result = "no variable " + tmpA + " and " + tmpB;
            } else {
                //if first operand is not null
                result = "no varaible " + tmpA;
            }
        } else {
            //first null, second not null
            if (b == null) {
                result = "no variable " + tmpB;
            }
        }
        //return the message
        return result;
    }
}
