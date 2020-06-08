//------------------------------------------------------------------//
// PSA3Tester.java                                                  //
//                                                                  //
// Simple Tester for students to use while doing PSA3               //
//------------------------------------------------------------------//

/** DO NOT MODIFY */

import java.util.*;
import java.io.*;

public class PSA3Tester
{
    private static int SEED = 2017;
    private static String inputBoard = "psa3Test.board";
    private static String outputBoard = "student.board";
    private static int inputScore = 2481632;
    private static int[][] inputGrid = {{2,128,256,32768},
                                        {2,64,512,16384},
                                        {4,32,1024,8192},
                                        {8,16,2048,4096}};

    // All these test boards are based off the SEED 2017
    private static int[][][] testGrid = {{{0,0,0,0},
                                          {2,0,0,0},    // Grid 0
                                          {0,2,0,0},
                                          {0,0,0,0}},

                                         {{0,0,0,0},
                                          {2,0,0,0},    // Grid 1
                                          {2,2,0,0},
                                          {0,0,0,0}},

                                         {{0,0,0,0},
                                          {2,0,0,0},    // Grid 2
                                          {2,2,0,0},
                                          {2,0,0,0}},

                                         {{0,0,0,0},
                                          {2,0,0,0},    // Grid 3
                                          {2,2,0,0},
                                          {2,0,0,2}},

                                         {{0,0,0,0},
                                          {2,0,0,0},    // Grid 4
                                          {2,2,0,2},
                                          {2,0,0,2}},

                                         {{0,0,0,0},
                                          {2,0,0,2},    // Grid 5
                                          {2,2,0,2},
                                          {2,0,0,2}},

                                         {{0,0,0,0},
                                          {2,0,0,2},    // Grid 6
                                          {2,2,2,2},
                                          {2,0,0,2}}};

    public static void main(String[] args) throws IOException,
                                                  InterruptedException
    {
        System.out.println("Begining Testing PSA3");

        testBoardCtor1();
        testBoardCtor2();
        testAddRandomTile();
        testSaveBoard();
        
        
        testCanMove();
        testIsGameOver();
        testMove();
    }


    private static void testBoardCtor1() throws IOException
    {
        Board studentBoard;

        // Test the Constructor which generates a new board
        System.out.print("Testing Board(random, int) Constructor......");

        try
        {
            studentBoard = new Board(new Random(SEED), 4);

            int[][] studentGrid = studentBoard.getGrid();
            if(studentGrid == null)
            {
                System.out.println("FAILED!");
                System.out.println("Your grid (2D int array) is null " +
                                   "and hasn't been initialized!");
                return;
            }

            if(studentBoard.GRID_SIZE != 4)
            {
                System.out.println("FAILED!");
                System.out.println("You didn't set the GRIDSIZE correctly");
                System.out.println("Should be: " + 4 +
                                   " Yours is: " + studentBoard.GRID_SIZE);
                return;
            }

            if(studentBoard.getScore() != 0)
            {
                System.out.println("FAILED!");
                System.out.println("Score is incorrect");
                System.out.println("Should be: " + 0 +
                                   " Yours is: " + studentBoard.getScore());
                return;
            }

            if(!Arrays.deepEquals(testGrid[0], studentBoard.getGrid()))
            {
                System.out.println("FAILED!");
                System.out.println("Your 2D grid isn't correct");
                System.out.println("This may be due to your " +
                                   "addRandomTile Method");
                System.out.println("Should be: ");
                print2DArray(testGrid[0]);
                System.out.println("Yours is: ");
                print2DArray(studentBoard.getGrid());
                return;
            }

            System.out.println("Passed!");
        }
        catch(Exception e)
        {
            System.out.println("FAILED!");
            System.out.println("Exception was thrown while trying " +
                               "to create a new Board");
            e.printStackTrace();
            return;
        }
    }

