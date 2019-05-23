/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package turingmachinetask1;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

/**
 *
 * @author weiwang_ww5
 */
public class Turing {

    public int state_num;
    ArrayList<State> stateArr = new ArrayList<>(state_num);

    public Turing(int state_num) {
        this.state_num = state_num;
    }

    public String execute(String inTape) {
        Character[] resultArray = new Character[100];
        for(int i=0;i<inTape.length();i++){
            resultArray[i] = inTape.charAt(i);
        }
        for (int i = inTape.length(); i < resultArray.length; i++) {
            resultArray[i] = 'B';
        }

        char init_input = inTape.charAt(0);
        State init_state = stateArr.get(0);
        Transition t = init_state.transitMap.get(init_input);
        char init_output = t.output;
        State nextState = stateArr.get(t.nextState);
        resultArray[0] = init_output;
        int actionIndex = 0;
        actionIndex += init_state.transitMap.get(init_input).direction;

        for (int i = actionIndex; i < inTape.length(); i = i + t.direction) {
            char input = resultArray[i];
            t = nextState.transitMap.get(input);
            if (t.nextState == (state_num - 1)) {
                break;
            }
            char output = t.output;
            resultArray[i] = output;
            nextState = stateArr.get(t.nextState);
        }
        return getString(resultArray);
    }

    public String getString(Character[] a) {
        String result = "";
        for (int i = 0; i < a.length; i++) {
            result += a[i];
        }
        return result;
    }

    public void addState(State s) {
        stateArr.add(s);
    }
}
