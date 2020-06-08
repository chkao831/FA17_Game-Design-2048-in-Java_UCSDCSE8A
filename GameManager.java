import java.util.*;
import java.io.*;

/* ---File Header---
 * File: GameManager
 * Purpose: To connect the movements instructed by the player as input in the
 * Board class
 * Name: Chih-Hsuan Kao
 * Login: cs8bwagy
 * Email: c4kao@ucsd.edu
 * Date: Feb 4, 2018
 * Sources of Help: Tutorhours
 **/

/**---Class Header---
 * GameManager Class contains two instance variables: Board board
 * (the actual 2048 board) and String outputBoard (file to save the
 * board to when exiting).
 * The capability of the class is to connect the movements instructed by the
 * player as input in the Board class
 */
public class GameManager {
    // Instance variables
    private Board board; // The actual 2048 board
    private String outputBoard; // File to save the board to when exiting

    /*ec*/
    private String outputRecord; // file to save the record file
    StringBuilder history = new StringBuilder(); // a string of commands history
    /*ce*/

    /**
     * This is the first constructor for GameManager Class. 
     * This constructor creates a new board with a grid size corresponding to the
     * value passed in the parameter boardsize. 
     */
    public GameManager(String outputBoard, int boardSize, Random random) {

        this.outputBoard = outputBoard; 
        this.board = new Board(random, boardSize);

    }

    /**
     * This is the second constructor for GameManager Class. 
     * This constructor loads a saves game. It loads a board using the filename
     * passed in via the inputBoard parameter. 
     */
    public GameManager(String inputBoard, String outputBoard, Random random) throws IOException {

        this.board = new Board(random,inputBoard);
        this.outputBoard = outputBoard; 

    }

    /** Method: play
     *  This method is the main play loop. It takes in input from the user to
     *  specity moves to execute. 
     *  Valid moves are:
     *     w - Move up
     *     s - Move Down
     *     a - Move Left
     *     d - Move Right
     *     q - Quit and Save Board
     * If command is invalid, then print the controls to remind the user of the
     * valid moves. 
     * One decides to quit or game is over, save the game board to the file
     * based on the outputBoard string then return
     *
     * Name: Chih-Hsuan Kao
     * Login: cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return none
     */
    public void play() throws Exception {
        System.out.println("Welcome to 2048!");
        System.out.println("Generating a New Board");
        //boolean for printing control command or not
        boolean printControl = true;
        //stop condition
        while(!this.board.isGameOver()){
            //print user control options
            if(printControl){
                this.printControls();
            }
            printControl = false;
            //print current state
            System.out.println(this.board.toString());

            //getting user's keyboard input
            Scanner scan = new Scanner(System.in);
            String userInput =scan.nextLine();
            char input = userInput.charAt(0);

            //if the user decides to quit with command q
            //save the board to the output file and exit the method
            if (userInput.length()==1 && input == 'q'){
                break;
            }
            //if the user enters a valid move
            //perform that move and add a new random tile to the board
            else if((userInput.length() ==1) 
                    && (input=='w'||input=='s'||input=='a'||input=='d')){
                System.out.println(userInput.length());
                Direction dir;
                if(input=='w'){
                    dir = Direction.UP;
                }
                else if(input =='s'){
                    dir = Direction.DOWN;
                }
                else if(input =='a'){
                    dir = Direction.LEFT;
                }
                else{
                    dir = Direction.RIGHT;
                }
                if(this.board.canMove(dir)){
                    this.board.move(dir);
                    this.board.addRandomTile();
                }
            }
            //if the user's input is not valid
            //do not add a random tile and print valid moves
            else{
                printControl = true;
            }
        }
        //if quit or game over, save board
        board.saveBoard(outputBoard);
        //print out Game Over! if the game is over
        if(this.board.isGameOver()){
            System.out.println("Game Over!");
        }
    }

    //main method to test out play method
    public static void main(String[] args){
    Random random = new Random();
    GameManager game = new GameManager("testBoard", 4, random);
    try{
    game.play();
    }
    catch(Exception e ){
    System.out.println("This is the exception "+ e);
    }
    }

    /**Method: printControls
     * This method prints the controls for the game
     * @param none
     * @return none
     */
    private void printControls() {
        System.out.println("  Controls:");
        System.out.println("    w - Move Up");
        System.out.println("    s - Move Down");
        System.out.println("    a - Move Left");
        System.out.println("    d - Move Right");
        System.out.println("    q - Quit and Save Board");
        System.out.println();
    }
}
