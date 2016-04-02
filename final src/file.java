package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-19.
 */

public class file extends fileSystem{
    /**
     * name of the file
     */
    private String name = "";
    /**
     * The parent of the file
     */
    private fileSystem parent = null;
    /**
     * The content of the file
     */
    private String content = "";
    /**
     * Constructor
     * @param name name of the file
     */
    public file(String name){
        this.name = name;
    }
    /**
     * Constructor
     * @param name name of the file
     * @param content content of the current file
     */
    public file(String name, String content ){
        this.name = name;
        this.content = content;
    }
    /**
     * Constructor
     * @param name name of the file
     * @param content content of the current file
     * @param parent parent of the current file
     */
    public file(String name, String content, fileSystem parent ){
        this.name = name;
        this.content = content;
        this.parent = parent;
    }
    /**
     * Set the content to the file
     * @param content content of the current file
     */
    public void addContent(String content){
        this.content = content;
    }
    /**
     * append the content to the file
     * @param content content of the new file
     */
    public void appendContent(String content){
        this.content += content;
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
     * @param name new file
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
     * get content for the current object
     * @return String
     */
    public String getContent(){
        return content;
    }
    /**
     * get parent for the current object
     * @return String
     */
    public fileSystem getParent(){
        return parent;
    }


}



