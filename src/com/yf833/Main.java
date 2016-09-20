package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {


    private static final String INPUT_FILE = "input.txt";
    private static final int MAX_DEPTH = 10;

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
        Node root = new Node(processor_speeds, task_lengths);

        // 2. compute starting point q;
        int q = computeQ(task_lengths, target);
        System.out.println("Q: " + q);

        // 3. run IDFS on root node //
//        Node goal = IDFS(root, q);
//
//        if(goal != null){
//            System.out.println(goal.toString());
//        }else{
//            System.out.println("No Solution");
//        }


        // 4. run hill-climbing
//        System.out.println(getRandomStartState().toString());
//        Node goal = hillClimbingRandomRestart();


    }


//    // TODO: hill-climbing with random restart //
//    private static Node hillClimbingRandomRestart(){
//        // fetch a random starting point
//
//
//    }
//
//    // TODO: hillClimb() - examines all neighbors
//    private static Node hillClimb(Node s){
//
//    }
//
//
//    // TODO: generateNeighbors - get states that add a task or swap two tasks
//    private static ArrayList<Node> getHillNeighbors(){
//        ArrayList<Node> neighbors = new ArrayList<>();
//
//        return neighbors;
//    }


    // getRandomStartState - get a random starting node from the search-space
    private static Node getRandomStartState(){

        // return a random combination of processor-task assignments
        ArrayList<Node> combinations = new ArrayList<>();

        Node root = new Node(processor_speeds, task_lengths);
        Stack<Node> s = new Stack<Node>();
        s.add(root);

        //while stack is not empty, keep popping from stack and generating adjacent nodes from the popped node
        while(!s.isEmpty()){

            Node current = s.pop();
            combinations.add(current);

            // get the set of unassigned tasks
            HashSet<Integer> unassigned_tasks = new HashSet<>();
            for(int task : tasks){
                if(!current.hasTask(task)){
                    unassigned_tasks.add(task);
                }
            }

            // create a node for each possible processor-task assignment and add to the stack
            for(int task : unassigned_tasks){
                for(int p=0; p<processor_speeds.size(); p++) {
                    s.add(new Node(current, p, task));
                }
            }
        }

        // get a random number from 0 - (# of combinations)
        Random r = new Random();
        int randindex = r.nextInt(combinations.size() - 0 + 1) + 0;

        return combinations.get(randindex);

    }

    // cost function - a function of a node's value deficit and time overflow
    private static float costFn(Node n){
        float value_deficit = target - n.totalValue();
        float time_overflow = n.maxTimeTaken() - deadline;
        return value_deficit + time_overflow;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // iterative deepening DFS //
    private static Node IDFS(Node root, int q){

        Node start = root;

        for(int i=q; i<MAX_DEPTH; i++){
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

            System.out.println(current.toString());

            // check if current node is goal state
            if(isGoal(current) && !isFail(current)){
                System.out.println("FOUND GOAL");
                return current;
            }
            // check if current node is fail state
            if(isFail(current)){
                System.out.println("FAIL STATE");
                continue;
            }

            // check if max depth is reached
            if(current.depth >= depthlimit){
                return null;
            }

            // add adjacent nodes to stack
            if(!current.visited){
                current.adjacent_nodes = getAdjacentNodes(current);
            }

            for(Node n : current.adjacent_nodes){
                stack.push(n);
            }

            current.visited = true;

        }

        return null;
    }

    // return an array list of adjacent nodes
    private static ArrayList<Node> getAdjacentNodes(Node n){

        ArrayList<Node> adjacentnodes = new ArrayList<>();

        // get the set of unassigned tasks
        HashSet<Integer> unassigned_tasks = new HashSet<>();
        for(int task : tasks){
            if(!n.hasTask(task)){ unassigned_tasks.add(task); }
        }

        // create a node for each possible processor-task assignment and add to the adjacent nodes array
        for(int task : unassigned_tasks){
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

    // compute starting point Q
    private static int computeQ(ArrayList<Float> task_lengths, float target){
        int q = 0;
        //sort task_lengths from highest to lowest
        ArrayList<Float> sorted_lengths = new ArrayList<>(task_lengths);
        Collections.sort(sorted_lengths);

        float total = 0;
        for(int i=sorted_lengths.size()-1; i>=0; i--){
            total += sorted_lengths.get(i);
            if(total < target){
                q++;
            }else{
                q++;
                break;
            }
        }
        return q;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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