    private static void testBoardCtor2() throws IOException
    {
        Board studentBoard;
        // Test the Constructor which Loads a saved board
        System.out.print("Testing Board(random, string) Constructor...");

        try
        {
            studentBoard = new Board(new Random(SEED),inputBoard);

            int[][] studentGrid = studentBoard.getGrid();
            if(studentGrid == null)
            {
                System.out.println("FAILED!");
                System.out.println("Your grid (2D int array) is null " +
                                   "and hasn't been initialized!");
                return;
            }

            if(studentBoard.GRID_SIZE != 4)
            {
                System.out.println("FAILED!");
                System.out.println("You didn't set the GRIDSIZE correctly");
                System.out.println("Should be: " + 4 +
                                   " Yours is: " + studentBoard.GRID_SIZE);
                return;
            }

            if(studentBoard.getScore() != inputScore)
            {
                System.out.println("FAILED!");
                System.out.println("Score is incorrect");
                System.out.println("Should be: " + inputScore +
                                   " Yours is: " + studentBoard.getScore());
                return;
            }

            if(!Arrays.deepEquals(inputGrid, studentBoard.getGrid()))
            {
                System.out.println("FAILED!");
                System.out.println("Your 2D grid isn't correct");
                System.out.println("You didn't load the board properly");
                System.out.println("Should be: ");
                print2DArray(inputGrid);
                System.out.println("Yours is: ");
                print2DArray(studentBoard.getGrid());
                return;
            }

            System.out.println("Passed!");
        }
        catch(Exception e)
        {
            System.out.println("FAILED!");
            System.out.println("Exception was thrown while trying " +
                               "to load a board from a file");
            e.printStackTrace();
            return;
        }
        catch(OutOfMemoryError e)
        {
            System.out.println("FAILED!");
            System.out.println("Check how you are parsing the input " +
                               "file.  You are probably using the score\n" +
                               "as the GRID_SIZE which causes an exception " +
                               "when trying to create the grid array.");
            e.printStackTrace();
            return;
        }
    }

    private static void testAddRandomTile()
    {
        System.out.print("Testing addRandomTile Method................");

        try
        {
            Board studentBoard = new Board(new Random(SEED), 4);

            if(studentBoard.getGrid() == null)
            {
                System.out.println("FAILED!");
                System.out.println("Your grid (2D int array) is null " +
                                   "and hasn't been initialized!");
                return;
            }

            for(int i = 0; i < testGrid.length; i++)
            {
                if(!Arrays.deepEquals(testGrid[i], studentBoard.getGrid()))
                {
                    System.out.println("FAILED!");
                    System.out.println("Your 2D grid isn't correct");
                    System.out.println("Your addRandomTile method didn't " +
                                       "add the proper tile in the correct " +
                                       "location");
                    System.out.println("Should be: ");
                    print2DArray(testGrid[i]);
                    System.out.println("Yours is: ");
                    print2DArray(studentBoard.getGrid());
                    return;
                }

                studentBoard.addRandomTile();
            }

            System.out.println("Passed!");
        }
        catch (Exception e)
        {
            System.out.println("FAILED!");
            System.out.println("Exception was thrown while trying to " +
                               "run the addRandomTile Method");
            e.printStackTrace();
            return;
        }
    }

    private static void testSaveBoard() throws IOException,
                                               InterruptedException
    {
        System.out.print("Testing saveBoard Method....................");

        try
        {
            Board studentBoard = new Board(new Random(SEED), inputBoard);

            studentBoard.saveBoard(outputBoard);

            String command = "diff " + outputBoard + " " + inputBoard;
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            if(p.exitValue() == 0)
            {
                System.out.println("Passed!");
            }
            else
            {
                System.out.println("FAILED!");
                System.out.println("Your saveBoard method is incorrect");
                System.out.println("Run the following command to see the " +
                                   "differences:\n\t" + command);
                return;
            }
        }
        catch (Exception e)
        {
            System.out.println("FAILED!");
            System.out.println("Exception was thrown while trying to " +
                               "save the board to a file");
            e.printStackTrace();
            return;
        }
        catch(OutOfMemoryError e)
        {
            System.out.println("FAILED!");
            System.out.println("Check how you are parsing the input " +
                               "file.  You are probably using the score\n" +
                               "as the GRID_SIZE which causes an exception " +
                               "when trying to create the grid array.");
            e.printStackTrace();
            return;
        }
    }

