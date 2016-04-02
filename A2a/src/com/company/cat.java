package com.company;

/**
 * Created by lichuanr on 2016-03-21.
 */
import com.company.JShell;
import java.util.ArrayList;

public class cat extends command{
    private ArrayList<String> splited = null;
    private fileSystem pointer = null;
    public cat(ArrayList<String> cmd) {
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
        if (this.splited != null) {
            if(changeDirectory() == -1){
                return;
            }
        }
        //validation checking
        if (pointer instanceof directory) {
            System.out.println("Invalided argument - not file");
            return;
        }
        else if(pointer instanceof file){
            System.out.println(pointer.getContent());
            return;
        }
        else{
            System.out.println("Invalided argument - not file");
            return;
        }
    }
}