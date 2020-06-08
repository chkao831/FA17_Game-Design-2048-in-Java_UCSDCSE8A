import java.util.*;
import java.io.*;

/**---File Header---
 * File: Board.java
 * Purpose: Fundamental board where tiles will be placed and moved around
 * Name: Chih-Hsuan Kao
 * Login: cs8bwagy
 * Email: c4kao@ucsd.edu
 * Date: Feb 4, 2018
 * Sources of Help: Tutorhours
 */

/**
 * Sample Board
 * <p/>
 * 0   1   2   3
 * 0   -   -   -   -
 * 1   -   -   -   -
 * 2   -   -   -   -
 * 3   -   -   -   -
 * <p/>
 * The sample board shows the index values for the columns and rows
 * Remember that you access a 2D array by first specifying the row
 * and then the column: grid[row][column]
 */

/**---Class Header---
 * Board Class contains the following instance variables: private final Random
 * random (a reference to the Random object), private int[][] grid (a 2D int
 * array) and private int score (the current int score). The capability of this
 * class is to construct a board and place/move the tiles around. 
 */
public class Board {
    public final int NUM_START_TILES = 2; 
    public final int TWO_PROBABILITY = 90;
    public final int GRID_SIZE;


    private final Random random; // a reference to the Random object, passed in 
    // as a parameter in Boards constructors
    private int[][] grid;  // a 2D int array, its size being boardSize*boardSize
    private int score;     // the current score, incremented as tiles merge 

    /**
     * This constructor constructs a fresh board with random tiles
     * @param Random random: a random object
     *        int boardSize: the grid size of the board
     * @return Board
     */ 
    public Board(Random random, int boardSize) {
        this.random = random; // Initialize random by the given parameter
        GRID_SIZE = boardSize; //initailze GRIP_SIZE by the given parameter

        //initialize the 2d grid
        grid = new int[GRID_SIZE][GRID_SIZE];

        //add 2 random tiles by calling addRandomTile()
        for (int i=0; i<NUM_START_TILES;i++){
            this.addRandomTile();
        }
    }

