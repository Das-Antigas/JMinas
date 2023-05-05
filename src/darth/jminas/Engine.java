package darth.jminas;

import darth.jminas.tools.Sonido;

public class Engine {

    private JMinasMain main;
    private PanelSuperior panelTop;
    private PanelCentral panelCentral;
    private PanelInferior panelBottom;
    private Timer chronometer;

    private boolean playing = false;
    private boolean winner = false;
    private boolean loser = false;

    public Engine(JMinasMain main, PanelSuperior panelSuperior, PanelCentral panelCentral, PanelInferior panelInferior) {
        this.main = main;
        this.panelTop = panelSuperior;
        this.panelCentral = panelCentral;
        this.panelBottom = panelInferior;
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
        panelCentral.Perdio();
        playing = false;
        winner = false;
        loser = true;
        new Sonido(Variables.SonidoExplosion, true).start();
    }

    public void WinGame() {
        StopTimer();
        panelCentral.Gano();
        winner = true;
        loser = false;
        new Sonido(Variables.SonidoGanador, false).start();
    }

    public void StopTimer() {
        chronometer.setActive(false);
    }
}
