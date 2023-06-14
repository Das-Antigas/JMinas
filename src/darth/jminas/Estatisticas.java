package darth.jminas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Estatisticas extends JFrame {

    public Estatisticas() {
        setTitle("Estatísticas");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Criação dos componentes
        JPanel painelSuperior = new JPanel();
        JLabel labelTitulo = new JLabel("Estatísticas");
        painelSuperior.add(labelTitulo);

        JPanel painelCentral = new JPanel();
        painelCentral.setLayout(new GridLayout(3, 2));
        JLabel labelJogos = new JLabel("Jogos jogados:");
        JLabel labelJogosValor = new JLabel("10");
        JLabel labelVitorias = new JLabel("Vitórias:");
        JLabel labelVitoriasValor = new JLabel("3");
        JLabel labelDerrotas = new JLabel("Derrotas:");
        JLabel labelDerrotasValor = new JLabel("7");
        painelCentral.add(labelJogos);
        painelCentral.add(labelJogosValor);
        painelCentral.add(labelVitorias);
        painelCentral.add(labelVitoriasValor);
        painelCentral.add(labelDerrotas);
        painelCentral.add(labelDerrotasValor);

        JPanel painelInferior = new JPanel();
        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new JMinasMain();
            }
        });
        painelInferior.add(botaoFechar);

        // Adição dos componentes ao JFrame
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }


    public static void main(String[] args) throws InterruptedException {
        new Estatisticas();
    }


    public void exibirEstatisticas() {
        new Estatisticas();
    }
}