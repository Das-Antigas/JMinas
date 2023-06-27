package darth.jminas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TopPanel extends JPanel {

    private static final long serialVersionUID = 2473191468363778297L;

    private JMinasMain minasMain;
    private static JLabel lblTime, lblMines, lblStart;
    private JPanel pTimer, pMines;

    public TopPanel(JMinasMain minasMain) {
        this.minasMain = minasMain;
        setBorder(BorderFactory.createRaisedBevelBorder());
        setLayout(new GridLayout(1, 5, 3, 3));

        initComponents();
        addComponents();
    }

    private void initComponents() {
        pTimer = new JPanel();
        pTimer.setBackground(Color.black);
        pTimer.setLayout(new BorderLayout());
        pMines = new JPanel();
        pMines.setBackground(Color.black);
        pMines.setLayout(new BorderLayout());

        lblTime = new JLabel("00:00", JLabel.CENTER);
        lblTime.setBackground(Color.black);
        lblTime.setForeground(Color.green);
        lblTime.setFont(new Font("Serif", Font.BOLD, 16));
        pTimer.add(lblTime, BorderLayout.CENTER);

        lblMines = new JLabel("00", JLabel.CENTER);
        lblMines.setBackground(Color.black);
        lblMines.setForeground(Color.green);
        lblMines.setFont(new Font("Serif", Font.BOLD, 16));
        pMines.add(lblMines, BorderLayout.CENTER);

        lblStart = new JLabel("", JLabel.CENTER);
        lblStart.setBorder(BorderFactory.createRaisedBevelBorder());
        lblStart.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lblStart.setBorder(BorderFactory.createRaisedBevelBorder());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                lblStart.setBorder(BorderFactory.createLoweredBevelBorder());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (lblStart.getIcon() != null) {
                    lblStart.setIcon(icon);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (lblStart.getIcon() != null) {
                    icon = lblStart.getIcon();
                    lblStart.setIcon(new ImageIcon(getClass().getResource(Variables.LAUGHING_ICON_PATH)));
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                minasMain.RestartGame();
            }
        });
    }

    private static Icon icon;

    private void addComponents() {
        try {
            URL urlTimerIcon = this.getClass().getResource(Variables.TIMER_ICON_PATH);
            ImageIcon timerIcon = new ImageIcon(urlTimerIcon);
            add(new JLabel(timerIcon, JLabel.RIGHT));
        } catch (NullPointerException e) {
            add(new JLabel("tiempo ", JLabel.RIGHT));
        }

        add(pTimer);
        add(lblStart);
        add(pMines);

        try {
            URL urlMinesIcon = this.getClass().getResource(Variables.MINES_ICON_PATH);
            ImageIcon minesIcon = new ImageIcon(urlMinesIcon);
            add(new JLabel(minesIcon, JLabel.LEFT));
        } catch (NullPointerException e) {
            add(new JLabel(" minas", JLabel.LEFT));
        }
    }

    public void restart() {
        lblTime.setForeground(Color.green);
        lblTime.setText("00:00");
        lblMines.setForeground(Color.green);
    }

    public static void UpdateTime(int min, int seg) {
        if (min == 99 && seg == 59) {
            JMinasMain.StopChron();
            lblTime.setForeground(Color.red);
            lblTime.setText("--:--");
            return;
        }

        String strMin, strSeg;
        if (seg > 9) {
            strSeg = "" + seg;
        } else {
            strSeg = "0" + seg;
        }

        if (min < 10) {
            strMin = "0" + min;
        } else {
            strMin = "" + min;
        }

        lblTime.setText(strMin + ":" + strSeg);
    }

    public static void UpdateMinas(int num) {
        if (num < 0) {
            lblMines.setForeground(Color.red);
        } else {
            lblMines.setForeground(Color.green);
        }
        lblMines.setText("" + num);
    }

    public static void UpdateIconStart(ImageIcon icon, String text) {
        if (icon != null) {
            lblStart.setIcon(icon);
        } else {
            lblStart.setIcon(null);
            lblStart.setText(text);
        }
    }
}
