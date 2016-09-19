package com.yf833;


import java.util.ArrayList;

public class Node {



    //array of arrays -- index is the processor, inner array is the set of tasks assigned to that processor
    public ArrayList<ArrayList<Integer>> assignments;
    public ArrayList<Integer> processor_speeds;

    public boolean visited;
    public int depth;
    public ArrayList<Node> adjacent_nodes;



    //create a root node (takes an array of processor speeds)
    public Node(ArrayList<Integer> processor_speeds){
        this.processor_speeds = processor_speeds;

        //create an array of tasks for each processor
        ArrayList<ArrayList<Integer>> newassignments = new ArrayList<>();
        for(int i=0; i<processor_speeds.size(); i++){
            newassignments.add(new ArrayList<Integer>());
        }

        this.assignments = newassignments;
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = 0;
    }


    //create a child node
    public Node(Node oldnode, int processor, int task){
        ArrayList<ArrayList<Integer>> newassignments = oldnode.assignments;
        newassignments.get(processor).add(task);

        this.processor_speeds = oldnode.processor_speeds;
        this.assignments = newassignments;
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = oldnode.depth + 1;
    }



    // return the total value of the assigned tasks in this node //
    public int totalValue(){

        int totalvalue = 0;

        for(int i=0; i<this.assignments.size(); i++){
            for(int j=0; j<this.assignments.get(i).size(); j++){
                totalvalue += this.assignments.get(i).get(j);
            }
        }

        return totalvalue;
    }

    // return the maximum amount of time taken by a processor in this node //
    public float maxTimeTaken(){
        
        float maxtimetaken = 0;

        for(int i=0; i<this.assignments.size(); i++){
            float processortime = 0;
            for(int j=0; j<this.assignments.get(i).size(); j++){
                processortime += calcTimeTaken(this.assignments.get(i).get(j), this.processor_speeds.get(i));
            }

            if(processortime > maxtimetaken){
                maxtimetaken = processortime;
            }
        }


        return maxtimetaken;
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
