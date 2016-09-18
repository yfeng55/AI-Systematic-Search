package com.yf833;


import java.util.ArrayList;

public class Node {



    //array of arrays -- index is the processor, inner array is the set of tasks assigned to that processor
    public ArrayList<ArrayList<Number>> assignments;
    public ArrayList<Number> processor_speeds;

    public boolean visited;
    public int depth;
    public ArrayList<Node> adjacent_nodes;

    //create a root node (takes an array of processor speeds)
    public Node(ArrayList<Number> processor_speeds){
        this.processor_speeds = processor_speeds;

        //create an array of tasks for each processor
        ArrayList<ArrayList<Number>> newassignments = new ArrayList<>();
        for(int i=0; i<processor_speeds.size(); i++){
            newassignments.add(new ArrayList<Number>());
        }

        this.assignments = newassignments;
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = 0;
    }

    //create a child node
    public Node(Node oldnode, int processor, int task){
        ArrayList<ArrayList<Number>> newassignments = oldnode.assignments;
        newassignments.get(processor).add(task);

        this.processor_speeds = oldnode.processor_speeds;
        this.assignments = newassignments;
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = oldnode.depth + 1;
    }



    //TODO: implement totalTimeTaken()
    public int totalTimeTaken(){
        return 0;
    }

    //TODO: implement totalValue()
    public int totalValue(){
        return 0;
    }


    public String toString(){
        String output = "";
        for(int i=0; i<assignments.size(); i++){
            output += ("P" + i+1 + ": ");
            output += (assignments.get(i).toString());
        }
        return output;
    }


    // calculate the time it takes to complete a certain task
    private static float calcTimeTaken(int task_length, int processor_speed){
        return ((float) task_length) / processor_speed;
    }




}
