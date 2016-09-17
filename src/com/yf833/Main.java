package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    private static final String INPUT_FILE = "input.txt";

    private static int num_tasks;
    private static int num_processors;
    private static ArrayList<Number> task_lengths = new ArrayList<>();
    private static ArrayList<Number> processor_speeds = new ArrayList<>();
    private static float deadline;
    private static float target;


    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(INPUT_FILE);
        readInputFile(file);


//        System.out.println("TASK LENGTHS: " + task_lengths.toString());
//        System.out.println("PROCESSOR SPEEDS: " + processor_speeds.toString());
//        System.out.println("DEADLINE: " + deadline);
//        System.out.println("TARGET: " + target);
//        System.out.println("NUM TASKS: " + num_tasks);
//        System.out.println("NUM PROCESSORS: " + num_processors);



        


    }




    //TODO: implement goal function
    private static boolean isGoal(Node n){
        return false;
    }

    //TODO: implement fail function
    private static boolean isFail(Node n){
        return false;
    }






    // initialize values from input txt file //
    private static void readInputFile(File f) throws FileNotFoundException {

        Scanner s = new Scanner(f);

        String[] firstline = s.nextLine().split(" +");
        for(String c : firstline){
            task_lengths.add(Float.parseFloat(c));
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






}
