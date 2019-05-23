/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingmachinetask1;

/**
 *
 * @author weiwang_ww5
 */
public class Transition {

    final public static int RIGHT = 1;
    final public static int LEFT = -1;

    public char input;
    public char output;
    public int direction;
    public int nextState;

    public Transition(char input, char output, int direction, int state) {
        this.input = input;
        this.output = output;
        this.direction = direction;
        this.nextState = state;
    }
    

}
