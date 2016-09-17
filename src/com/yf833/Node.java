package com.yf833;


import java.util.ArrayList;

public class Node {

    //array of arrays -- index is the processor, inner array is the set of tasks assigned to that processor
    public ArrayList<ArrayList<Number>> assignments;

    public Node(){

    }

    //TODO: implement totalTimeTaken()
    public int totalTimeTaken(){
        return 0;
    }

    //TODO: implement totalValue()
    public int totalValue(){
        return 0;
    }



    // calculate the time it takes to complete a certain task
    private static float calcTimeTaken(int task_length, int processor_speed){
        return ((float) task_length) / processor_speed;
    }


}
