package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */



import java.util.ArrayList;

public class ls extends command{
    /**
     * Store the relative of absolute path
     */
    private ArrayList<String> splited = null;
    /**
     * the cursor of the Jshell
     */
    private fileSystem pointer = null;
    /**
     * supporting both single directory ls and multiple directory ls
     */
    boolean multiple = false;
    /**
     * Constructor for recursive ls
     * @param cmd the input command line
     */
    public ls(ArrayList<String> cmd) {
        this.splited = cmd;
        this.pointer = JShell.current;
    }

    /**
     * Constructor for normal ls
     * @param cmd the input command line
     * @param multiple define the specific type
     */
    public ls(ArrayList<String> cmd, boolean multiple) {
        this.splited = cmd;
        this.pointer = JShell.current;
        this.multiple = multiple;
    }
    /**
     * change directory and access the fileSystem
     */
    public void callFilesystem() {
        if (this.splited != null) {
            pointer = changeDirectory(this.splited, this.pointer);
            if (pointer == null) {
                return;
            }
        }
        //validation checking
        if (pointer instanceof file) {
            System.out.println("Invalided argument - not directory");
            return;
        }

        ArrayList<fileSystem> members = pointer.getList();
        if (this.multiple == true) {
            System.out.println(pointer.getName() + ": ");
        }
        for(fileSystem item : members) {
            System.out.print(item.getName() + " ");
        }
        if (members.size() != 0) {
            System.out.println();
        }
    }

    /**
     * support recursive call
     * print entire tree structure for the file system
     */
    public void printSF() {
        if (this.splited != null) {
            pointer = changeDirectory(this.splited, this.pointer);
            if (pointer == null) {
                return;
            }
        }

        //validation checking
        if (pointer instanceof file) {
            System.out.println("Invalided argument - not directory");
            return;
        }
        pointer.printChild();
    }
}

