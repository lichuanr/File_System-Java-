package com.company;

/**
 * Created by lichuanr on 2016-03-19.
 */


/*
Arraylist: track the next level files and directory
Name: name of the current directory
Pid & ppid: The parent dir and current dir
 */


import java.util.*;

public class directory extends fileSystem{
    private String name = "";
    private fileSystem parent = null;
    private ArrayList<fileSystem> contentList = new ArrayList<fileSystem>();

    public directory(String name){
        this.name = name;
    }

    public void addContent(fileSystem dir){
        dir.setParent(this);
        contentList.add(dir);
    }


    public void setParent(fileSystem parent){
        this.parent = parent;
    }

    public String getName(){
        return name;
    }

    public fileSystem getParent(){
        return parent;
    }

    public  ArrayList<fileSystem> getList(){
        return contentList;
    }

    public int contain(String name){
        for(int i = 0; i < contentList.size(); i++){
            if(contentList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
}

