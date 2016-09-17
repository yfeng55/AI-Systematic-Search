package com.yf833;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {


    private static final String INPUT_FILE = "input.txt";

    private static ArrayList<Number> task_lengths = new ArrayList<>();
    private static ArrayList<Number> processor_speeds = new ArrayList<>();
    private static float deadline;
    private static float target;


    public static void main(String[] args) throws FileNotFoundException {

        File file = new File(INPUT_FILE);
        readInputFile(file);


        System.out.println(task_lengths.toString());
        System.out.println(processor_speeds.toString());
        System.out.println(deadline);
        System.out.println(target);
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
    }


}
