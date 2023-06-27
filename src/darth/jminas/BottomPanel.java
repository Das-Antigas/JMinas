package darth.jminas;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BottomPanel extends JPanel {

    private static final long serialVersionUID = 2473191468363778297L;

    public BottomPanel() {
        setBorder(BorderFactory.createRaisedBevelBorder());
        setLayout(new BorderLayout());
        add(new JLabel(Variables.JMINES_VERSION), BorderLayout.EAST);
    }
}
