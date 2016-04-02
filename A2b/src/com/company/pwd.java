package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */

import com.company.JShell;

import java.util.ArrayList;

public class pwd extends command{
    /**
     * Store the relative of absolute path
     */
    private ArrayList<String> splited = null;
    /**
     * Constructor
     */
    public pwd() {}
    /**
     * Constructor
     * @param temp the input command line
     */
    public pwd(ArrayList<String> temp) {
        this.splited = temp;
    }
    /**
     * access the fileSystem or call the echo function
     */
    public void callFilesystem() {
        String result = JShell.current.findPath();
        if (splited == null) {
            System.out.println(result);
        }else{
            String content = result;
            String sign = ">";
            ArrayList<String> temp = this.splited;
            command input = new echo(content, sign, temp);
            input.callFilesystem();
        }
    }
}
