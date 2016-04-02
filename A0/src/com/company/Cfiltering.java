// **********************************************************
// Assignment0:
// CDF user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package com.company;


import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Cfiltering {
    // this is a 2d matrix i.e. user*movie
    private int userMovieMatrix[][];
    // this is a 2d matrix i.e. user*movie
    private double userUserMatrix[][];
    private int numberOfUsers;
    private int numberOfMovies;


    //other members
    DecimalFormat dec = new DecimalFormat("0.0000");
    private double largest = -1;
    private ArrayList lArray =  new ArrayList();

    private double smallest = 2;
    private ArrayList<Integer> sArray =  new ArrayList<Integer>();

    /**
     * Default Constructor.
     */
    public Cfiltering() {
        // this is 2d matrix of size 1*1
        userMovieMatrix = new int[1][1];
        // this is 2d matrix of size 1*1
        userUserMatrix = new double[1][1];
    }

    /**
     * Constructs an object which contains two 2d matrices, one of size
     * users*movies which will store integer movie ratings and one of size
     * users*users which will store float similarity scores between pairs of
     * users.
     *
     * @param numberOfUsers Determines size of matrix variables.
     * @param numberOfMovies Determines size of matrix variables.
     */
    public Cfiltering(int numberOfUsers, int numberOfMovies) {
        this.numberOfUsers = numberOfUsers;
        this.numberOfMovies = numberOfMovies;
        // this is 2d matrix of size numberOfUsers*numberOfMovies
        userMovieMatrix = new int[numberOfUsers][numberOfMovies];
        // this is 2d matrix of size numberOfMovies*numberOfMovies
        userUserMatrix = new double[numberOfMovies][numberOfMovies];

    }

    /**
     * The purpose of this method is to populate the UserMovieMatrix. As input
     * parameters it takes in a rowNumber, columnNumber and a rating value. The
     * rating value is then inserted in the UserMovieMatrix at the specified
     * rowNumber and the columnNumber.
     *
     * @param rowNumber The row number of the userMovieMatrix.
     * @param columnNumber The column number of the userMovieMatrix.
     * @param ratingValue The ratingValue to be inserted in the userMovieMatrix
     */
    public void populateUserMovieMatrix(int rowNumber, int columnNumber,
                                        int ratingValue) {
        userMovieMatrix[rowNumber][columnNumber] = ratingValue;
        //System.out.println("For debugging:Rating is: " + ratingValue);
    }

    /**
     * Determines how similar each pair of users is based on their ratings. This
     * similarity value is represented with with a float value between 0 and 1,
     * where 1 is perfect/identical similarity. Stores these values in the
     * userUserMatrix.
     *
     * @return None
     */
    public void calculateSimilarityScore() {
        //creating the right format for the solution
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.FLOOR);

        //storing the user matrix
        for(int out_index = 0; out_index < numberOfUsers; out_index++) {
            for (int in_index = out_index + 1; in_index < numberOfUsers; in_index++) {
                //calculation
                double result = 0;
                double temp_sum = 0;
                for (int i = 0; i < numberOfMovies; i++) {
                    temp_sum += Math.pow((userMovieMatrix[out_index][i] - userMovieMatrix[in_index][i]), 2);
                }

                //formatting the double
                result = 1 / (1 + Math.pow(temp_sum, 0.5));
                result = Double.parseDouble(df.format(result));

                //tracking the biggest and smallest diff
                if (result > largest){
                    lArray.clear();
                    lArray.add(out_index);
                    lArray.add(in_index);
                    largest = result;
                }
                else if (result == largest){
                    lArray.add(out_index);
                    lArray.add(in_index);
                }

                if(result < smallest){
                    sArray.clear();
                    sArray.add(out_index);
                    sArray.add(in_index);
                    smallest = result;
                }
                else if (result == smallest){
                    sArray.add(out_index);
                    sArray.add(in_index);
                }
                //assign the result to the matrix
                userUserMatrix[out_index][in_index] = result;
                userUserMatrix[in_index][out_index] = result;
            }
        }
        //storing the 1 to user matrix
        for(int i = 0; i < numberOfUsers; i++ ){
                userUserMatrix[i][i] = 1;
        }
    }

    /**
     * Prints out the similarity scores of the userUserMatrix, with each row and
     * column representing each/single user and the cell position (i,j)
     * representing the similarity score between user i and user j.
     * calculateSimilarityScore is a helper function of this function.
     *
     * @return None
     */

    public void printUserUserMatrix() {
        ArrayList<Double> list = new ArrayList<Double>();

        //print the user matrix
        for(int i = 0; i < numberOfUsers; i++ ){
            for(int j = 0; j < numberOfUsers; j++ ){
                list.add(userUserMatrix[i][j]);
            }
            System.out.println(list);
            list.clear();
        }
    }

    /**
     * This function finds and prints the most similar pair of users in the
     * userUserMatrix.
     * @return None
     */

    public void findAndprintMostSimilarPairOfUsers() {
        System.out.println("The most similar pairs");
        for(int i = 0; i < lArray.size(); i+=2){
            String data1 = String.valueOf((int) lArray.get(i) + 1);
            String data2 = String.valueOf((int) lArray.get(i+1) + 1);
            System.out.println("User" +  data1 + " and " + "User" +data2);
        }
        System.out.println("The score is: " + largest);
    }

    /**
     * This function finds and prints the most dissimilar pair of users in the
     * userUserMatrix.
     *
     * @return None
     */
    public void findAndprintMostDissimilarPairOfUsers() {
        System.out.println("The most dissimilar pairs");
        for(int i = 0; i < sArray.size(); i+=2){
            String data1 = String.valueOf((int) sArray.get(i) + 1);
            String data2 = String.valueOf((int) sArray.get(i+1) + 1);
            System.out.println("User" +  data1 + " and " + "User" +data2);
        }
        System.out.println("The score is: " + smallest);
    }


}
