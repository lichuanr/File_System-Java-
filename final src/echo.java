package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */
public class echo extends command{
    /**
     * Store the relative of absolute path
     */
    private ArrayList<String> splited = null;
    /**
     * the cursor of the Jshell
     */
    private fileSystem pointer = null;
    /**
     * the content for the file object
     */
    private String content = "";
    /**
     * operation sign for echo command
     */
    private String sign = "";
    /**
     * name of the file
     */
    private String filename = "";
    /**
     * Constructor
     * @param content the input command line
     * @param sign the input command line
     * @param cmd the input command line
     */
    public echo(String content, String sign, ArrayList<String> cmd) {
        this.filename = cmd.get(cmd.size() - 1);
        cmd.remove(cmd.size() - 1);
        this.splited = cmd;
        this.content = content;
        this.sign = sign;
        this.pointer = JShell.current;
    }
    /**
     * existence checking
     * checking the file instance in the arraylist
     * @return file
     */
    public file exist() {
        ArrayList<fileSystem> currList = pointer.getList();
        for (fileSystem item : currList){
            if (item instanceof file && item.getName().equals(this.filename)){
                //return an existing file
                return (file)item;
            }
        }
        //add new file into parent directory
        file f1 = new file(this.filename);
        pointer.addContent(f1);
        return f1;
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
        }};
        for(int i = 0; i < filename.length(); i++){
            if(list.contains(this.filename.charAt(i))){
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
            pointer = changeDirectory(this.splited, this.pointer);
            if (pointer == null) {
                return;
            }
        }

        file f1 = exist();
        if(sign.equals(">")){
            f1.addContent(content);
        }
        else{
            f1.appendContent(content);
        }

    }
}
