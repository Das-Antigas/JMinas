package darth.jminas;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;


/**
 *
 * @author luciano
 */
public class ChangeLanguage extends JFrame {

    Language language;

    public ChangeLanguage() {
        this.language = Language.getInstance();
        setTitle(this.language.getString("change_language"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationByPlatform(true);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        getContentPane().add(mainPanel);

        // Vertical Panel
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        mainPanel.add(radioPanel);

        ButtonGroup languageButtonGroup = new ButtonGroup();
        JRadioButton radioPT = new JRadioButton(language.getString("language_pt"));
        JRadioButton radioEN = new JRadioButton(language.getString("language_en"));
        JRadioButton radioES = new JRadioButton(language.getString("language_es"));
        languageButtonGroup.add(radioPT);
        languageButtonGroup.add(radioEN);
        languageButtonGroup.add(radioES);
        radioPanel.add(radioPT);
        radioPanel.add(radioEN);
        radioPanel.add(radioES);

        // Panel for buttons with horizontal FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(buttonPanel);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        
        switch (this.language.getLocale().getLanguage()) {
            case "pt":
                radioPT.setSelected(true);
                break;
            case "en":
                radioEN.setSelected(true);
                break;
            default:
                radioES.setSelected(true);
                break;
        }
        
        // Buttons listeners
        okButton.addActionListener((ActionEvent e) -> {
            Locale locale1;
            if (radioPT.isSelected()) {
                locale1 = new Locale("pt", "BR");
            } else if (radioEN.isSelected()) {
                locale1 = new Locale("en", "US");
            } else if (radioES.isSelected()) {
                locale1 = new Locale("es", "ES");
            } else {
                locale1 = new Locale("en", "US");
            }
            language.setLocale(locale1);
            new JMinasMain();
            dispose();
        });
        cancelButton.addActionListener((ActionEvent e) -> {
            dispose(); // Close the current frame
        });

        setSize(300, 200);
    }

}