    private static void print2DArray(int[][] array)
    {
       for(int i = 0; i < array.length; i++)
           System.out.println(Arrays.toString(array[i]));
    }
    
    
    
    
    
    
    private static void testIsGameOver() throws IOException, InterruptedException, SecurityException {

        try {
            Board studentBoard1, studentBoard2;
            boolean passed1 = true, passed2 = true;
            boolean result1 = true;
            boolean result2 = false;

            //Creating two student boards
            studentBoard1 = new Board(new Random(), "gameOver0.in");

            studentBoard2 = new Board(new Random(), "gameOver1.in");

            System.out.print("Testing isGameOver() Method..");

            if (studentBoard1.isGameOver() != result1) passed1 = false;
            if (studentBoard2.isGameOver() != result2) passed2 = false;

            if (passed1 == true && passed2 == true)
                System.out.println("PASSED!!");
            else {
                System.out.println("");
                if (passed1 == false) {
                    System.out.println("Your isGameOver method failed for gameOver0.in file");
                    System.out.println("Your method returned : false");
                    System.out.println("The correct reutrn value should be: true");
                }
                if (passed2 == false) {
                    System.out.println("Your isGameOver method FAILED for gameOver1.in file");
                    System.out.println("Your method returned : true");
                    System.out.println("The correct return value should be : false");
                }
            }
        } catch (Exception e) {
            System.out.println("Your isGameOver method caught an exception, here is the exception detail: " + e);
        }
    }

    //Testing canMove method on canMove1.in and canMove2.in
    private static void testCanMove() throws IOException, InterruptedException {
        try {
            Board studentBoard1, studentBoard2;
            int passed1 = 0;
            int passed2 = 0;
            int failed1 = 0;
            int failed2 = 0;
            int i = 0;

            System.out.print("Testing canMove() Method..");

            //Creating 2 new student boards
            studentBoard1 = new Board(new Random(), "canMoveTest1.in");
            studentBoard2 = new Board(new Random(), "canMoveTest2.in");
            //The correct outputs for the two boards
            Boolean[] result1 = {false, true, false, true};
            Boolean[] result2 = {true, true, true, true};
            //Storing the student's results for comparison
            Boolean[] studentResult1 = new Boolean[4];
            Boolean[] studentResult2 = new Boolean[4];
            //Looping for canMove1.in for all 4 directions
            for (Direction dir : Direction.values()) {
                studentResult1[i] = studentBoard1.canMove(dir);
                // Check to see if the the student board matches the solutions
                if (result1[i] == studentResult1[i])
                    passed1++;
                else
                    failed1++;
                i++;
            }
            i = 0;
            //Looping for canMove2.in for all 4 directions
            for (Direction dir : Direction.values()) {
                studentResult2[i] = studentBoard2.canMove(dir);
                // Check to see if the the student board matches the solutions
                if (result2[i] == studentResult2[i])
                    passed2++;
                else
                    failed2++;
                i++;
            }

            //If all cases pass, output PASSED
            if (passed1 == 4 && passed2 == 4) {
                System.out.println("PASSED!");
            } else {
                System.out.println("");
                if (failed1 > 0) {
                    System.out.println("Failed for canMoveTest1.in board!");
                    System.out.println("Your results for this board are(UP,DOWN,LEFT,RIGHT): " + Arrays.toString(studentResult1));
                    System.out.println("The results for this board should be(UP,DOWN,LEFT,RIGHT): " + Arrays.toString(result1));
                }
                if (failed2 > 0) {
                    System.out.println("Failed for canMoveTest2.in board!");
                    System.out.println("Your results for this board are(UP,DOWN,LEFT,RIGHT): " + Arrays.toString(studentResult2));
                    System.out.println("The results for this board should be(UP,DOWN,LEFT,RIGHT): " + Arrays.toString(result2));
                }

            }
        } catch (Exception e) {
            System.out.println("Testing canMove() failed , please check your code . Here is the detailed error: " + e);
        }
    }

