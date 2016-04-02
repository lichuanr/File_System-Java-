package com.company;

import java.util.Scanner;
import java.util.*;

public class JShell {
    Scanner inputcommands;
    /**
     * define a class object
     */
    JShell jshell;
    /**
     * define a new FileSystem
     */
    public static fileSystem fs = new fileSystem();
    /**
     * initialize the current fileSystem
     */
    public static fileSystem current = fs;
    /**
     * main function
     */
    public static void main(String[] args) {
        JShell jshell = new JShell();
        jshell.MonitorProcess();
    }
    /**
     * Constructor for the command line shell
     */
    public JShell() {
        inputcommands = new Scanner(System.in);
    }
    /**
     * Keep reading the input form inputcommands and call the executor function
     * Support the !number special cmd in this layer
     */
    public void MonitorProcess() {
        while (true){
            System.out.print("/# ");
            String input = inputcommands.nextLine();

            //!number special cmd, no record in history list
            if (input.substring(0, 1).equals("!") && input.substring(1).matches("\\d+")){
                int index = Integer.parseInt(input.substring(1));
                if (index >= command.historyList.size() || index <= 1){
                    System.out.println("Invalided argument - cannot find the history CMD");
                    continue;
                }
                else{
                    input = command.historyList.get(index-1);
                    System.out.println(input);
                }
            }

            command cmdline = new command(input);
            int result = cmdline.executor();

            //checking the exit
            if(result == -1) {
               break;
            }
            System.out.println("The current is: " + current.getName());
        }
        System.out.print("finish");
    }
}
