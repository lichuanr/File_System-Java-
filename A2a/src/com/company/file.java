package com.company;

import java.util.ArrayList;

/**
 * Created by lichuanr on 2016-03-19.
 */


/*
Name: the name of the file
Content: the content of the file
Pid && ppid
*/
public class file extends fileSystem{
    private String name = "";
    private fileSystem parent = null;
    private String content = "";

    public file(String name){
        this.name = name;
    }

    public void addcontent(String content){
        this.content = content;
    }

    public void appendcontent(String content){
        this.content += content;
    }

    public void setParent(fileSystem parent){
        this.parent = parent;
    }

    public String getName(){
        return name;
    }

    public String getContent(){
        return content;
    }

    public fileSystem getParent(){
        return parent;
    }


}



