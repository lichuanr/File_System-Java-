package com.company;

/**
 * Created by lichuanr on 2016-03-19.
 */
import java.util.*;

public class fileSystem {
    /**
     * Root represents the root directory of a FileSystem
     */
    private String name = "root";
    /**
     * The parent of root directory defined to be null
     */
    private fileSystem parent = null;
    /**
     * The ArrayList to store the sub-directory and files
     */
    private ArrayList<fileSystem> contentList = new ArrayList<fileSystem>();
    /**
     * The ArrayList to support the global popd and pushd function
     */
    private ArrayList<String> rootStack = new ArrayList<String>();
    /**
     * Constructor
     */
    public fileSystem(){}
    /**
     * Add new item into current object Arraylist
     * @param dir new directory or file
     */
    public void addContent(fileSystem dir){
        dir.setParent(this);
        this.contentList.add(dir);
    }
    /**
     * initialize the arraylist for current object
     * @param  contentList ArrayList
     */
    public void setList(ArrayList<fileSystem> contentList){
        this.contentList = contentList;
    }
    /**
     * Set parent for the current object
     * @param parent new directory or file
     */
    public void setParent(fileSystem parent){
        this.parent = parent;
    }
    /**
     * Set name for the current object
     * @param name new directory or file
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     * get name for the current object
     * @return String
     */
    public String getName(){
        return name;
    }
    /**
     * get parent the current object
     * @return directory
     */
    public fileSystem getParent(){
        return parent;
    }
    /**
     * get arraylist of current object
     * @return ArrayList
     */
    public  ArrayList<fileSystem> getList(){
        return contentList;
    }
    /**
     * get content for file, only use in file object
     * @return file content
     */
    public String getContent(){
        return "Please call subclass";
    }


    /**
     * get absolute path for both root and directory object
     * @return path
     */
    public String findPath(){
        String path = "";
        fileSystem start = this;
        //System.out.println("checking1 " + start.getName());
        while(start != null){
            path = "/" + start.getName() + path;
            start = start.getParent();
        }
        return path;
    }


    /**
     * designe for popd, always call the root directory
     * @param name file or directory name
     * @return integer, find target or not
     */
    public int contain(String name){
        for(int i = 0; i < contentList.size(); i++){
            if(contentList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    /**
     * for change the directory function, only for directory checking
     * @param name file or directory name, searching target
     * @param inputList ArrayList, current object arraylist
     * @return integer, find target or not
     */
    public int contain(String name, ArrayList<fileSystem> inputList) {
        for(int i = 0; i < inputList.size(); i++) {
            if(inputList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    /**
     * for remove function, renaming purpose
     * @param name file or directory name, searching target
     * @param newName file or directory new name
     * @param inputList ArrayList, current object's parent arraylist
     */
    public void rename(String name, String newName, ArrayList<fileSystem> inputList){
        for(int i = 0; i < inputList.size(); i++) {
            if(inputList.get(i).getName().equals(name)){
                inputList.get(i).setName(newName);
                break;
            }
        }
    }

    /**
     * for copy function
     * @param name file or directory name, searching target
     * @param newName file or directory new name
     * @param inputList ArrayList, current object's parent arraylist
     */
    public void copy(String name, String newName, ArrayList<fileSystem> inputList){
        for(int i = 0; i < inputList.size(); i++) {
            if(inputList.get(i).getName().equals(name)){
                //new element, arraylist change the original content
                fileSystem newitem = inputList.get(i).deepcopy();
                newitem.setName(newName);
                inputList.add(newitem);
            }
        }
    }

    /**
     * for file and directory remove
     * @param target file or directory name, searching target
     * @param inputList ArrayList, current object's parent arraylist
     * @return integer, find target or not
     */
    public int remove(fileSystem target, ArrayList<fileSystem> inputList){
        for(int i = 0; i < inputList.size(); i++){
            if(inputList.get(i) == target){
                inputList.remove(i);
                return i;
            }
        }
        return -1;
    }

    /**
     * Only used for file object
     */
    public void addContent(String content) {
    }


    /**
     * support the cp command, fileSystem, tree structure, copy
     * @param origin pointer the original filesystem
     * @param copy new pointer for the copied filesystem
     */
    public void step(fileSystem origin, fileSystem copy) {
        if(origin instanceof file){
            String name = origin.getName();
            String newContent = origin.getContent();
            copy.addContent(new file(name, newContent));
            return;
        }
        else if (origin.getList().size() == 0) {
            copy.addContent(new directory(origin.getName()));
            return;
        }


        for (fileSystem item : origin.getList()) {
            step(item, copy);
        }

        copy = new directory(origin.getName());
        copy = copy.getParent();

    }

    /**
     * support the cp command, fileSystem, tree structure, copy
     * @return the new pointer for the copied filesystem
     */
    public fileSystem deepcopy(){
        fileSystem newdir = null;

        //initial checking for files
        if (this instanceof file){
            fileSystem newfile = new file(this.getName());
            newfile.addContent(this.getContent());
            return newfile;
        }
        else{
            newdir = new directory(this.getName());
        }

        step(this, newdir);
        return newdir;
        //return newdir.getList().get(0);
    }

    /**
     * BFS --> print helper function, only print the current level of directory and files
     * @param name parent directory name
     * @param temp arraylist of the temp
     */
    public void printhelper(String name,  ArrayList<fileSystem> temp){
        System.out.println(name + ": ");
        for(fileSystem item: temp){
            System.out.print(item.getName() + " ");
        }

        if (temp.size() != 0) {
            System.out.println();
        }
    }

    /**
     * BFS --> Support the ls -R, recursive printing the files
     */
    public void printChild(){
        ArrayList<fileSystem> queue = new ArrayList<fileSystem>();
        queue.add(this);
        int tracking = 0;
        while (queue.size() != 0){
            //current level size
            int cursize = queue.size();
            int index = 0;
            //next level element search
            while (index < cursize) {
                if (!(queue.get(0) instanceof file)){
                    fileSystem curstage = queue.get(0);
                    ArrayList temp = curstage.getList();
                    if (temp.size() != 0){
                        if (tracking == 0){
                            tracking = 1;
                        }
                        else{
                            System.out.println();
                        }
                        queue.addAll(temp);
                        printhelper(curstage.getName(), temp);
                    }
                }
                queue.remove(0);
                index += 1;
            }
        }
    }

    /**
     * only in the root class
     * Store the used the path in the stack
     * @param path the absolute path from the pushd command
     */
    public void pushStack(String path){
        rootStack.add(path);
    }

    /**
     * only in the root class
     * return the used the path in the stack
     * @return string, the absolute path from the popd command
     */
    public String popStack(){
        String path = "";
        if (rootStack.size() > 0){
            path = rootStack.get(rootStack.size()-1);
            rootStack.remove(rootStack.size()-1);
        }
        else {
            System.out.println("invalid argument, stack is empty");
        }
        return path;
    }
    /**
     * only in the root class
     * Show all the paths in the stack
     */
    public void showStack(){
        for(String item : rootStack){
            System.out.println(item);
        }
    }
}
