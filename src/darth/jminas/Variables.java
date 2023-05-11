package darth.jminas;

import java.awt.Color;

public class Variables {

    private static int level = 1;
    public static int height = 9;
    public static int width = 9;
    public static int numberOfMines = 18;

    public static final String TIMER_ICON_PATH = "/darth/img/cronometro.png";
    public static final String MINES_ICON_PATH = "/darth/img/mina.png";
    public static final String FLAG_ICON_PATH = "/darth/img/bandera.png";
    public static final String EXPLOSION_ICON_PATH = "/darth/img/boom.png";
    public static final String NORMAL_ICON_PATH = "/darth/img/cool_32x32.png";
    public static final String CLICK_ICON_PATH = "/darth/img/surprised_32x32.png";
    public static final String MARK_ICON_PATH = "/darth/img/question_32x32.png";
    public static final String LOOSER_ICON_PATH = "/darth/img/crying_32x32.png";
    public static final String WINNER_ICON_PATH = "/darth/img/party_32x32.png";
    public static final String LAUGHING_ICON_PATH = "/darth/img/laughtingoutloud_32x32.png";
    public static final String BOOM_SOUND = "/darth/audio/boom.wav";
    public static final String WINNER_SOUND = "/darth/audio/win.wav";


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

    public static Color getColorCantidad(int op) {
        switch (op) {
            case 1:
                return Color.blue;
            case 2:
                return new Color(00, 99, 00);
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
