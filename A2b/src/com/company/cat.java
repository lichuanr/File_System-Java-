package com.company;

/**
 * Created by lichuanr on 2016-03-21.
 */
import com.company.JShell;
import java.util.ArrayList;

public class cat extends command{
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
    public cat(ArrayList<String> cmd) {
        this.splited = cmd;
        this.pointer = JShell.current;
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
        if(pointer instanceof file){
            System.out.println(pointer.getContent());
            return;
        }
        else{
            System.out.println("Invalided argument - not file");
            return;
        }
    }
}