package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */

import com.company.JShell;

import java.util.ArrayList;

public class cd extends command{
    /**
     * Store the relative of absolute path
     */
    private ArrayList<String> splited = null;
    /**
     * the cursor of the Jshell
     */
    private fileSystem pointer = null;
    /**
     * Constructor
     * @param cmd the input command line
     */
    public cd(ArrayList<String> cmd) {
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

        //cd only support directory
        if (pointer instanceof file) {
            System.out.println("Invalided argument - not directory, try again");
            return;
        }
        JShell.current = pointer;
    }
}

