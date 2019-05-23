/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingsubtractor;

import java.util.Scanner;

/**
 *
 * @author weiwang_ww5
 */
public class TuringSubtractor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Turing machine1=new Turing(7);
        
        State s0=new State(0);
        s0.addTransition(new Transition('0','B',Transition.RIGHT,1));
        s0.addTransition(new Transition('1','B',Transition.RIGHT,5));
        
        State s1=new State(1);
        s1.addTransition(new Transition('0','0',Transition.RIGHT,1));
        s1.addTransition(new Transition('1','1',Transition.RIGHT,2));
        
        State s2=new State(2);
        s2.addTransition(new Transition('1','1',Transition.RIGHT,2));
        s2.addTransition(new Transition('0','1',Transition.LEFT,3));
        s2.addTransition(new Transition('B','B',Transition.LEFT,4));
        
        State s3=new State(3);
        s3.addTransition(new Transition('0','0',Transition.LEFT,3));
        s3.addTransition(new Transition('1','1',Transition.LEFT,3));
        s3.addTransition(new Transition('B','B',Transition.RIGHT,0));
        
        State s4=new State(4);
        s4.addTransition(new Transition('1','B',Transition.LEFT,4));
        s4.addTransition(new Transition('0','0',Transition.LEFT,4));
        s4.addTransition(new Transition('B','0',Transition.RIGHT,6));
        
        State s5=new State(5);
        s5.addTransition(new Transition('0','B',Transition.RIGHT,5));
        s5.addTransition(new Transition('1','B',Transition.RIGHT,5));
        s5.addTransition(new Transition('B','B',Transition.RIGHT,6));
        
        machine1.addState(s0);
        machine1.addState(s1);
        machine1.addState(s2);
        machine1.addState(s3);
        machine1.addState(s4);
        machine1.addState(s5);
        
        System.out.println("This is a demo:");
        String demo="0001010";
        System.out.println("Input is: "+demo);
        System.out.println(machine1.execute(demo));
        
        
        System.out.println("Please enter the input you want:");
        Scanner input=new Scanner(System.in);
        String inputS=input.nextLine();
        //String inputS="0001010";
        System.out.println(machine1.execute(inputS));
    }
    
}
