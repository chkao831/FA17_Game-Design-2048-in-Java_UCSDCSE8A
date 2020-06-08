import javafx.application.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import java.util.*;

import java.io.*;

/**---File Header---
 * File: Gui2048.java
 * This file implements the GUI and event handling code
 * Name: Chih-Hsuan Kao
 * Login: cs8bwagy
 * Email: c4kao@ucsd.edu
 * Date: Feb 25, 2018
 * Sources of Help: Javadoc
 */

/**---Class Header---
 * The class Gui2048 inherits Application class. This class implements the GUI
 * by processing the command line arguments and creating instance of the Board
 * class. Also, this class handles keyboard events that occur while focusing 
 * on the GUI Window. 
 */
public class Gui2048 extends Application
{
    //the grid size is 4 by 4
    public final int SIZE = 4;
    //gap size is 15
    public final int GAP = 15;
    //scene WIDTH and HEIGHT is 600
    public final int SCENE = 600;
    //gameover overlay size is 700
    public final int OVERLAY = 700;
    //rectangle size is 100 by 100
    public final int RecSIZE = 100;
    //Color for empty grid
    final Color COLOR_BACK_POWDERBLUE = Color.rgb(176,224,230);
    
    //the filename for where to save the Board to
    private String outputBoard; 
    //the pane to hold visual objects
    private GridPane pane;
    //create a field of label to indicate score
    Label scoreIndicator = new Label("");
    //instance of the Board class
    private Board board = new Board(new Random(), SIZE);
    //boolean to check if the game is over
    private boolean gameOver = false;

    //The following instances variables are for Tile inner class
    //labelNumber to indicate which number it is
    private Label labelNumber;
    //rectangle object corresponding to the tile
    private Rectangle addTile;
    //tile class 2d arrays to combine the color and number property 
    private Tile[][] gridTile = new Tile[SIZE][];
    //boolean to check if firstTime to update the grid in update method 
    private boolean firstTime = true;

