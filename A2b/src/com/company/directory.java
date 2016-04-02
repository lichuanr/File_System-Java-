package com.company;

/**
 * Created by lichuanr on 2016-03-19.
 */


import java.util.*;

public class directory extends fileSystem{
    /**
     * name of the directory
     */
    private String name = "";
    /**
     * The parent of root directory defined to be null
     */
    private fileSystem parent = null;
    /**
     * The ArrayList to store the sub-directory and files
     */
    private ArrayList<fileSystem> contentList = new ArrayList<fileSystem>();
    /**
     * Constructor
     * @param name name of the directory
     */
    public directory(String name){
        this.name = name;
    }
    /**
     * Constructor
     * @param name name of the directory
     * @param parent parent of the current directory
     */
    public directory(String name, fileSystem parent){
        this.name = name;
        this.parent = parent;
    }
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
     * @param contentList ArrayList
     */
    public void setList(ArrayList<fileSystem> contentList){
        this.contentList = contentList;
    }
    /**
     * Set parent for the current object
     * @param parent new directory
     */
    public void setParent(fileSystem parent){
        this.parent = parent;
    }
    /**
     * Set name for the current object
     * @param name new directory
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

}

