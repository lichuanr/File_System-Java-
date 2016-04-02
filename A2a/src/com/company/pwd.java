package com.company;

/**
 * Created by lichuanr on 2016-03-20.
 */

import com.company.JShell;

public class pwd extends command{
    public pwd() {
    }

    public void callFilesystem() {
        System.out.println(JShell.current.findPath());
    }

}
