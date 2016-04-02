package com.company;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lichuanr on 2016-03-20.
 */

/*This is class is mainly used for command validation*/

public class command {
    private String cmd = "";
    int len = 0;
    private String[] splited = null;
    private static ArrayList<String> historyList = new ArrayList<String>();

    public HashMap<String, Integer> map = new HashMap<String, Integer>(){{
        /*
        since mkdir and cat supports multiple make -> at least one argument
        */
        put("mkdir", 1);
        put("cat", 1);
        /*
        history -> at most one argument
        */
        put("history", 1);
        put("ls", 1);

        /*
        normal -> same number of argument
        */
        put("cd", 1);
        put("pwd", 0);
        put("echo", 3);
        put("exit", 0);
        put("man", 1);
        put("pushd", 1);
        put("popd", 0);

        /*
        will be implemented for the next milestone
        */
        //The mv command is used to move or rename files.
        //put("mv", 2);
        //The cp command is used to make copies of files and directories.
        //put("cp", 2);
    }};

    public command() {
    }

    public command(String cmd){
        //tracking the input first
        historyList.add(cmd);

        this.cmd = cmd;
        String[] splited = cmd.split("\\s+");
        this.len = splited.length;
        this.splited = splited;
    }


    public String getCommand(){
        return cmd;
    }

    public void callFilesystem() {
    }

    public ArrayList<String> pathsplit(String input){
        String[] result = input.split("\\/");
        ArrayList<String> temp = new ArrayList<String>();

        temp.addAll(Arrays.asList(result));
        if (temp.get(0).equals("")){
            temp.remove(0);
        }
        return temp;
    }


    public int executor(){
        if (this.validator(splited) == false){
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
            if (splited.length > 1){
                temp = pathsplit(splited[1]);
            }
            command input = new ls(temp);
            input.callFilesystem();
        }
        else if(splited[0].equals("pwd")) {
            command input = new pwd();
            input.callFilesystem();
        }
        /*
            2, command lines deal with files
            including: echo, cat
        */
        else if(splited[0].equals("echo")) {
            String content = splited[1];
            String sign = splited[2];
            ArrayList<String> temp = pathsplit(splited[3]);
            command input = new echo(content, sign, temp);
            input.callFilesystem();
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
        return 0;
    }

    public boolean validator(String[]inputCmd){
        //overall checking the number of statement
        if (splited.length > 0){
            //checking the valid command first
            if (map.containsKey(splited[0])) {
                //check for the number of statement for the relevant command
                if (len != map.get(splited[0])+1) {
                    //at least case
                    if(splited[0].equals("mkdir")||splited[0].equals("cat")) {
                        if(len > map.get(splited[0])+1){
                            return true;
                        }
                        else{
                            return false;
                        }
                    }
                    //at most case
                    if((splited[0].equals("history")||splited[0].equals("ls")) && (len < map.get(splited[0])+1)) {
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
