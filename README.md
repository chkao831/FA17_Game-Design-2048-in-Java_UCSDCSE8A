File: README.md
Name: Chih-Hsuan Kao
Login: cs8bwagy
Email: c4kao@ucsd.edu
Date: Feb 5, 2018
Source of Help: N/A

/---Vim related Questions---/
(1) :20
(2) in command mode, press u
(3) /(the word that I want to search) 
    not including () while typing
(4) after setting up vimrc, type gg=G in command mode
(5) Use :tabe <filename> to open another file (not including <>)
    Use :tabn or :tabp for next and previous ones
(6) a. Use w to forward one word
    b. Use b to backward
/---Unix/Linux related Questions---/
(7) rm *.class
(8) a. rmdir<directoryname>
    b. rmdir -r<directoryname>
(9) clear
    Ctrl+L
(10) A swap file takes up a space on a hard disk as the virtual memory 
extension of a computer's real memory. 
     They exist because I probably do not close my file correctly. 
     To restore, press :recover
     To remove all swap files, press rm *.swap

/--Program Description---/
<Board.java> (CS terms allowed)
    This file contains the fundamental mechanisms for this game. 
    There are methods in this file to construct a board, save the board, 
    add tiles to the board, and most importantly, determine whether a 
    move is possible, move the tiles if possible, and then check if the
    game is over under certain conditions. 
    In general, the Board class is intended as the fundamental board where
    tiles will be placed and moved around. 

<GameManager.java> (CS terms allowed)
    This file is mainly used to connect movements instructed by the user
    as read the inputs and perform the movement and run the game. The play
    method is the main play loop for the 2048 game. The capabilities of it
    include but not limited to: print out the controls to operate the game, 
    print out the current state of the board, perform movement if the move
    is valid, terminate the game if user decides to quit or if the game is
    over. 

<General Program Summary> (No CS term is allowed)
    Board.java File is where the 2048 board is created, saved, and where
    I place and move around tiles in the board. Things in the file helps
    my 2048 game perform identically to the original game in all cases, such 
    as moving up, down, left and right, tell me it's game over when no 
    movement is possible, etc. 
    GameManager.java File is where I collect all things I just wrote in the
    Board.java File and connect them to make the game. When my player enters
    command, I'll determine whether it's valid or not, and perform movement 
    if valid, otherwise tell the player to reenter something else if the keyboard 
    command is invalid. If the player decides to quit or he loses the game,
    I'll terminate the game. 
