package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */


import java.util.ArrayList;

public class mkdir extends command{
    /**
     * Store the relative of absolute path
     */
    private ArrayList<String> splited = null;
    /**
     * the cursor of the Jshell
     */
    private fileSystem pointer = null;
    /**
     * define the name of new directory
     */
    private String newdir = "";
    /**
     * Constructor
     * @param cmd the input command line
     */
    public mkdir(ArrayList<String> cmd) {
        this.newdir = cmd.get(cmd.size() - 1);
        cmd.remove(cmd.size() - 1);

        this.splited = cmd;
        this.pointer = JShell.current;

    }
    /**
     * existence checking
     * checking the directory instance in the arraylist
     * @return cmd the input command line
     */
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
    /**
     * name checking
     * In assignment, name should not have special character
     * @return integer
     */
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
    /**
     * change directory and access the fileSystem
     */
    public void callFilesystem() {
        //name checking
        if(validName() == -1){
            return;
        }

        //path checking
        if (this.splited.size() > 0) {
            //passing by value
            pointer = changeDirectory(this.splited, this.pointer);
            if (pointer == null) {
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
