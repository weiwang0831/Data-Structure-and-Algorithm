/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingdecider;

import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 */
public class TuringDecider {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Turing machine1 = new Turing(9);

        State s0 = new State(0);
        s0.addTransition(new Transition('0', 'X', Transition.RIGHT, 1));
        //goes to reject status
        s0.addTransition(new Transition('1', 'B', Transition.RIGHT, 5));

        State s1 = new State(1);
        s1.addTransition(new Transition('1', 'Y', Transition.LEFT, 2));
        s1.addTransition(new Transition('0', '0', Transition.RIGHT, 1));
        s1.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 1));
        s1.addTransition(new Transition('X', 'X', Transition.RIGHT, 1));
        //goes to reject status
        s1.addTransition(new Transition('B', 'B', Transition.RIGHT, 5));

        State s2 = new State(2);
        s2.addTransition(new Transition('X', 'X', Transition.RIGHT, 3));
        s2.addTransition(new Transition('0', '0', Transition.LEFT, 2));
        s2.addTransition(new Transition('Y', 'Y', Transition.LEFT, 2));

        State s3 = new State(3);
        s3.addTransition(new Transition('0', 'X', Transition.RIGHT, 1));
        s3.addTransition(new Transition('X', 'X', Transition.RIGHT, 3));
        s3.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 4));

        State s4 = new State(4);
        s4.addTransition(new Transition('Y', 'Y', Transition.RIGHT, 4));
        s4.addTransition(new Transition('B', 'B', Transition.RIGHT, 6));//accept
        s4.addTransition(new Transition('0', 'B', Transition.RIGHT, 5));//reject
        s4.addTransition(new Transition('1', 'B', Transition.RIGHT, 5));//reject

        State s5 = new State(5);
        s5.addTransition(new Transition('B', 'B', Transition.LEFT, 7));
        s5.addTransition(new Transition('X', 'B', Transition.RIGHT, 5));
        s5.addTransition(new Transition('Y', 'B', Transition.RIGHT, 5));
        s5.addTransition(new Transition('0', 'B', Transition.RIGHT, 5));
        s5.addTransition(new Transition('1', 'B', Transition.RIGHT, 5));

        //processing result, both halt at state 8
        //if the language is accept, mark R as 1
        State s6 = new State(6);
        s6.addTransition(new Transition('B', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('X', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('Y', 'B', Transition.LEFT, 6));
        s6.addTransition(new Transition('R', '1', Transition.LEFT, 8));

        //if the language is rejected, mark R as 0
        State s7 = new State(7);
        s7.addTransition(new Transition('B', 'B', Transition.LEFT, 7));
        s7.addTransition(new Transition('X', 'B', Transition.LEFT, 7));
        s7.addTransition(new Transition('Y', 'B', Transition.LEFT, 7));
        s7.addTransition(new Transition('0', 'B', Transition.LEFT, 7));
        s7.addTransition(new Transition('1', 'B', Transition.LEFT, 7));
        s7.addTransition(new Transition('R', '0', Transition.LEFT, 8));//state 8 is the terminal state

        machine1.addState(s0);
        machine1.addState(s1);
        machine1.addState(s2);
        machine1.addState(s3);
        machine1.addState(s4);
        machine1.addState(s5);
        machine1.addState(s6);
        machine1.addState(s7);

        System.out.println("This is a demo:");
        String demo = "0101010";
        System.out.println("Input is: " + demo);
        System.out.println(machine1.execute(demo));
        
        //you can input here
        System.out.println("You can input below:");
        Scanner input=new Scanner(System.in);
        String inputS = input.nextLine();
        System.out.println("Input is: " + inputS);
        System.out.println(machine1.execute(inputS));
    }

}
