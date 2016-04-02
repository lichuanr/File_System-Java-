package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */


public class popd extends command{
    ArrayList<String> splited = null;
    public popd(ArrayList<String> cmd) {
        this.splited = cmd;
    }

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
            }
            else {
                int index = JShell.current.contain(step);
                if(index != -1){
                    JShell.current = JShell.current.getList().get(index);
                }
            }
        }
    }

    public void callFilesystem() {
        changeDirectory();
        JShell.fs.showStack();
    }
}