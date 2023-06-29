package darth.jminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StatisticsWindow extends JFrame {
    private Language language;
    private SaveFile saveFile;

    public StatisticsWindow() {
        
        language = Language.getInstance();
        saveFile = SaveFile.getInstance();
        
        setTitle(language.getString("statistics_title"));
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);

        // Criação dos componentes
        JPanel painelSuperior = new JPanel();
        JLabel labelTitulo = new JLabel(language.getString("statistics_title"));
        painelSuperior.add(labelTitulo);

        int gamesPlayed = saveFile.getGamesPlayed();
        int gamesWon = saveFile.getGamesWon();
        int gamesLost = gamesPlayed - gamesWon;
        
        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(3, 2));
        JLabel labelJogos = new JLabel(language.getString("games_played"));
        JLabel labelJogosValor = new JLabel("" + gamesPlayed);
        JLabel labelVitorias = new JLabel(language.getString("victories"));
        JLabel labelVitoriasValor = new JLabel("" + gamesWon);
        JLabel labelDerrotas = new JLabel(language.getString("lost_games"));
        JLabel labelDerrotasValor = new JLabel("" + gamesLost);
        painelCentral.add(labelJogos);
        painelCentral.add(labelJogosValor);
        painelCentral.add(labelVitorias);
        painelCentral.add(labelVitoriasValor);
        painelCentral.add(labelDerrotas);
        painelCentral.add(labelDerrotasValor);

        JPanel painelInferior = new JPanel();
        JButton botaoFechar = new JButton(language.getString("close"));
        botaoFechar.addActionListener((ActionEvent e) -> {
            dispose();
        });
        painelInferior.add(botaoFechar);

        // Adição dos componentes ao JFrame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }
}