package com.company;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lichuanr on 2016-03-20.
 */

/*This is class is mainly used for command validation*/
public class command {
    /**
     * The command line input
     */
    private String cmd = "";
    /**
     * The length of command line input
     */
    int len = 0;
    /**
     * The redefined format of command line input
     */
    private String[] splited = null;
    /**
     * Arraylist used to track the history command lines
     */
    public static ArrayList<String> historyList = new ArrayList<String>();
    /**
     * hashmap to define the format of each input command line
     */
    public HashMap<String, Integer> map = new HashMap<String, Integer>(){{
        /*
        since mkdir and cat supports multiple make -> at least one argument
        since pwd and ls supports multiple make -> at least none argument
        */
        put("mkdir", 1);
        put("cat", 1);
        put("ls", 0);
        put("pwd", 0);

        /*
        history -> at most one argument
        */
        put("history", 1);
        //echo can be 1 or 3
        put("echo", 3);

        /*
        normal -> same number of argument
        */
        put("cd", 1);
        put("exit", 0);
        put("man", 1);
        put("pushd", 1);
        put("popd", 0);
        put("mv", 2);
        put("cp", 2);
    }};

    /**
     Constructor
     */
    public command() {
    }

    /**
     * Constructor
     * - track the command line history
     * - split the user input by space
     * @param cmd command line
     */
    public command(String cmd){
        //tracking the input first
        historyList.add(cmd);

        this.cmd = cmd;
        String[] splited = cmd.split("\\s+");
        this.len = splited.length;
        this.splited = splited;
    }

    /**
     * Gets a directory by a given string path and move the cursor of Jshell
     * null return value, indicating that the directory was not found.
     *
     * @param splited  relative or absolute path
     * @param pointer  the current position of user in the Jshell
     * @return The new position of the Jshell. Returns null if the path is invalid
     */
    public fileSystem changeDirectory(ArrayList<String> splited, fileSystem pointer) {
        if (splited.get(0).equals("root")){
            pointer = JShell.fs;
            splited.remove(0);
        }

        for(String step: splited){
            if (step.equals("..")){
                fileSystem parent = pointer.getParent();
                if(parent != null){
                    pointer = parent;
                }
                else {
                    System.out.println("Invalided argument - This is the root");
                    return null;
                }
            }
            else if(step.equals(".")){
                continue;
            }
            else {
                //checking the input is directory
                if (pointer instanceof file) {
                    System.out.println("Invalided argument - Invalided path ");
                    return null;
                }

                //checking the next level
                int index = pointer.contain(step, pointer.getList());
                if(index != -1){
                    pointer = pointer.getList().get(index);
                }
                else{
                    System.out.println("Invalided argument - - Invalided path ");
                    return null;
                }
            }
        }
        return pointer;
    }

    /**
     * Get the private instance, cmd
     * @return the input cmd
     */
    public String getCommand(){
        return cmd;
    }

    /**
     * Use in the ls -r or ls -R function
     */
    public void printSF(){
    }

    /**
     * Use in mv function to count the path length
     */
    public int pathcounter() {
        return -1;
    }

    /**
     * Use in specific command line functions
     */
    public void callFilesystem() {
    }


    /**
     * splite the relative for later processing
     * @param input relative or absolute path
     * @return The arrayList of string
     */
    public ArrayList<String> pathsplit(String input){
        String[] result = input.split("\\/");
        ArrayList<String> temp = new ArrayList<String>();

        temp.addAll(Arrays.asList(result));
        if (temp.get(0).equals("")){
            temp.remove(0);
        }
        return temp;
    }

