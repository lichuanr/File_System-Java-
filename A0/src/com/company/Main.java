package com.company;


import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.company.Cfiltering;

public class Main {
    public static void main(String[] args) {
        try {
            // open file to read
            String fileName;
            Scanner in = new Scanner(System.in);
            System.out.println("Enter the name of input file? ");
//            fileName = in.nextLine();
            FileInputStream fStream = new FileInputStream("input6.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fStream));

            // Read dimensions: number of users and number of movies
            int numberOfUsers = Integer.parseInt(br.readLine());
            int numberOfMovies = Integer.parseInt(br.readLine());

      /*
       * create a new Cfiltering object that contains: a) 2d matrix
       * i.e.userMovieMatrix (#users*#movies) b) 2d matrix i.e. userUserMatrix
       * (#users*#users)
       */
            Cfiltering cfObject = new Cfiltering(numberOfUsers, numberOfMovies);

            // this is a blankline being read
            br.readLine();

            // read each line of movie ratings and populate the
            // userMovieMatrix
            String row;
            // initialize of the loop to convert such thing into matrix index
            int j = -1;
            int i = -1;
            while ((row = br.readLine()) != null) {
                // allRatings is a list of all String numbers on one row
                String allRatings[] = row.split(" ");
                // this is the loop to convert such thing into matrix index
                i++;
                j = -1;
                for (String singleRating : allRatings) {

                    // populate userMovieMatrix
                    // this is the loop to convert such thing into matrix index
                    j++;
                    // make the String number into an integer
                    int c = Integer.parseInt(singleRating);
                    cfObject.populateUserMovieMatrix(i, j, c);

                    // TODO: COMPLETE THIS I.E. POPULATE THE USER_MOVIE MATRIX
                }
            }
            // close the file
            System.out.println("For debugging:Finished reading file");
            fStream.close();
            System.out.println("\n");
      /*
       * COMPLETE THIS ( I.E. CALL THE APPROPRIATE FUNCTIONS THAT DOES THE
       * FOLLOWING)
       */
            cfObject.calculateSimilarityScore();
            cfObject.printUserUserMatrix();
            System.out.println("\n");
            cfObject.findAndprintMostSimilarPairOfUsers();
            System.out.println("\n");
            cfObject.findAndprintMostDissimilarPairOfUsers();
        } catch (FileNotFoundException e) {
            System.err.println("Do you have the input file in the root folder "
                    + "of your project?");
            System.err.print(e.getMessage());
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }
}
