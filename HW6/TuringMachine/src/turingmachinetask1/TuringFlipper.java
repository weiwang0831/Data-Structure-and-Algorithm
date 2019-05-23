/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingmachinetask1;

import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 *
 */
public class TuringFlipper {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Turing machine1 = new Turing(2);    // A two nextState machine 

        State s0 = new State(0); // Only s0 has transitions  
        s0.addTransition(new Transition('0', '1', Transition.RIGHT, 0));
        s0.addTransition(new Transition('1', '0', Transition.RIGHT, 0));
        s0.addTransition(new Transition('B', 'B', Transition.RIGHT, 1));

        System.out.println("This is the demo:");
        machine1.addState(s0);// Add the nextState to the machine                  
        String inTape = "0101010101010";
        // Define some input 
        System.out.println(inTape);
        String outTape = machine1.execute(inTape);  // Execute the machine 
        System.out.println(outTape);  // Show the machine’s output 
        
        
        System.out.println("Please enter the input you want:");
        Scanner input=new Scanner(System.in);
        String inputS=input.nextLine();
        System.out.println(machine1.execute(inputS));
        
    }

}
