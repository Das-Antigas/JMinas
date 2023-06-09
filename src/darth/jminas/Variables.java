package darth.jminas;

import java.awt.Color;

public class Variables {
    
    public static String JMINES_VERSION = "1.0";

    private static int level = 1;
    public static int height = 9;
    public static int width = 9;
    public static int numberOfMines = 9;
    
    public static final String BOOM_SOUND = "/darth/audio/boom.wav";
    public static final String WINNER_SOUND = "/darth/audio/win.wav";

    public static final String TIMER_ICON_PATH = "/darth/img/com.github.parnold-x.timer.png";
    public static final String MINES_ICON_PATH = "/darth/img/edit-bomb.png";
    public static final String FLAG_ICON_PATH = "/darth/img/flag-red.png";
    public static final String EXPLOSION_ICON_PATH = "/darth/img/edit-bomb.png";
    public static final String NORMAL_ICON_PATH = "/darth/img/face-cool.png";
    public static final String CLICK_ICON_PATH = "/darth/img/face-surprise.png";
    public static final String MARK_ICON_PATH = "/darth/img/face-uncertain.png";
    public static final String LOOSER_ICON_PATH = "/darth/img/face-crying.png";
    public static final String WINNER_ICON_PATH = "/darth/img/face-raspberry.png";
    public static final String LAUGHING_ICON_PATH = "/darth/img/face-smile-crying.png";

    public static final String NORMAL_TEXT = " :) ";
    public static final String CLICK_TEXT = " :O ";
    public static final String MARK_TEXT = " :| ";
    public static final String WINNER_TEXT = " XD ";
    public static final String LOOSER_TEXT = " :( ";
    public static final String LAUGHING_TEXT = " ... ";

    public static void setNivel(int val) {
        level = val;
        switch (val) {
            case 0:
                height = 9;
                width = 9;
                numberOfMines = 9;
                break;
            case 1:
                height = 9;
                width = 9;
                numberOfMines = 18;
                break;
            case 2:
                height = 16;
                width = 16;
                numberOfMines = 40;
                break;
            case 3:
                height = 16;
                width = 30;
                numberOfMines = 99;
                break;
        }
    }

    public static int getLevel() {
        return level;
    }

    public static Color getColorMineCount(int op) {
        switch (op) {
            case 1:
                return Color.blue;
            case 2:
                return new Color(0x006000);
            case 3:
                return Color.red;
            case 4:
                return Color.magenta;
            case 5:
                return Color.yellow;
            case 6:
                return Color.cyan;
            case 7:
                return Color.orange;
            case 8:
                return Color.black;
            default:
                return Color.black;
        }
    }
}
