package com.company;

import java.util.Scanner;
import java.util.*;


public class JShell {
    Scanner inputcommands;
    JShell jshell;
    //The fs
    public static fileSystem fs = new fileSystem();
    public static fileSystem current = fs;

    public static void main(String[] args) {
        JShell jshell = new JShell();
        jshell.MonitorProcess();
    }

    public JShell() {
        inputcommands = new Scanner(System.in);
    }

    public void MonitorProcess() {
        while (true){
            System.out.print("/# ");
            String input = inputcommands.nextLine();
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
