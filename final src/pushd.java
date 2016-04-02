package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */
public class pushd extends command{
    /**
     * Store the relative of absolute path
     */
    ArrayList<String> splited = null;
    /**
     * the cursor of the Jshell
     */
    private fileSystem pointer = null;
    /**
     * Constructor
     * @param cmd the input command line
     */
    public pushd(ArrayList<String> cmd) {
        this.splited = cmd;
        this.pointer = JShell.current;
    }
    /**
     * change directory and access the fileSystem
     */
    public void callFilesystem() {
        pointer = changeDirectory(this.splited, this.pointer);
        if (pointer == null) {
            return;
        }

        //pushd only support directory
        if (pointer instanceof file) {
            System.out.println("Invalided argument - not directory, try again");
            return;
        }
        JShell.current = pointer;
        JShell.fs.pushStack(JShell.current.findPath());
        JShell.fs.showStack();
    }
}