    /**
     * This constructor constructs a board based on an input file
     * assume board is valid. 
     * @param Random random: a random object
     *        String inputBoard: the string specifying the save board
     * @return Board
     */ 
    public Board(Random random, String inputBoard) throws IOException {
        this.random = random; //Initialize random by the given parameter

        //Use file and scanner to read the input from the file
        File sourceFile = new File(inputBoard);
        Scanner input = new Scanner(sourceFile);

        //read the first int from the file to set GRIP_SIZE
        GRID_SIZE = input.nextInt(); 

        //initialize the grid
        grid = new int[GRID_SIZE][GRID_SIZE];

        //read the second int from the file to set score
        this.score = input.nextInt();

        //add the value of the board into the grid[][]
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                this.grid[i][j]=input.nextInt();
            }
        }
    }

    /**Method: saveBoard
     * This method saves the current board to a file
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Jan 23, 2018
     * @param String outputBoard: the name of the file to save the board to
     * @return none
     */
    public void saveBoard(String outputBoard) throws IOException {
        FileOutputStream fw = new FileOutputStream(outputBoard);
        PrintWriter writer = new PrintWriter(fw);

        writer.println(this.GRID_SIZE);
        writer.println(this.getScore());

        for(int i=0; i < this.GRID_SIZE; i++){
            for(int j=0; j < this.GRID_SIZE; j++){
                int element = this.grid[i][j];
                writer.print(element);
                writer.print(" ");
            }
            writer.print("\n");
        }
        writer.flush();
        writer.close();
    }

    /**Method: addRandomTile
     * This method adds a random tile (of value 2 or 4) to a random empty 
     * space on the board.
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Jan 23, 2018
     * @param none
     * @return none
     */
    public void addRandomTile() {
        //this variable counts the available tiles
        int count=0;

        //count the available tiles
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){
                //increment the count if meet 0 in the grid
                if(this.grid[i][j]==0){
                    count=count+1;
                }
            }
        }

        //If count is 0 then there is no room, return
        if(count==0){
            return;
        }

        //generate a random number between 0 to count-1
        int location = this.random.nextInt(count);

        //generate random number between 0 and 99
        int value = this.random.nextInt(100);
        count=0;

        //walk the board row first, column second keep count of the empty
        //space
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE; j++){

                //count the empty space
                if(grid[i][j]==0){
                    count++;
                }

                //add new tile while hit the i th random number
                if((count-1)==location){

                    //the probability of adding 2 is 90 percent
                    if(value < TWO_PROBABILITY){
                        this.grid[i][j]=2;
                    }
                    //the probability of adding 4 is 10 percent
                    else{
                        this.grid[i][j]=4;
                    }
                    return;
                }
            }
        }
    }

    /**Method: canMove
     * This method determines whether the board can move in a certain direction
     * return true if such a move is possible. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param Direction direction: a certain direction to move
     * @return true: if move is possible; false: if move is impossible
     */
    public boolean canMove(Direction direction){
        int i = direction.getX();
        int j = direction.getY();
        if(i == -1 && j == 0){
            return this.canMoveLeft();
        }
        if(i == 1 && j == 0){
            return this.canMoveRight();
        }
        if(i == 0 && j == -1){
            return this.canMoveUp();
        }
        if(i ==0 && j == 1){
            return this.canMoveDown();
        }
        return false;
    }

    /**Method: move
     * This method moves the board in a certain direction
     * return true if such a move is successful
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param Direction direction: a certain direction to move
     * @return true: if move is possible; false: if move is impossible
     */
    public boolean move(Direction direction) {
        //return false if no move in this direction is possible
        if(this.canMove(direction) == false){
            return false;
        }
        //if a move in this direction is possible, check which direction
        int i = direction.getX();
        int j = direction.getY();
        if(i == -1 && j == 0){
            MoveLeft();
            return true;
        }
        if(i == 1 && j == 0){
            MoveRight();
            return true;
        }
        if(i == 0 && j == -1){
            MoveUp();
            return true;
        }
        if(i == 0 && j == 1){
            MoveDown();
            return true;
        }
        return false;
    }

    /**Method: shiftLeft
     * This method is called in the moveLeft() method, when I'm shifting certain row
     * left, I need to get rid of zero
     * from the start index to end index in a certain row
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param int start: the start index of the zero that I want to remove
     *        int end: the end index of the zero 
     *        int row: the number of the row that this operation is performed
     * @return none
     */
    private void shiftLeft(int start, int end, int row){
        for (int i=start; i<end;i++){
            this.grid[row][i]=this.grid[row][i+1];
        }
        this.grid[row][end]=0;
    }

    /**Method: shiftRight
     * This method is called in the moveRight()  method, when I'm shifting certain row
     * right, I need to get rid of zero
     * from the start index to end index in a certain row
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param int start: the start index of the zero that I want to remove
     *        int end: the end index of the zero 
     *        int row: the number of the row that this operation is performed
     * @return none
     */
    private void shiftRight(int start, int end, int row){
        for (int i=start; i>end;i--){
            this.grid[row][i]=this.grid[row][i-1];
        }
        this.grid[row][end]=0;
    }

    /**Method: shiftUp
     * This method is called in the moveUp() method, when I'm shifting certain
     * column up, I need to get rid of zero
     * from the start index to end index in a certain column
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param int start: the start index of the zero that I want to remove
     *        int end: the end index of the zero 
     *        int row: the number of the row that this operation is performed
     * @return none
     */
    private void shiftUp(int start, int end, int column){
        for (int i=start; i<end;i++){
            this.grid[i][column]=this.grid[i+1][column];
        }
        this.grid[end][column]=0;
    }

    /**Method: shiftDown
     * This method is called in the moveDown()  method, when I'm shifting certain
     * column down, I need to get rid of zero
     * from the start index to end index in a certain column
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param int start: the start index of the zero that I want to remove
     *        int end: the end index of the zero 
     *        int row: the number of the row that this operation is performed
     * @return none
     */
    private void shiftDown(int start, int end, int column){
        for (int i=start; i>end;i--){
            this.grid[i][column]=this.grid[i-1][column];
        }
        this.grid[end][column]=0;
    } 

    /**Method: MoveUp
     * This method is a helper method for Move() method. This is called when a
     * direction up is passed in the Move() method as the parameter. This helper
     * method would then perform up movement. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return none
     */
    private void MoveUp(){
        for(int j=0; j<GRID_SIZE; j++){
            for(int i=0; i<GRID_SIZE-1; i++){

                int countZero=0;
                //get rid of zeros in the front and shift all up 
                //until the first element is not zero
                while(this.grid[i][j]==0 && (countZero <GRID_SIZE)){
                    shiftUp(i,GRID_SIZE-1,j);
                    countZero++;
                }

                //if all elements in this column are zeros
                //skip the column and jump to the next outer for loop
                if (countZero == GRID_SIZE){
                    i=GRID_SIZE-1;                    
                }
                else{

                    //add up the adjacent tile if they are same
                    if(this.grid[i+1][j]==this.grid[i][j]){
                        this.score = this.getScore() + this.grid[i][j]*2;
                        this.grid[i][j]= 2*this.grid[i][j];
                        shiftUp(i+1, GRID_SIZE-1, j);
                    }

                    //add up the tile if there are zero between them
                    if(this.grid[i+1][j]==0){
                        int count = 0;

                        //count the zero between the two nonzero tiles
                        while(this.grid[count+1+i][j] == 0){
                            count = count + 1;
                            if(count==(GRID_SIZE-1-i)){
                                break;
                            }  
                        }

                        //if the latter elements are all zero until the end
                        //no performance, jump to outer for loop
                        if (count == (GRID_SIZE-1-i)){
                            i=GRID_SIZE-1;
                        }
                        //add up the two nonzero tiles if they're identical
                        else if(this.grid[i+count+1][j] == this.grid[i][j]){
                            this.score = this.getScore() + this.grid[i][j]*2;
                            this.grid[i][j]= 2*this.grid[i][j];
                            this.grid[i+count+1][j] = 0;
            
                            //shift up all of the remain tiles
                            //get rid of zeros
                            for(int k=0; k<= count; k++){
                                shiftUp(i+1, GRID_SIZE-1, j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**Method: MoveDown
     * This method is a helper method for Move() method. This is called when a
     * direction down is passed in the Move() method as the parameter. This helper
     * method would then perform down movement. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return none
     */
    private void MoveDown(){
        for(int j=0; j<GRID_SIZE; j++){
            for(int i=GRID_SIZE-1; i>0; i--){
                int countZero=0;
                //get rid of zeros in the bottom and shift all down 
                //until the last element is not zero
                while(this.grid[i][j]==0 && (countZero <GRID_SIZE)){
                    shiftDown(i,0,j);
                    countZero++;
                }

                //if all elements in this column are zeros
                //skip the column and jump to the next outer for loop
                if (countZero == GRID_SIZE){
                    i=0;                    
                }
                else{

                    //add up the adjacent tile if they are same
                    if(this.grid[i-1][j]==this.grid[i][j]){
                         
                        this.score = this.getScore() + this.grid[i][j]*2;
                        this.grid[i][j]= 2*this.grid[i][j];
                        shiftDown(i-1, 0, j);
                    }

                    //add up the tile if there are zero between them
                    if(this.grid[i-1][j]==0){
                        int count = 0;

                        //count the zero between the two nonzero tiles
                        while(this.grid[i-1-count][j] == 0){
                            count = count + 1;
                            if(count==(i)){
                                break;
                            }  
                        }

                        //if the latter elements are all zero until the end
                        //no performance, jump to outer for loop
                        if (count == (i)){
                            i=0;
                        }
                        //add up the two nonzero tiles if they're identical
                        else if(this.grid[i-count-1][j] == this.grid[i][j]){
                       
                            this.score = this.getScore() + this.grid[i][j]*2;
                            this.grid[i][j]= 2*this.grid[i][j];
                            this.grid[i-count-1][j] = 0;

                            //shift down all of the remain tiles
                            //get rid of zeros
                            for(int k=0; k<= count; k++){
                                shiftDown(i-1, 0, j);
                            }
                        }
                    }
                }
            }
        }
    }

    /**Method: MoveLeft
     * This method is a helper method for Move() method. This is called when a
     * direction left is passed in the Move() method as the parameter. This helper
     * method would then perform left movement. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return none
     */
    private void MoveLeft(){
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=0; j<GRID_SIZE-1; j++){

                int countZero=0;
                //get rid of zeros on the left and shift all left 
                //until the first element is not zero
                while(this.grid[i][j]==0 && (countZero <GRID_SIZE)){
                    shiftLeft(j,GRID_SIZE-1,i);
                    countZero++;
                }

                //if all elements in this row are zeros
                //skip the row and jump to the next outer for loop
                if (countZero == GRID_SIZE){
                    j=GRID_SIZE-1;                    
                }
                else{

                    //add up the adjacent tile if they are same
                    if(this.grid[i][j+1]==this.grid[i][j]){
                                                
                        this.score = this.getScore() + this.grid[i][j]*2;
                        this.grid[i][j]= 2*this.grid[i][j];
                        shiftLeft(j+1, GRID_SIZE-1, i);
                    }

                    //add up the tile if there are zero between them
                    if(this.grid[i][j+1]==0){
                        int count = 0;

                        //count the zero between the two nonzero tiles
                        while(this.grid[i][count+1+j] == 0){
                            count = count + 1;
                            if(count==(GRID_SIZE-1-j)){
                                break;
                            }  
                        }

                        //if the latter elements are all zero until the end
                        //no performance, jump to outer for loop
                        if (count == (GRID_SIZE-1-j)){
                            j=GRID_SIZE-1;
                        }
                        //add up the two nonzero tiles if they're identical
                        else if(this.grid[i][count+1+j] == this.grid[i][j]){
                        
                            this.score = this.getScore() + this.grid[i][j]*2;
                            this.grid[i][j]= 2*this.grid[i][j];
                            this.grid[i][count+1+j] = 0;

                            //shift left all of the remain tiles
                            //get rid of zeros
                            for(int k=0; k<= count; k++){
                                shiftLeft(j+1, GRID_SIZE-1, i);
                            }
                        }
                    }
                }
            }
        }
    }

    /**Method: MoveRight
     * This method is a helper method for Move() method. This is called when a
     * direction right is passed in the Move() method as the parameter. This helper
     * method would then perform right movement. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return none
     */
    private void MoveRight(){
        for(int i=0; i<GRID_SIZE; i++){
            for(int j=GRID_SIZE-1; j>0; j--){

                int countZero=0;
                //get rid of zeros on the right and shift all right 
                //until the first element is not zero
                while(this.grid[i][j]==0 && (countZero <GRID_SIZE)){
                    shiftRight(j,0,i);
                    countZero++;
                }

                //if all elements in this row are zeros
                //skip the row and jump to the next outer for loop
                if (countZero == GRID_SIZE){
                    j=0;                    
                }
                else{

                    //add up the adjacent tile if they are same
                    if(this.grid[i][j-1]==this.grid[i][j]){
                        
                        this.score = this.getScore() + this.grid[i][j]*2;
                        this.grid[i][j]= 2*this.grid[i][j];
                        shiftRight(j-1, 0, i);
                    }

                    //add up the tile if there are zero between them
                    if(this.grid[i][j-1]==0){
                        int count = 0;

                        //count the zero between the two nonzero tiles
                        while(this.grid[i][j-1-count] == 0){
                            count = count + 1;
                            if(count==(j)){
                                break;
                            }  
                        }

                        //if the latter elements are all zero until the end
                        //no performance, jump to outer for loop
                        if (count == (j)){
                            j=0;
                        }
                        //add up the two nonzero tiles if they're identical
                        else if(this.grid[i][j-count-1] == this.grid[i][j]){
                        
                            this.score = this.getScore() + this.grid[i][j]*2;
                            this.grid[i][j]= 2*this.grid[i][j];
                            this.grid[i][j-count-1] = 0;

                            //shift right all of the remain tiles
                            //get rid of zeros
                            for(int k=0; k<= count; k++){
                                shiftRight(j-1, 0, i);
                            }
                        }
                    }
                }
            }
        }
    }

    /**Method: canMoveLeft
     * This method is a helper method for canMove() method. This is called when a
     * direction left is passed in the canMove() method as the parameter. This helper
     * method would then return true if a left movement can be performed. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return boolean true: if a left moveemnt can be performed
     *                 false: if a left movement cannot be performed
     */
    private boolean canMoveLeft(){
        //loop through every element except for the last column
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE-1; j++){
                //if an element is empty
                if(this.grid[i][j] == 0){
                    //loop through elements on its right and see if it's empty
                    for(int k = j+1; k < GRID_SIZE; k++){
                        //if not empty, can move left
                        if(this.grid[i][k]!= 0){
                            return true;
                        }
                    }
                }
                //if two adjacent elements are the same, can move left
                if(this.grid[i][j] == this.grid[i][j+1] && this.grid[i][j] > 0){
                    return true;
                }
            }
        }
        //return false if no such movement is available
        return false; 
    }

    /**Method: canMoveRight
     * This method is a helper method for canMove() method. This is called when a
     * direction right is passed in the canMove() method as the parameter. This helper
     * method would then return true if a right movement can be performed. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return boolean true: if a right moveemnt can be performed
     *                 false: if a right movement cannot be performed
     */
    private boolean canMoveRight(){
        //loop through every element except for the last column
        for (int i = 0; i < GRID_SIZE; i++){
            for (int j = 1; j < GRID_SIZE; j++){
                //if an element is empty
                if(this.grid[i][j] == 0){
                    //loop through elements on its left and see if it's empty
                    for(int k = j-1; k >= 0; k--){
                        //if not empty, can move right
                        if(this.grid[i][k]!= 0){
                            return true;
                        }
                    }
                }
                //if two adjacent elements are the same, can move right
                if(this.grid[i][j] == this.grid[i][j-1] && this.grid[i][j] > 0){
                    return true;
                }
            }
        }
        //return false if no such movement is available
        return false; 
    }

    /**Method: canMoveUp
     * This method is a helper method for canMove() method. This is called when a
     * direction up is passed in the canMove() method as the parameter. This helper
     * method would then return true if an up movement can be performed. 
     * Name: Chih-Hsuan Kao, cs8bwagy    
     * Date: Feb 4, 2018
     * @param none
     * @return boolean true: if an up moveemnt can be performed
     *                 false: if an up movement cannot be performed
     */
    private boolean canMoveUp(){
        //loop through every element except for the last row
        for (int i = 0; i < GRID_SIZE-1; i++){
            for (int j = 0; j < GRID_SIZE; j++){
                //if an element is empty
                if(this.grid[i][j] == 0){
                    //loop through elements below it and see if it's empty
                    for(int k = i+1; k < GRID_SIZE; k++){
                        //if not empty, can move up
                        if(this.grid[k][j]!= 0){
                            return true;
                        }
                    }
                }
                //if two up-and-down elements are the same, can move up
                if(this.grid[i][j] == this.grid[i+1][j] && this.grid[i][j] > 0){
                    return true;
                }
            }
        }
        //return false if no such movement is available
        return false; 
    }

    /**Method: canMoveDown
     * This method is a helper method for canMove() method. This is called when a
     * direction down is passed in the canMove() method as the parameter. This helper
     * method would then return true if a down movement can be performed. 
     * Name: Chih-Hsuan Kao, cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return boolean true: if a down moveemnt can be performed
     *                 false: if a down movement cannot be performed
     */
    private boolean canMoveDown(){
        //loop through every element except for the first row
        for (int i = 1; i < GRID_SIZE; i++){
            for (int j = 0; j < GRID_SIZE; j++){

                //if an element is empty
                if(this.grid[i][j] == 0){

                    //loop through elements above it and see if it's empty
                    for(int k = i-1; k >=0; k--){

                        //if not empty, can move down
                        if(this.grid[k][j]!= 0){
                            return true;
                        }
                    }
                }
                //if two up-and-down elements are the same, can move down
                if(this.grid[i][j] == this.grid[i-1][j] && this.grid[i][j] > 0){
                    return true;
                }
            }
        }
        //return false if no such movement is available
        return false; 
    }

    /**Method: isGameOver
     * This method checks to see if we have a game over
     * Name: Chih-Hsuan Kao
     * Login: cs8bwagy
     * Date: Feb 4, 2018
     * @param none
     * @return boolean true: if game over
     *                 false: if game still proceeds
     */
    public boolean isGameOver() {
        if(this.canMoveLeft()){
            return false;
        }
        if(this.canMoveRight()){
            return false;
        }
        if(this.canMoveUp()){
            return false;
        }
        if(this.canMoveDown()){
            return false;
        }
        return true; 
    }

    /**Method: getGrid
     * This method returns the reference to the 2048 Grid
     * @param none
     * @return int[][] grid
     */
    public int[][] getGrid() {
        return grid;
    }

    /**Method: getScore
     * This method returns the score of the board
     * @param none
     * @return int score: the score
     */
    public int getScore() {
        return score;
    }

    @Override
        public String toString() {
            StringBuilder outputString = new StringBuilder();
            outputString.append(String.format("Score: %d\n", score));
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int column = 0; column < GRID_SIZE; column++)
                    outputString.append(grid[row][column] == 0 ? "    -" :
                            String.format("%5d", grid[row][column]));

                outputString.append("\n");
            }
            return outputString.toString();
        }
}
