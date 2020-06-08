//---File Header---//
File: README.md
Purpose: Program Description and Short Response
Name: Chih-Hsuan Kao
Login: cs8bwagy
Email: c4kao@ucsd.edu
Date: Feb 25, 2018
Sources of Help: N/A

//PROGRAM DESCRIPTION//

[How I tested my codes]
This assignment is mostly about after successful movement in the back (after
the board is updated), how I make the Gui looks right in the front (to display
rectangles and texts). The way I tested out my code is mostly about using
System.out.println in for loops, to see if the things changed in my board
also change in my tiles (those tiles capture the properties that I want to 
display to the user). After they completely match (after successfully updated),
I would be able to connect my front objects with what's happening in the back.

[About the entire program] no cs term is allowed
The program is the final stage of the 2048 project. What I did in PSA3 was to 
implement the backend of the game; now, I'm implementing the frontend
by adding some visual effects using GUI. When my mechanism in the back performs,
I tried to show the effect by adding things onto the interface by creating 
shape objects and add it onto the scene that users see. 

//SHORT RESPONSE//
Unix/Linux Questions:
(1) Suppose you are currently inside a directory and in there you want to
make a new directory called fooDir. And inside fooDir, you want
another directory called barDir. Using only a single mkdir command,
how can you create a directory called fooDir with a directory
called barDir inside it?

    To create parent directories, I can use a single command as
    mkdir -p fooDir/barDir

(2) Give an example of how you would use a wildcard character
(you can use it with whichever command you want like rm, cat, ls, cp,etc).
Describe what happens after you use it. 
    
    When I'm in my psa6 folder but I'm not sure what files I exactly have,
    I would use 
    ls
    What happens is all files in the directory will show up,
    which enables to see clearly and what file I can work on next. 

(3) How can you run gvim through the command line to open all Java source
code files in the current directory, each file in its own tab?
    
    gvim -p *.java

Java Questions:
(4) What does the keyword static mean in regards to methods? 
Provide an example use of a static method.
    
    The static keyword in a  method denotes that the method can be accessed
    without requiring an instantiation of the class to which it belongs.
    Basically, it means that I can call a method with its class name
    without creating the object to which it belongs!
    
    In PSA1, for example, the method letterOpration is a static method, 
    whose header is
    
        private static char letterOperation(char a, int rotation){

    When I need to call this method, I'll simply put Caesar.letterOperation(...)
    since it's under Caesar class. I don't need to create an object to call it.

(5) A high school student is trying to write a Java program that will
draw different shapes and in different possible colors.
To do this, she has written just one Java class called ShapeDrawer,
which contains all the necessary methods like drawRedCircle(),
drawBlueCircle(), drawYellowSquare(), drawGreenSquare(), and so on.
Using object-oriented terminology, describe how you can help the
student improve her design.

    As in PSA5, I would suggest the student to firstly create classes for
    each shape, and put a draw method inside it. This method might be ideal
    to take in canvas, color and a boolean parameter fill to determine 
    whether or not to fill the shape. After the method has been created 
    for each class, the student will be able to call the draw method
    in another class, in which whatever shape he/she just created can be 
    painted out at once by probably using a for each loop. 
