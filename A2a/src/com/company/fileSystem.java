package com.company;

/**
 * Created by lichuanr on 2016-03-19.
 */

/*
List of functions
1, Create a root directory, pid & ppid
1, ppid should be 1, pid should be 11,
2, support the pwd and stack functions
 */


import java.util.*;

public class fileSystem {
    private String name = "root";
    private fileSystem parent = null;
    private ArrayList<fileSystem> contentList = new ArrayList<fileSystem>();

    private ArrayList<String> rootStack = new ArrayList<String>();
    public fileSystem(){}

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

    public String getContent(){
        return "Please call subclass";
    }

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

    public int contain(String name){
        for(int i = 0; i < contentList.size(); i++){
            if(contentList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public void pushStack(String path){
        rootStack.add(path);
    }

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

    public void showStack(){
        for(String item : rootStack){
            System.out.println(item);
        }
    }



}
