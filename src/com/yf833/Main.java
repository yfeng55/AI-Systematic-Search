package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {


    private static final String INPUT_FILE = "input.txt";
    private static final int MAX_DEPTH = 1000000;

    private static int num_tasks;
    private static int num_processors;

    private static HashSet<Integer> tasks = new HashSet<>();

    private static ArrayList<Float> task_lengths = new ArrayList<>();
    private static ArrayList<Float> processor_speeds = new ArrayList<>();
    private static float deadline;
    private static float target;


    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(INPUT_FILE);
        readInputFile(file);

        // 1. create a root node //
        Node root = new Node(processor_speeds);

        // 2. run IDFS on root node //



    }


    // iterative deepening DFS //
    private static Node IDFS(Node root){

        Node start = root;

        for(int i=0; i<MAX_DEPTH; i++){
            Node g = DLS(start, i);
            if(g != null){ return g; }
        }

        return null;
    }


    // depth limited search function //
    private static Node DLS(Node start, int depthlimit){

        Stack<Node> stack = new Stack<>();
        stack.push(start);

        while(!stack.isEmpty()){

            Node current = stack.pop();

            // check if current node is goal state
            if(isGoal(current)){
                System.out.println("FOUND GOAL");
                return current;
            }

            // check if current node is fail state
            if(isGoal(current)){
                System.out.println("FAIL STATE");
                return null;
            }

            // check if max depth is reached
            if(current.depth >= depthlimit){
                continue;
            }

            for(Node n : getAdjacentNodes(current)){
                stack.push(n);
            }

        }

        return null;
    }


    // return an array list of adjacent nodes
    private static ArrayList<Node> getAdjacentNodes(Node n){

        ArrayList<Node> adjacentnodes = new ArrayList<>();

        // get the set of unassigned tasks
        HashSet<Integer> unassigned_tasks = new HashSet<>();
        for(int task : tasks){
            if(!n.hasTask(task)){
                unassigned_tasks.add(task);
            }
        }

        // create a node for each possible processor-task assignment and add to the adjacent nodes array
        for(int task : tasks){
            for(int p=0; p<processor_speeds.size(); p++) {
                adjacentnodes.add(new Node(n, p, task));
            }
        }


        return adjacentnodes;
    }

    // goal function -- check if the node's value exceeds target
    private static boolean isGoal(Node n){
        if(n.totalValue() >= target){
            return true;
        }
        return false;
    }

    // fail function -- check if the any of the processors exceed the deadline
    private static boolean isFail(Node n){
        if(n.maxTimeTaken() > deadline){
            return true;
        }
        return false;
    }


    // initialize values from input txt file //
    private static void readInputFile(File f) throws FileNotFoundException {

        Scanner s = new Scanner(f);

        String[] firstline = s.nextLine().split(" +");
        int tasknum = 1;
        for(String c : firstline){
            task_lengths.add(Float.parseFloat(c));

            tasks.add(tasknum);
            tasknum++;
        }

        String[] secondline = s.nextLine().split(" +");
        for(String c : secondline){
            processor_speeds.add(Float.parseFloat(c));
        }

        deadline = Float.parseFloat(s.next());
        target = Float.parseFloat(s.next());

        num_tasks = task_lengths.size();
        num_processors = processor_speeds.size();
    }


    //check that input was read in correctly
    private static void printInputValues(){
        System.out.println("TASK LENGTHS: " + task_lengths.toString());
        System.out.println("PROCESSOR SPEEDS: " + processor_speeds.toString());
        System.out.println("DEADLINE: " + deadline);
        System.out.println("TARGET: " + target);
        System.out.println("NUM TASKS: " + num_tasks);
        System.out.println("NUM PROCESSORS: " + num_processors);
    }




}













