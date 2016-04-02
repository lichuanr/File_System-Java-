package com.company;

import java.util.Scanner;
import java.util.*;

public class JShell {
    Scanner inputcommands;
    JShell jshell;

    public HashMap<String, Integer> map = new HashMap<String, Integer>(){{
        put("mkdir", 1);
        put("cd", 1);
        put("ls", 0);
        put("pwd", 0);
        //The mv command is used to move or rename files.
        put("mv", 2);
        //The cp command is used to make copies of files and directories.
        put("cp", 2);
        put("cat", 1);
        put("get", 1);
        put("echo", 3);
        put("exit", 1);
    }};

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
            //System.out.println(input);
            String[] splited = input.split("\\s+");
            int num = splited.length;

            //checking the exit
            if(splited[0].equals("exit") && num == 1) {
               break;
            }

            //overall checking the number of statement
            if (splited.length > 0){
                //checking the valid command first
                if (map.containsKey(splited[0])) {
                    //check for the number of statement for the relevant command
                    if (num != map.get(splited[0])+1) {
                        System.out.println("This is invalid");
                    }
                    else if(splited[0].equals("echo") && (!splited[2].equals(">") && !splited[2].equals(">>"))) {
                            System.out.println("This is invalid");
                    }
                    else{
                        //valid command
                        System.out.println(splited[0]);
                        //printing function
                        for(int i = 1; i < splited.length; i++){
                            System.out.print(splited[i] + " ");
                        }
                        System.out.println();
                    }
                }
                else{
                    System.out.println("This is invalid");
                }
            }
            else{
                System.out.println("This is invalid");
            }
        }
        System.out.print("finish");
    }

}