    /**
     * Call different command line functions, based on the user input
     * @return integer indicating the program finished or not, ex: -1 means exit
     */
    public int executor(){
        if (this.validator() == false){
            System.out.println("Invalided argument - input format problem");
            return 0;
        }
        //start execute the command line in the bash
        /*
            1, command lines deal with directory
            including: mkdir, cd, ls, pwd
         */
        if(splited[0].equals("mkdir")) {
            for(int i = 1; i < splited.length; i++) {
                ArrayList<String> temp = pathsplit(splited[i]);
                command input = new mkdir(temp);
                input.callFilesystem();
            }
        }
        else if(splited[0].equals("cd")) {
            ArrayList<String> temp = pathsplit(splited[1]);
            command input = new cd(temp);
            input.callFilesystem();
        }
        else if(splited[0].equals("ls")) {
            ArrayList<String> temp = null;

            //recursive query + with path
            if ( splited.length > 2 && (splited[1].equals("-r") || splited[1].equals("-R"))){
                for (int i = 2; i < splited.length; i++) {
                    temp = pathsplit(splited[i]);
                    command input = new ls(temp);
                    input.printSF();
                    if (i != splited.length-1){
                        System.out.println();
                    }
                }
            }
            //recursive query + without path
            else if(splited.length > 1 && (splited[1].equals("-r") || splited[1].equals("-R"))){
                command input = new ls(temp);
                input.printSF();
            }

            //normal query + with path
            else if (splited.length > 1){
                boolean mulDir = true;
                if (splited.length <= 2){
                    mulDir = false;
                }
                for (int i = 1; i < splited.length; i++) {
                    temp = pathsplit(splited[i]);
                    command input = new ls(temp, mulDir);
                    input.callFilesystem();
                    //break line for each directory
                    if (i != splited.length-1){
                        System.out.println();
                    }
                }

            }
            //normal query + without path
            else{
                command input = new ls(temp, false);
                input.callFilesystem();
            }
        }
        else if(splited[0].equals("pwd")) {
            if (splited.length == 1) {
                command input = new pwd();
                input.callFilesystem();
            }
            else{
                ArrayList<String> temp = pathsplit(splited[2]);
                command input = new pwd(temp);
                input.callFilesystem();
            }
        }
        /*
            2, command lines deal with files
            including: echo, cat
        */
        else if(splited[0].equals("echo")) {
            if (splited.length == 2) {
                System.out.println(splited[1]);
            }
            else{
                String content = splited[1];
                String sign = splited[2];
                ArrayList<String> temp = pathsplit(splited[3]);
                command input = new echo(content, sign, temp);
                input.callFilesystem();
            }

        }
        else if(splited[0].equals("cat")) {
            for(int i = 1; i < splited.length; i++){
                ArrayList<String> temp = pathsplit(splited[i]);
                command input = new cat(temp);
                input.callFilesystem();
            }
        }
        /*
            3, command lines deal with fs stack
            including: pushd, popd
        */
        else if(splited[0].equals("pushd")) {
            ArrayList<String> temp = pathsplit(splited[1]);
            command input = new pushd(temp);
            input.callFilesystem();
        }

        else if(splited[0].equals("popd")) {
            String step = JShell.fs.popStack();
            if (step.length() != 0){
                ArrayList<String> temp = pathsplit(step);
                command input = new popd(temp);
                input.callFilesystem();
            }
        }
        /*
            4, command lines deal with history record
            including: history #, history
            note: including the history command itself
        */
        else if(splited[0].equals("history")) {
            int argnum = historyList.size();
            int counter = argnum;
            if (splited.length > 1) {
                counter = Integer.parseInt(splited[1]);
            }

            for(int i = (argnum - counter); i < argnum; i++){
                int index = i + 1;
                System.out.println(index + ". " + historyList.get(i));
            }
        }
        /*
            6, command lines deal with Linux instructions
            including: man CMD
        */
        else if(splited[0].equals("man")) {
            command input = new man(splited[1]);
            input.callFilesystem();
        }
        /*
            7, command lines deal with bash exit
            including: exit
        */
        else if(splited[0].equals("exit")) {
            return -1;
        }
        /*
            8, command lines deal with file move and copy
            including: mv and cp
        */
        else if(splited[0].equals("mv")){
            ArrayList<String> temp1 = pathsplit(splited[1]);
            ArrayList<String> temp2 = pathsplit(splited[2]);

            command input = new mv(temp1, temp2);
            input.callFilesystem();
        }
        else if(splited[0].equals("cp")){
            ArrayList<String> temp1 = pathsplit(splited[1]);
            ArrayList<String> temp2 = pathsplit(splited[2]);

            command input = new cp(temp1, temp2);
            input.callFilesystem();
        }
        return 0;
    }
    /**
     * Validate all the command line before execution
     * @return boolean indicating the correctness of input command format
     */
    public boolean validator(){
        //overall checking the number of statement
        if (splited.length > 0){
            //checking the valid command first
            if (map.containsKey(splited[0])) {
                //check for the number of statement for the relevant command
                if (len != map.get(splited[0])+1) {
                    //at least case
                    if(splited[0].equals("mkdir")||splited[0].equals("cat") || splited[0].equals("ls")) {
                        if(len > map.get(splited[0])+1){
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    //at most case
                    if(splited[0].equals("history") && (len < map.get(splited[0])+1)) {
                        return true;
                    }
                    else if(splited[0].equals("echo") && (len == 2)) {
                        return true;
                    }
                    else if(splited[0].equals("pwd") && (len == 3) && splited[1].equals(">")) {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else if(splited[0].equals("echo") && (!splited[2].equals(">") && !splited[2].equals(">>"))) {
                    return false;
                }
                else{
                    return true;
                }
            }
            else{
                return false;
            }
        }
        return false;
    }

}
