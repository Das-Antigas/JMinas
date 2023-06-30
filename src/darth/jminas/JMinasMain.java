package darth.jminas;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;

import darth.jminas.gui.AboutDialog;
import darth.jminas.tools.ErrorReporter;
import darth.jminas.tools.MakeSound;
import javax.swing.SwingUtilities;

public class JMinasMain extends JFrame implements ActionListener {

    private static final long serialVersionUID = 5263848303195260404L;

    private JMenuBar menuBar;

    private JMenu optionsMenu;
    private JMenu helpMenu;
    private JMenu difficultyMenuItem;

    private JMenuItem newGameMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem statiticsMenuItem;
    private JMenuItem aboutMenuItem;
    private JMenuItem difficulty0MenuItem;
    private JMenuItem difficulty1MenuItem;
    private JMenuItem difficulty2MenuItem;
    private JMenuItem difficulty3MenuItem;
    private JMenuItem changeLanguageMenuItem;

    private final ErrorReporter errorReporter;

    private TopPanel panelSuperior;
    private CentralPanel panelCentral;
    private BottomPanel panelInferior;

    private static Timer cronometro;

    private boolean jugando = false;
    public boolean Ganador = false;

    private Language language;
    private SaveFile saveFile;
    

    public JMinasMain() {
        this.language = Language.getInstance();
        this.saveFile = SaveFile.getInstance();

        errorReporter = new ErrorReporter();
        cleanErrors();

        setTitle(this.language.getString("game_name"));
        setSize(300, 396);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        createMenu();
        initComponents();
        addComponents();
        addKeyListener(panelCentral);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        validateLoadingErrors();
    }

    private void initComponents() {
        panelSuperior = new TopPanel(this);
        panelCentral = new CentralPanel(this);
        panelInferior = new BottomPanel();
    }

    private void addComponents() {
        add(panelSuperior, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void createMenu() {
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        optionsMenu = new JMenu(this.language.getString("options"));
        helpMenu = new JMenu(this.language.getString("help"));

        menuBar.add(optionsMenu);
        menuBar.add(helpMenu);

        difficultyMenuItem = new JMenu(this.language.getString("difficulty"));

        {
            difficulty0MenuItem = new JMenuItem(this.language.getString("difficulty_0"));
            difficulty1MenuItem = new JMenuItem(this.language.getString("difficulty_1"));
            difficulty2MenuItem = new JMenuItem(this.language.getString("difficulty_2"));
            difficulty3MenuItem = new JMenuItem(this.language.getString("difficulty_3"));

            difficulty0MenuItem.addActionListener(this);
            difficulty1MenuItem.addActionListener(this);
            difficulty2MenuItem.addActionListener(this);
            difficulty3MenuItem.addActionListener(this);

            difficultyMenuItem.add(difficulty0MenuItem);
            difficultyMenuItem.add(difficulty1MenuItem);
            difficultyMenuItem.add(difficulty2MenuItem);
            difficultyMenuItem.add(difficulty3MenuItem);
        }

        newGameMenuItem = new JMenuItem(this.language.getString("new_game"));
        changeLanguageMenuItem = new JMenuItem(this.language.getString("change_language"));
        statiticsMenuItem = new JMenuItem(this.language.getString("statitics"));
        exitMenuItem = new JMenuItem(this.language.getString("exit"));

        newGameMenuItem.addActionListener(this);
        changeLanguageMenuItem.addActionListener(this);
        statiticsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        optionsMenu.add(newGameMenuItem);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(changeLanguageMenuItem);
        optionsMenu.add(difficultyMenuItem);
        optionsMenu.add(statiticsMenuItem);
        optionsMenu.add(new JSeparator());
        optionsMenu.add(exitMenuItem);

        /*--------------------------------------------*/
        aboutMenuItem = new JMenuItem(this.language.getString("about"));
        aboutMenuItem.addActionListener(this);

        helpMenu.add(aboutMenuItem);
    }

    public void StartGame() {
        cronometro = new Timer();
        cronometro.start();
        jugando = true;
        Ganador = false;
        saveFile.incrementGamesPlayed();
    }

    public void RestartGame() {
        panelCentral.restart();
        panelSuperior.restart();
        if (cronometro != null) {
            cronometro.setActive(false);
        }
        jugando = false;
        Ganador = false;
        saveFile.incrementGamesPlayed();
    }

    public void LostGame() {
        cronometro.setActive(false);
        panelCentral.lostGame();
        jugando = false;
        Ganador = false;
        new MakeSound(Variables.BOOM_SOUND, Ganador).start();
        saveFile.incrementGamesLost();
        JOptionPane.showMessageDialog(null, language.getString("lost_message"));
    }

    public void WinGame() {
        StopChron();
        panelCentral.wonGame();
        Ganador = true;
        new MakeSound(Variables.WINNER_SOUND, Ganador).start();
        saveFile.incrementGamesWon();
        JOptionPane.showMessageDialog(null, language.getString("won_message"));
    }

    public static void StopChron() {
        cronometro.setActive(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameMenuItem) {
            RestartGame();
        } else if (e.getSource() == changeLanguageMenuItem) {
            SwingUtilities.invokeLater(() -> {
                ChangeLanguage window = new ChangeLanguage();
                window.setVisible(true);
                dispose();

            });
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        } else if (e.getSource() == difficulty0MenuItem) {
            Variables.setNivel(0);
            setSize(300, 395);
            setLocationRelativeTo(null);
            RestartGame();
        } else if (e.getSource() == difficulty1MenuItem) {
            Variables.setNivel(1);
            setSize(300, 395);
            setLocationRelativeTo(null);
            RestartGame();
        } else if (e.getSource() == difficulty2MenuItem) {
            Variables.setNivel(2);
            setSize(468, 500);
            setLocationRelativeTo(null);
            RestartGame();
        } else if (e.getSource() == difficulty3MenuItem) {
            Variables.setNivel(3);
            setSize(842, 548);
            setLocationRelativeTo(null);
            RestartGame();
        } else if (e.getSource() == aboutMenuItem) {
            new AboutWindow();
        } else if (e.getSource() == statiticsMenuItem) {
            new StatisticsWindow();
        }

    }

    public boolean isPlaying() {
        return jugando;
    }

    private void cleanErrors() {
        errorReporter.LimpiaErrores();
    }

    private void validateLoadingErrors() {
        if (!errorReporter.ExistenErrores()) {
            return;
        }
        JOptionPane.showMessageDialog(null,
                "No se pudieron cargar algunos recursos,\n"
                + "se creo el archivo JMinas_errorlog.txt con\n"
                + "informacion al respecto",
                "ERROR",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) throws InterruptedException {
        FlatMacLightLaf.setup();
        new JMinasMain();
    }
}
