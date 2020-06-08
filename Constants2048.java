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

/**---File Header---/
 * File: Constants2048.java
 * @modified by Chih-Hsuan Kao, cs8bwagy
 * Login: cs8bwagy
 * Email: c4kao@ucsd.edu
 * Date: Feb 25, 2018
 * Sources of Help: N/A
 */

/**---Class Header---/
 * This class constains color that each tile value in 2048 could use
 */

public class Constants2048 {
    public static final int TILE_WIDTH = 106;

    public static final int TEXT_SIZE_LOW = 55;
    public static final int TEXT_SIZE_MID = 45; 
    public static final int TEXT_SIZE_HIGH = 35; 

    public static final int GAMEOVER_BIGTEXT = 100;
    // Fill colors for each of the Tile values
    //PowderBlue
    public static final Color COLOR_EMPTY = Color.rgb(176, 224, 230, 1);
    //LightSkyBlue
    public static final Color COLOR_2 = Color.rgb(0, 191, 255);
    //CornFlowerBlue
    public static final Color COLOR_4 = Color.rgb(100, 149, 237);
    //MediumSlateBlue
    public static final Color COLOR_8 = Color.rgb(123, 104, 238);
    //RoyalBlue
    public static final Color COLOR_16 = Color.rgb(65, 105, 225);
    //SteelBlue
    public static final Color COLOR_32 = Color.rgb(70, 130, 180);
    //BlueViolet
    public static final Color COLOR_64 = Color.rgb(138, 43, 226);
    //DarkMagenta
    public static final Color COLOR_128 = Color.rgb(139, 0, 139);
    //Megenta
    public static final Color COLOR_256 = Color.rgb(255, 0, 255);
    //Plum
    public static final Color COLOR_512 = Color.rgb(221, 160, 221);
    //BurlyWood
    public static final Color COLOR_1024 = Color.rgb(222, 184, 135);
    //CornSilk
    public static final Color COLOR_2048 = Color.rgb(255, 248, 220);
    //LightGrey
    public static final Color COLOR_OTHER = Color.rgb(211,211,211);
    //NavyBlue text Game Over
    public static final Color COLOR_GAME_OVER = Color.rgb(0, 0, 128, 1);
    //gameover overlay
    public static final Color COLOR_OVERLAY = Color.rgb(255,255,255,0.35);

    public static final Color COLOR_VALUE_LIGHT = Color.rgb(249, 246, 242); 
                        // For tiles >= 8

    public static final Color COLOR_VALUE_DARK = Color.rgb(119, 110, 101); 
                       // For tiles < 8
}
