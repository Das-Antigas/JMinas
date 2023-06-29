package darth.jminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class StatisticsWindow extends JFrame {

    public StatisticsWindow() {
        setTitle("Estatísticas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);

        // Criação dos componentes
        JPanel painelSuperior = new JPanel();
        JLabel labelTitulo = new JLabel("Estatísticas");
        painelSuperior.add(labelTitulo);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(3, 2));
        JLabel labelJogos = new JLabel("Jogos jogados:");
        JLabel labelJogosValor = new JLabel("10");
        JLabel labelVitorias = new JLabel("Vitórias:");
        JLabel labelVitoriasValor = new JLabel("1");
        JLabel labelDerrotas = new JLabel("Derrotas:");
        JLabel labelDerrotasValor = new JLabel("9");
        painelCentral.add(labelJogos);
        painelCentral.add(labelJogosValor);
        painelCentral.add(labelVitorias);
        painelCentral.add(labelVitoriasValor);
        painelCentral.add(labelDerrotas);
        painelCentral.add(labelDerrotasValor);

        JPanel painelInferior = new JPanel();
        JButton botaoFechar = new JButton("Fechar");
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