package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */

/*This is class is for mkdir command
* 1, multiple make
* 2, cursor move
* 3, absolute and relative path
* */
import com.company.JShell;

import java.util.ArrayList;

public class mkdir extends command{
    private ArrayList<String> splited = null;
    private fileSystem pointer = null;
    private String newdir = "";

    public mkdir(ArrayList<String> cmd) {
        this.newdir = cmd.get(cmd.size() - 1);
        cmd.remove(cmd.size() - 1);

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

    public int duplicated_item() {
        ArrayList<fileSystem> currList = pointer.getList();
        for (fileSystem item : currList){
            if (item instanceof directory && item.getName().equals(this.newdir)){
                System.out.println("Invalided argument - Duplicated directory ");
                return -1;
            }
        }
        return 0;
    }

    public int validName() {
        ArrayList<Character> list = new ArrayList<Character>() {{
            add('!');
            add('@');
            add('$');
            add('*');
            add('(');
            add(')');
            add('?');
            add(':');
            add('[');
            add(']');
            add('"');
            add('\\');
            add('/');
            add('{');
            add('}');
            add('.');
            add(' ');
        }};
        for(int i = 0; i < newdir.length(); i++){
            if(list.contains(this.newdir.charAt(i))){
                System.out.println("Invalided argument - Naming error ");
                return -1;
            }
        }
        return 0;
    }

    public void callFilesystem() {
        //name checking
        if(validName() == -1){
            return;
        }

        //path checking
        if (this.splited.size() > 0) {
            if (changeDirectory() == -1) {
                return;
            }
        }

        //duplicated element
        if (duplicated_item() == -1) {
            return;
        }
        directory d1 = new directory(this.newdir);
        pointer.addContent(d1);
    }

}
