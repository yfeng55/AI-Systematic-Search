package com.yf833;


import java.util.ArrayList;

public class Node {



    //array of arrays -- index is the processor, inner array is the set of tasks assigned to that processor
    public ArrayList<ArrayList<Integer>> assignments;

    public ArrayList<Float> processor_speeds;
    public ArrayList<Float> task_lengths;

    public boolean visited;
    public int depth;
    public ArrayList<Node> adjacent_nodes;



    //create a root node (takes an array of processor speeds)
    public Node(ArrayList<Float> processor_speeds, ArrayList<Float> task_lengths){
        this.processor_speeds = processor_speeds;
        this.task_lengths = task_lengths;

        //create an array of tasks for each processor
        ArrayList<ArrayList<Integer>> newassignments = new ArrayList<>();
        for(int i=0; i<processor_speeds.size(); i++){
            newassignments.add(new ArrayList<Integer>());
        }

        this.assignments = newassignments;
        this.visited = false;
        this.depth = 0;
        this.adjacent_nodes = new ArrayList<>();

    }


    //create a child node
    public Node(Node oldnode, int processor, int tasknum){
        ArrayList<ArrayList<Integer>> newassignments = copyAssignments(oldnode.assignments);
        newassignments.get(processor).add(tasknum);

        this.processor_speeds = new ArrayList<>(oldnode.processor_speeds);
        this.task_lengths = new ArrayList<>(oldnode.task_lengths);

        this.assignments = new ArrayList<>(newassignments);
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = oldnode.depth + 1;
    }

    //copy constructor
    public Node(Node oldnode){
        ArrayList<ArrayList<Integer>> newassignments = copyAssignments(oldnode.assignments);

        this.processor_speeds = new ArrayList<>(oldnode.processor_speeds);
        this.task_lengths = new ArrayList<>(oldnode.task_lengths);

        this.assignments = new ArrayList<>(newassignments);
        this.visited = false;
        this.adjacent_nodes = new ArrayList<>();
        this.depth = oldnode.depth;
    }



    // return the total value of the assigned tasks in this node //
    public float totalValue(){

        float totalvalue = 0;

        for(int i=0; i<this.assignments.size(); i++){
            for(int j=0; j<this.assignments.get(i).size(); j++){
                totalvalue += this.task_lengths.get(this.assignments.get(i).get(j)-1);
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
                processortime += calcTimeTaken(task_lengths.get(this.assignments.get(i).get(j) - 1), this.processor_speeds.get(i));
            }

            if(processortime > maxtimetaken){
                maxtimetaken = processortime;
            }
        }


        return maxtimetaken;
    }


    //return true if the node has been assigned this task
    public boolean hasTask(int tasknum){
        for(ArrayList<Integer> assignment : assignments){
            for(int task : assignment){
                if(tasknum == task){ return true; }
            }
        }
        return false;
    }


    public String toString(){
        String output = "";
        for(int i=0; i<assignments.size(); i++){
            output += ("P" + (i+1) + ": ");
            output += (assignments.get(i).toString());
            output += "\n";
        }
        return output;
    }


    public String toAnswer(){
        String output = "";

        int numtasks = this.task_lengths.size();

        for(int i=1; i<numtasks+1; i++){

            String taskassignment = "";

            //loop through processors
            for(int j=0; j<this.assignments.size(); j++){
                if(this.assignments.get(j).contains(i)){
                    taskassignment = Integer.toString(j+1);
                }
            }
            if(taskassignment.equals("")){
                taskassignment = "0";
            }
            output += taskassignment + " ";

        }

        return output;
    }


    ///// STATIC HELPER METHODS /////

    // calculate the time it takes to complete a certain task
    private static float calcTimeTaken(float task_length, float processor_speed){
        return ((float) task_length) / (float) processor_speed;
    }

    // create a copy of the assignments arraylist
    public static ArrayList<ArrayList<Integer>> copyAssignments(ArrayList<ArrayList<Integer>> oldassignments){
        ArrayList<ArrayList<Integer>> newassignments = new ArrayList<>();
        for(ArrayList<Integer> assignment : oldassignments){
            newassignments.add(new ArrayList<Integer>(assignment));
        }
        return newassignments;
    }



}