    @Override
    public void start(Stage primaryStage)
    {

        /// Create the pane that will hold all of the visual objects
        pane = new GridPane();
        pane.setAlignment(Pos.CENTER);
        //the padding value is provided in the starter code
        pane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));

        //midnight blue colored gridpane
        pane.setStyle("-fx-background-color: rgb(25, 25, 112)");
        //set gap among pane
        pane.setHgap(GAP); 
        pane.setVgap(GAP);

        //set title to the stage
        primaryStage.setTitle("Gui2048");

        //create a scene and then add pane to the scene
        Scene scene = new Scene(pane,SCENE,SCENE);

        //add the scene to the stage so that the pane can be displayed

        primaryStage.setScene(scene);
        primaryStage.show();

        // Process Arguments and Initialize the Game Board
        processArgs(getParameters().getRaw().toArray(new String[0]));

        //create 2048 label 
        Label label2048 = new Label("2048");
        //set label fonts
        label2048.setFont(Font.font("Times New Roman",FontWeight.BOLD,
                    FontPosture.ITALIC,Constants2048.TEXT_SIZE_LOW));
        //set label color
        label2048.setTextFill(Color.WHITE);

        //show current score
        scoreIndicator.setText("Score: " + board.getScore());
        scoreIndicator.setFont(Font.font("Times New Roman",
                    FontWeight.BOLD,
                    FontPosture.ITALIC,Constants2048.TEXT_SIZE_HIGH));
        scoreIndicator.setTextFill(Color.WHITE);

        //add score label to topright corner
        //located at column2, row0, with columnspan2, rowspan1
        pane.add(scoreIndicator,2,0,2,1);
        GridPane.setHalignment(scoreIndicator,HPos.CENTER);

        //create 2-D arrays of rectangle references
        Rectangle[][] cells = new Rectangle[SIZE][];
        for(int i=0; i<SIZE; i++){
            cells[i] = new Rectangle[SIZE];
        }

        //initailze gridTile reference
        for(int i=0; i<SIZE; i++){
            gridTile[i]=new Tile[SIZE];
        }

        //initialize gridTile object (actually assiging ref to object)
        for(int i=0; i<SIZE; i++){
            for(int j=0; j<SIZE; j++){
                gridTile[i][j]=new Tile(0);
            }
        }

        //add label2048 to topleft corner
        //located at column0, row0, with columnspan2, rowspan1
        pane.add(label2048,0,0,2,1);
        GridPane.setHalignment(label2048,HPos.CENTER);

        //assign references to 2-D arrays of rectangle objects
        for(int i=0;i<SIZE;i++){
            for(int j=0;j<SIZE;j++){
                //rectangle objects with width and height 100 
                //and background color (orange)
                cells[i][j] = new Rectangle(RecSIZE,RecSIZE,COLOR_BACK_POWDERBLUE);
                //add cells to pane
                //from the 0th column and the 1st row
                //each cell span one column and one row
                pane.add(cells[i][j],i,j+1,1,1);
            }
        }

        //update the tile
        update();
        //based on the tile just got updated,
        //catch the property of tile using for loop
        for(int i = 0; i<SIZE; i++){
            for(int j = 0; j<SIZE; j++){
                //the property of the tile (rectangle with color)
                addTile = new Rectangle(RecSIZE,RecSIZE,gridTile[i][j].getColor());
                //add the rectangle to pane
                pane.add(addTile,j,i+1,1,1);

                //the property of the tile (what number)
                labelNumber = new Label("0");
                //set the text, font and text color
                labelNumber.setText(Integer.toString(gridTile[i][j].getElement()));
                labelNumber.setFont(Font.font("Times New Roman",
                            FontWeight.BOLD,FontPosture.ITALIC,
                            Constants2048.TEXT_SIZE_HIGH));
                labelNumber.setTextFill(Color.BLACK);

                //if the text I just got is not 0 (not empty)
                //add the text to pane
                if(gridTile[i][j].getElement()!=0){
                    pane.add(labelNumber,j,i+1,1,1);
                }
                //set alignment to be in the center
                GridPane.setHalignment(labelNumber,HPos.CENTER);
            }
        }

        //Set scene and what it should listen to in start method
        //listen to KeyEvent handler
        scene.setOnKeyPressed(new KeyHandler());
    }

    /**---Inner Class, Class Header---
     * Inner Class: Tile
     * This inner class combines the Rectangle the the Text element for each
     * entry in the board's grid. 
     * @created by Chih-Hsuan Kao, cs8bwagy
     *
     * This class has two instance variables: private Color tileColor and
     * private int element.
     */
    private class Tile{

        //instance variables
        private Color tileColor;
        private int element;

        /**
         * constructor to initialize tileColor and element 
         * with a number passed in
         * @param number
         */

        public Tile(int number){
            //initialize the integer element to by the parameter
            element = number;

            //initiliaze the color based on number
            if(element == 0){
                //this color is the same as the backpane color 
                tileColor = COLOR_BACK_POWDERBLUE;
            }
            else if(element == 2){
                tileColor = Constants2048.COLOR_2;
            }
            else if(element == 4){
                tileColor = Constants2048.COLOR_4;
            }
            else if(element == 8){
                tileColor = Constants2048.COLOR_8;
            }
            else if(element == 16){
                tileColor = Constants2048.COLOR_16;
            }
            else if(element == 32){
                tileColor = Constants2048.COLOR_32;
            }
            else if(element == 64){
                tileColor = Constants2048.COLOR_64;
            }
            else if(element == 128){
                tileColor = Constants2048.COLOR_128;
            }
            else if(element == 256){
                tileColor = Constants2048.COLOR_256;
            }
            else if(element == 512){
                tileColor = Constants2048.COLOR_512;
            }
            else if(element == 1024){
                tileColor = Constants2048.COLOR_1024;
            }
            else if(element == 2048){
                tileColor = Constants2048.COLOR_2048;
            }
            else{
                tileColor = Constants2048.COLOR_OTHER;
            }
        }

        /**Method: getColor
         * getter method to get the Color of the tile
         * @param none
         * @return Color tileColor
         */
        public Color getColor(){
            return tileColor;
        }

        /**Method: getElement
         * Getter method to return element's number
         * @param none
         * @return int element's number
         */
        public int getElement(){
            return element;
        }

        /**Method: setElement
         * Setter method to set element's number
         * @param int number: the number to be set as element
         * @return none
         */
        public void setElement(int number){
            this.element = number;
        }

        /**Method: setColor
         * Setter method to set tile color
         * @param int elem: the basis by which to set color
         * @return none
         */
        public void setColor(int elem){
            //initiliaze the color based on number
            if(elem == 0){
                tileColor = COLOR_BACK_POWDERBLUE;
            }
            else if(elem == 2){
                tileColor = Constants2048.COLOR_2;
            }
            else if(elem == 4){
                tileColor = Constants2048.COLOR_4;
            }
            else if(elem == 8){
                tileColor = Constants2048.COLOR_8;
            }
            else if(elem == 16){
                tileColor = Constants2048.COLOR_16;
            }
            else if(elem == 32){
                tileColor = Constants2048.COLOR_32;
            }
            else if(elem == 64){
                tileColor = Constants2048.COLOR_64;
            }
            else if(elem == 128){
                tileColor = Constants2048.COLOR_128;
            }
            else if(elem == 256){
                tileColor = Constants2048.COLOR_256;
            }
            else if(elem == 512){
                tileColor = Constants2048.COLOR_512;
            }
            else if(elem == 1024){
                tileColor = Constants2048.COLOR_1024;
            }
            else if(elem == 2048){
                tileColor = Constants2048.COLOR_2048;
            }
            else{
                tileColor = Constants2048.COLOR_OTHER;
            }
        }

    }

    /**---Inner Class, Class Header---
     * This class KeyHandler implements EventHandler<KeyEvent> interface
     * With the override method handle, the class is capable of handling 
     * keyboard events that occur while focusing on the Gui window.
     * The Gui would be updated after a move has occurred with the new state
     * of the board.
     *
     * @created by Chih-Hsuan Kao, cs8bwagy
     */
    private class KeyHandler implements EventHandler<KeyEvent>{

        @Override
        /**Method: handle
         * This method set score, call the update method after each successful
         * move and when game is over, apply an overlay to indicate.
         * @param KeyEvent event
         * @return none
         */
        public void handle(KeyEvent event){


            //show current score
            scoreIndicator.setText("Score: " + board.getScore());
            scoreIndicator.setFont(Font.font("Times New Roman",
                        FontWeight.BOLD,FontPosture.ITALIC,
                        Constants2048.TEXT_SIZE_HIGH));
            scoreIndicator.setTextFill(Color.WHITE);
            //set the score alignment to be in the center
            GridPane.setHalignment(scoreIndicator,HPos.CENTER);

            //if the game is over
            
            if(board.isGameOver() && gameOver==false){
                gameOver = true;
                //create a white transparent overlay over the whole window with
                //the Text "Game Over!" in the center of it
                Color overlayColor = Constants2048.COLOR_OVERLAY;
                Rectangle GameOverRec = new Rectangle(OVERLAY,OVERLAY,overlayColor);
                pane.add(GameOverRec,0,0,4,5);

                GridPane.setHalignment(GameOverRec,HPos.CENTER);

                //create gameover label
                Label labelGO = new Label("Game\nOver!");

                //set label fonts
                labelGO.setFont(Font.font("Times New Roman",FontWeight.BOLD,
                            FontPosture.ITALIC,
                            Constants2048.GAMEOVER_BIGTEXT));
                //set label color
                labelGO.setTextFill(Constants2048.COLOR_GAME_OVER);
                //add the label to pane and set alignment
                pane.add(labelGO,0,1,4,4);
                GridPane.setHalignment(labelGO,HPos.CENTER);
            
            }
            //If not yet gameover and the keycode input is valid
            //save the board if the s key is pressed
            if(event.getCode() == KeyCode.S){
                try{
                    //save the board to outputFile
                    board.saveBoard(outputBoard);

                    //print out the message to the terminal 
                    System.out.println("Saving Board to outputFile");

                } catch (IOException e){

                }
            }
            //if valid keyboard key is pressed
            //get the direction and perform movement
            else if (((event.getCode())==KeyCode.UP)
                    || ((event.getCode())==KeyCode.DOWN)
                    ||((event.getCode())==KeyCode.LEFT)
                    ||((event.getCode())==KeyCode.RIGHT))
            {

                Direction dir;

                if(event.getCode()==KeyCode.UP){
                    dir = Direction.UP;
                }
                else if(event.getCode()==KeyCode.DOWN){
                    dir = Direction.DOWN;
                }
                else if(event.getCode()==KeyCode.LEFT){
                    dir = Direction.LEFT;
                }
                else{
                    dir = Direction.RIGHT;
                }
                //if a move in dir is possible, perform movement
                if(board.canMove(dir)){
                    //move the tile in this direction
                    board.move(dir);
                    //print out movement message to terminal
                    System.out.println("Moving" + dir);
                    //update score
                    scoreIndicator.setText("Score: " + board.getScore());
                    //add random tile in an empty spot
                    board.addRandomTile();

                    //update the 2d array Tile
                    update();


                    //paint out after updating tile 
                    for(int i = 0; i<SIZE; i++){
                        for(int j = 0; j<SIZE; j++){

                            //catch the tile property (colored rectangle)
                            //and paint it onto the pane
                            addTile = new Rectangle
                                (RecSIZE,RecSIZE,gridTile[i][j].getColor());
                            pane.add(addTile,j,i+1,1,1);

                            //catch the tile property (text)
                            //and paint it onto the pane after setting font and
                            //color
                            labelNumber = new Label("0");
                            if(gridTile[i][j].getElement()!=0){
                                pane.add(labelNumber,j,i+1,1,1);
                                labelNumber.setText
                                    (Integer.toString(gridTile[i][j].getElement()));
                                labelNumber.setFont(Font.font("Times New Roman",
                                            FontWeight.BOLD,FontPosture.ITALIC,
                                            Constants2048.TEXT_SIZE_HIGH));
                                labelNumber.setTextFill(Color.BLACK);
                                //set pane alignment of the text
                                GridPane.setHalignment(labelNumber,HPos.CENTER);
                            }
                        }
                    }//paint out nested for loop ending bracket
                }//if board canMove ending bracket
            }//if valid keyboard ending bracket
        } //Handler method ending bracket
    } //KeyHandler inner class ending bracket


    /**Method: update
     * Update the Tile in the front after board is updated in the back
     * Name: Chih-Hsuan Kao, cs8bwagy
     * @param none
     * @return none
     */
    public void update(){
        //catch the updated value on the board
        int[][] intArray = board.getGrid();
        for(int i = 0; i<SIZE; i++){
            for(int j = 0; j<SIZE; j++){

                //if it;s the first time updating
                //initialze all instance variable getGrid
                if(firstTime){

                    gridTile[i][j].setElement(intArray[i][j]);
                    gridTile[i][j].setColor(intArray[i][j]);

                    //this will stop running the first time loop
                    if(i==SIZE-1 && j==SIZE-1){
                        firstTime = false;
                    }
                }
                //if not first time
                else{
                    //update the gridTile if the Board was updated
                    if(intArray[i][j] != gridTile[i][j].getElement()){
                        gridTile[i][j].setElement(intArray[i][j]);
                        gridTile[i][j].setColor(intArray[i][j]);
                    } 
                }
            }
        }
    }

    /** DO NOT EDIT BELOW */

    // The method used to process the command line arguments
    private void processArgs(String[] args)
    {
        String inputBoard = null;   // The filename for where to load the Board
        int boardSize = 0;          // The Size of the Board

        // Arguments must come in pairs
        if((args.length % 2) != 0)
        {
            printUsage();
            System.exit(-1);
        }

        // Process all the arguments 
        for(int i = 0; i < args.length; i += 2)
        {
            if(args[i].equals("-i"))
            {   // We are processing the argument that specifies
                // the input file to be used to set the board
                inputBoard = args[i + 1];
            }
            else if(args[i].equals("-o"))
            {   // We are processing the argument that specifies
                // the output file to be used to save the board
                outputBoard = args[i + 1];
            }
            else if(args[i].equals("-s"))
            {   // We are processing the argument that specifies
                // the size of the Board
                boardSize = Integer.parseInt(args[i + 1]);
            }
            else
            {   // Incorrect Argument 
                printUsage();
                System.exit(-1);
            }
        }

        // Set the default output file if none specified
        if(outputBoard == null)
            outputBoard = "2048.board";
        // Set the default Board size if none specified or less than 2
        if(boardSize < 2)
            boardSize = 4;

        // Initialize the Game Board
        try{
            if(inputBoard != null)
                board = new Board(new Random(), inputBoard);
            else
                board = new Board(new Random(), boardSize);
        }
        catch (Exception e)
        {
            System.out.println(e.getClass().getName() + 
                    " was thrown while creating a " +
                    "Board from file " + inputBoard);
            System.out.println("Either your Board(String, Random) " +
                    "Constructor is broken or the file isn't " +
                    "formated correctly");
            System.exit(-1);
        }
    }

    // Print the Usage Message 
    private static void printUsage()
    {
        System.out.println("Gui2048");
        System.out.println("Usage:  Gui2048 [-i|o file ...]");
        System.out.println();
        System.out.println("  Command line arguments come in pairs of the "+ 
                "form: <command> <argument>");
        System.out.println();
        System.out.println("  -i [file]  -> Specifies a 2048 board that " + 
                "should be loaded");
        System.out.println();
        System.out.println("  -o [file]  -> Specifies a file that should be " + 
                "used to save the 2048 board");
        System.out.println("                If none specified then the " + 
                "default \"2048.board\" file will be used");  
        System.out.println("  -s [size]  -> Specifies the size of the 2048" + 
                "board if an input file hasn't been"); 
        System.out.println("                specified.  If both -s and -i" + 
                "are used, then the size of the board"); 
        System.out.println("                will be determined by the input" +
                " file. The default size is 4.");
    }
}