    private static void testMove() throws IOException {
        //try {
            Board studentBoard;
            int size = 7;
            Boolean passed = true;
            double score = 0.0;
            int[][] result1 = {{0, 4, 0, 2048, 8, 8, 4}, {0, 0, 0, 8, 0, 4, 4}, {0, 0, 0, 0, 0, 32, 16384}, {0, 0, 0, 0, 0, 2048, 8}, {0, 0, 0, 0, 0, 32, 2}, {0, 0, 0, 0, 0, 0, 32}, {0, 0, 0, 0, 0, 0, 0}};
            int[][] result2 = {{0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 4}, {0, 0, 0, 0, 0, 8, 4}, {0, 0, 0, 0, 0, 4, 16384}, {0, 0, 0, 0, 0, 32, 8}, {0, 0, 0, 2048, 0, 2048, 2}, {0, 4, 0, 8, 8, 32, 32}};
            int[][] result3 = {{4, 2, 0, 0, 0, 0, 0}, {2, 0, 0, 0, 0, 0, 0}, {8, 4, 0, 0, 0, 0, 0}, {4, 16384, 0, 0, 0, 0, 0}, {32, 8, 0, 0, 0, 0, 0}, {2048, 4, 2048, 2, 0, 0, 0}, {8, 4, 64, 0, 0, 0, 0}};
            int[][] result4 = {{0, 0, 0, 0, 0, 4, 2}, {0, 0, 0, 0, 0, 0, 2}, {0, 0, 0, 0, 0, 8, 4}, {0, 0, 0, 0, 0, 4, 16384}, {0, 0, 0, 0, 0, 32, 8}, {0, 0, 0, 2048, 4, 2048, 2}, {0, 0, 0, 0, 8, 4, 64}};

            System.out.print("Testing move() Method..");

            // Directed Tests

            String inputBoard = "moveTest.in";
            for (Direction dir : Direction.values()) {

                studentBoard = new Board(new Random(), inputBoard);

                // If this is a valid move check to see if
                // the resultant move matches the solutions
                studentBoard.move(dir);
                int[][] temp = studentBoard.getGrid();
                for (int i = 0; i < size; ++i) {
                    for (int j = 0; j < size; ++j) {
                        
                        if (dir == Direction.UP) {
                            
                            if (temp[i][j] != result1[i][j]) {
                                passed = false;
                                System.out.println("Your move method failed for moveTest.in.in when moved UP");
                                System.out.println("Your output for move method when moved UP is");
                                printBoardOnScreen(temp, size);
                                System.out.println("The result for move when moved UP should be");
                                printBoardOnScreen(result1, size);
                            }
                        }    
                        else if (dir == Direction.DOWN) {
                            if (temp[i][j] != result2[i][j]) {
                                passed = false;
                                System.out.println("Your move method failed for moveTest.in.in when moved DOWN");
                                System.out.println("Your output for move method when moved DOWN is");
                                printBoardOnScreen(temp, size);
                                System.out.println("The result for move when moved DOWN should be");
                                printBoardOnScreen(result2, size);
                            }
                        } 
                        else if (dir == Direction.LEFT) {
                            if (temp[i][j] != result3[i][j]) {
                                passed = false;
                                System.out.println("Your move method failed for moveTest.in.in when moved LEFT");
                                System.out.println("Your output for move method when moved LEFT is");
                                printBoardOnScreen(temp, size);
                                System.out.println("The result for move when moved LEFT should be");
                                printBoardOnScreen(result3, size);
                            }
                        }
                        else {
                            if (temp[i][j] != result4[i][j]) {
                                passed = false;
                                System.out.println("Your move method failed for moveTest.in.in when moved RIGHT");
                                System.out.println("Your output for move method when moved RIGHT is");
                                printBoardOnScreen(temp, size);
                                System.out.println("The result for move when moved RIGHT should be");
                                printBoardOnScreen(result4, size);
                            }
                        }

                    }
                }
            }
            if (passed == true) {
                System.out.print("PASSED!");
            }
        //} catch (Exception e) {
          //  System.out.println("Your move method failed, here is the detailed error: " + e);
        //}
        
    }

    public static void printBoardOnScreen(int[][] g, int size) {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                System.out.print(g[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
