/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package darth.jminas;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author luciano
 */
public class AboutWindow extends JFrame {
    Language language;
    public AboutWindow() {
        language = Language.getInstance();
        setTitle("About JMinas");
        setSize(400, 220);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        this.setLocationByPlatform(true);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.append(language.getString("about_jminas") + "\n\n");
        textArea.append(language.getString("about_original_author") + "\n\n");
        textArea.append(language.getString("about_authors") + "\n");

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(20, 20, 360, 200);
        add(scrollPane);

        setLayout(null);
        setVisible(true);
    }

}
