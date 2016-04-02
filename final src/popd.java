package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */


public class popd extends command{
    /**
     * Store the relative of absolute path
     */
    ArrayList<String> splited = null;
    /**
     * Constructor
     * @param cmd the input command line
     */
    public popd(ArrayList<String> cmd) {
        this.splited = cmd;
    }

    /**
     * special changeDirectory function only starts from root
     */
    public void changeDirectory() {
        if (splited.get(0).equals("root")){
            JShell.current = JShell.fs;
        }
        for(String step: splited){
            if (step.equals("..")){
                fileSystem parent = JShell.current.getParent();
                if(parent != null){
                    JShell.current = parent;
                }
                else {
                    System.out.println("Invalided argument, this is the root");
                }
            } else if(step.equals(".")){
                continue;
            } else {
                int index = JShell.current.contain(step);
                if(index != -1){
                    JShell.current = JShell.current.getList().get(index);
                }
            }
        }
    }
    /**
     * change directory and access the fileSystem
     */
    public void callFilesystem() {
        changeDirectory();
        JShell.fs.showStack();
    }
}