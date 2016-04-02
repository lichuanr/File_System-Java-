package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */
public class pushd extends command{
    ArrayList<String> splited = null;
    private fileSystem pointer = null;
    public pushd(ArrayList<String> cmd) {
        this.splited = cmd;
        this.pointer = JShell.current;
    }

    public int changeDirectory() {
        if (splited.get(0).equals("root")){
            pointer = JShell.fs;
        }

        for(String step: splited){
            if (step.equals("..")){
                fileSystem parent = pointer.getParent();
                if(parent != null){
                    pointer = parent;
                }
                else {
                    System.out.println("Invalided argument - This is the root");
                    return -1;
                }
            }
            else {
                //checking the input is directory
                if (pointer instanceof file) {
                    System.out.println("Invalided argument - Invalided path ");
                    return -1;
                }

                //checking the next level
                int index = pointer.contain(step);
                if(index != -1){
                    pointer = pointer.getList().get(index);
                }
                else{
                    System.out.println("Invalided argument - - Invalided path ");
                    return -1;
                }
            }
        }
        return 0;
    }

    public void callFilesystem() {
        if(changeDirectory() == -1){
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
