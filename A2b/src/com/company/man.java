package com.company;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lichuanr on 2016-03-22.
 */
public class man extends command{
    /**
     * Store the the command line
     */
    private String splited = "";
    /**
     * description for all the command line
     */
    private HashMap<String, String> map = new HashMap<String, String>(){{
        put("mkdir", "Make a directory");
        put("cd", "Move the cursor within the fileSystem");
        put("ls", "Check the subdirectoy and file in the current directory");
        put("pwd", "Show the absolute path of the current directory");
        put("cat", "Display file content");
        put("echo", "Write a string into a file");
        put("exit", "Exit the system");
        put("history", "Give history records of command lines");
        put("pushd", "Save current working directory");
        put("popd", "Pop saved working directory");
    }};
    /**
     * Constructor
     * @param cmd the input command line
     */
    public man(String cmd) {
        this.splited = cmd;
    }
    /**
     * check the hashmap and print the description
     */
    public void callFilesystem() {
        if (map.containsKey(this.splited)) {
            System.out.println("The instruction is used to: " + this.map.get(splited));
        }
    }
}
