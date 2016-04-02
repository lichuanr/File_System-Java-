package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-21.
 */
public class echo extends command{
    private ArrayList<String> splited = null;
    private fileSystem pointer = null;
    private String content = "";
    private String sign = "";
    private String filename = "";
    public echo(String content, String sign, ArrayList<String> cmd) {
        this.filename = cmd.get(cmd.size() - 1);
        cmd.remove(cmd.size() - 1);
        this.splited = cmd;
        this.content = content;
        this.sign = sign;
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
            else if (!step.equals("..")){
                //checking the input is directory
                if (pointer instanceof file) {
                    System.out.println("Invalided argument - Invalided path1 ");
                    return -1;
                }

                //checking the next level
                int index = pointer.contain(step);
                if(index != -1){
                    pointer = pointer.getList().get(index);
                }
                else{
                    System.out.println("Invalided argument - - Invalided path2 ");
                    return -1;
                }
            }
        }
        return 0;
    }

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

    public void callFilesystem() {

        //name checking
        if(validName() == -1){
            return;
        }

        //path checking
        if (this.splited.size() > 0) {
            if(changeDirectory() == -1){
                return;
            }
        }

        file f1 = exist();
        if(sign.equals(">")){
            f1.addcontent(content);
        }
        else{
            f1.appendcontent(content);
        }

    }
}
