package com.company;

import java.util.ArrayList;


public class cp extends command {
    /**
     * Store the old path
     */
    private ArrayList<String> splited1 = null;
    /**
     * the cursor of the Jshell for the old path
     */
    private fileSystem pointer1 = null;
    /**
     * Store the new path
     */
    private ArrayList<String> splited2 = null;
    /**
     * the cursor of the Jshell for the new path
     */
    private fileSystem pointer2 = null;
    /**
     * Constructor
     * @param cmd1 the input command line for old path
     * @param cmd2 the input command line for new path
     */
    public cp(ArrayList<String> cmd1, ArrayList<String> cmd2) {
        this.splited1 = cmd1;
        this.splited2 = cmd2;
        this.pointer1 = JShell.current;
        this.pointer2 = JShell.current;
    }
    /**
     * renaming function for new path name not existing
     *  @return cmd2 the input command line for new path
     */
    public int renaming(){

        String oldName = splited1.get(splited1.size() - 1);
        String newName = splited2.get(splited2.size() - 1);

        fileSystem parent = pointer1.getParent();
        if(parent == null) {
            System.out.println("Invalided argument - root cannot change, try again");
            return -1;
        }

        int index = parent.contain(newName, parent.getList());

        //exist and diff name -> do the normal way
        if(index != -1 && (!oldName.equals(newName))){
            return 0;
        }

        //same level
        //change abs path to relative path
        if(splited2.get(0).equals("root")){
            splited2.remove(0);
        }

        for(int i = 0; i < splited2.size()-1; i++){
            if(!splited1.get(i).equals(splited2.get(i))){
                System.out.println("Invalided argument - Non-matching path, try again");
                return -1;
            }
        }

        //renaming
        if (!oldName.equals(newName)){
            parent.copy(oldName, newName, parent.getList());
        }
        //for both diff and identical cases
        return -1;
    }
    /**
     * count the input command line length
     * @param marray the input command line
     * @return length of command
     */
    public int pathcounter(ArrayList<String> marray){
        int step = 0;
        for(String item: marray) {
            if (item.equals("..")) {
                step -= 1;
            } else if (!item.equals(".") && !item.equals("root")) {
                step += 1;
            }
        }return step;
    }

    /**
     * check old path and new path relationship
     * prevent root-directory moving to the sub-directory
     * @param oldpath the input command line
     * @param newpath the input command line
     * @return boolean
     */
    public boolean relation(fileSystem oldpath, fileSystem newpath){
        while(newpath != null){
            //error, new is the child of old
            if (oldpath == newpath){
                System.out.println("Invalided argument - cannot move parent directory, try again");
                return false;
            }
            newpath = newpath.getParent();
        }
        return true;
    }
    /**
     * change directory and access the fileSystem
     */
    public void callFilesystem() {
        //old path
        //ArrayList listCopy = new ArrayList();
        pointer1 = changeDirectory(this.splited1, this.pointer1);
        if (pointer1 == null) {
            return;
        }

        //deal with non-exist new path, renaming
        //new path may be invalid in changeDirectory function.
        //We deal with it at this time
        if (pathcounter(this.splited1) == pathcounter(this.splited2)){
            if (renaming() == -1) {
                return;
            }
        }

        //new path
        pointer2 = changeDirectory(this.splited2, this.pointer2);
        if (pointer2 == null) {
            return;
        }

        //new path checking
        if (pointer2 instanceof file) {
            System.out.println("Invalided argument - not directory, try again");
            return;
        }

        //old path is subdir of new path
        if(relation(pointer1, pointer2) == false) {
            return;
        }

        fileSystem pointer3 = pointer1.deepcopy();
        pointer2.addContent(pointer3);
    }
}