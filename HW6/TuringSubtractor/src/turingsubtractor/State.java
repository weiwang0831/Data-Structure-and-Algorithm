/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingsubtractor;

import java.util.HashMap;

/**
 *
 * @author weiwang_ww5
 */
public class State {
    
    public int init_state;
    
    HashMap<Character,Transition> transitMap=new HashMap<>();

    public State(int state) {
        this.init_state = state;
    }

    public void addTransition(Transition transition) {
        transitMap.put(transition.input, transition);
    }

}
