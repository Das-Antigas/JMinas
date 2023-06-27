package darth.jminas;

import darth.jminas.tools.MakeSound;

public class Engine {

    private JMinasMain main;
    private TopPanel panelTop;
    private CentralPanel panelCentral;
    private BottomPanel panelBottom;
    private Timer chronometer;

    private boolean playing = false;
    private boolean winner = false;
    private boolean loser = false;
    
    private boolean soundDisabled = true;

    public Engine(JMinasMain main, TopPanel panelSuperior, CentralPanel panelCentral, BottomPanel panelInferior) {
        this.main = main;
        this.panelTop = panelSuperior;
        this.panelCentral = panelCentral;
        this.panelBottom = panelInferior;
    }

    public void setSound(boolean sound) {
        this.soundDisabled = sound;
    }    
    
    public void StartGame() {
        chronometer = new Timer();
        chronometer.start();
        playing = true;
        winner = false;
        loser = false;
    }

    public void RestartGame() {
        panelCentral.restart();
        panelTop.restart();
        if (chronometer != null) {
            chronometer.setActive(false);
        }
        playing = false;
        winner = false;
        loser = false;
    }

    public void LostGame() {
        chronometer.setActive(false);
        panelCentral.lostGame();
        playing = false;
        winner = false;
        loser = true;
        new MakeSound(Variables.BOOM_SOUND, this.soundDisabled).start();
    }

    public void WinGame() {
        StopTimer();
        panelCentral.wonGame();
        winner = true;
        loser = false;
        new MakeSound(Variables.WINNER_SOUND, this.soundDisabled).start();
    }

    public void StopTimer() {
        chronometer.setActive(false);
    }
}
