package darth.jminas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import darth.jminas.tools.ErrorReporter;

public class CentralPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

    private static final long serialVersionUID = 5390823312595822624L;

    private int dx;
    private int dy;
    private int imgDx;
    private int imgDy;

    private JMinasMain minasMain;
    private Point mira;
    private Board board;
    private int contMinasMarcadas;

    private Graphics2D g;
    private Image imgBandera;
    private Image imgExplosion;
    private ImageIcon iconNormal;
    private ImageIcon iconClick;
    private ImageIcon iconMarca;
    private ImageIcon iconLooser;
    private ImageIcon iconWiner;
    private ImageIcon iconRiendo;

    public CentralPanel(JMinasMain minasMain) {
        this.minasMain = minasMain;
        board = new Board();
        contMinasMarcadas = Variables.numberOfMines;
        TopPanel.UpdateMinas(contMinasMarcadas);
        mira = new Point(0, 0);

        loadAllImages();

        if (iconNormal != null) {
            TopPanel.UpdateIconStart(iconNormal, Variables.NORMAL_TEXT);
        }

        if (imgBandera != null) {
            imgDx = (imgBandera.getWidth(this) / 4);
            imgDy = (imgBandera.getHeight(this) / 4);
        }

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public void restart() {
        board = new Board();
        contMinasMarcadas = Variables.numberOfMines;
        TopPanel.UpdateMinas(contMinasMarcadas);
        TopPanel.UpdateIconStart(iconNormal, " :) ");
        mira = new Point(0, 0);
        repaint();
    }

    @Override
    public void paint(Graphics aux) {
        g = (Graphics2D) aux;
        g.setFont(new Font("Serif", Font.BOLD, 18));
        FontMetrics fm = g.getFontMetrics();
        int numCerc;
        String strNum;
        dx = getWidth() / Variables.width;
        dy = getHeight() / Variables.height;

        g.setColor(Color.gray);
        for (int i = 0; i < Variables.height; i++) {
            g.drawLine(0, i * dy, getWidth(), i * dy);
        }

        g.setColor(Color.lightGray);
        for (int i = 0; i < Variables.width + 1; i++) {
            g.drawLine(i * dx, 0, i * dx, getHeight());
        }

        for (int i = 0; i < Variables.width; i++) {
            for (int j = 0; j < Variables.height; j++) {
                //g.setColor(Color.gray);
                //g.setPaint(new GradientPaint(11, 10, Color.gray, 25, 25, Color.lightGray, true));
                g.setPaint(new Color(0xb9bcb5));
                //if(mapa.TieneMina(i, j))
                //	g.setColor(Color.red);
                g.fillRect(i * dx + 1, j * dy + 1, dx - 1, dy - 1);

                if (board.isCellOpen(i, j)) {
                    if (board.hasMine(i, j)) {
                        if (board.cellIsMarked(i, j)) {
                            g.setPaint(new GradientPaint(11, 10, new Color(0, 255, 0), 25, 25, new Color(0, 200, 0), true));
                        } else {
                            g.setPaint(new GradientPaint(11, 10, new Color(255, 0, 0), 25, 25, new Color(200, 0, 0), true));
                        }
                        g.fillRect(i * dx + 1, j * dy + 1, dx - 1, dy - 1);
                        if (imgExplosion != null) {
                            g.drawImage(imgExplosion, i * dx + (dx / 4), j * dy + (dy / 4), dx - (dx / 2), dy - (dy / 2), this);
                        }
                    } else {
                        g.setPaint(new GradientPaint(11, 10, new Color(200, 200, 200), 25, 25, new Color(230, 230, 230), true));
                        g.fillRect(i * dx + 1, j * dy + 1, dx - 1, dy - 1);
                    }

                    numCerc = board.getNumberOfAdjacentMines(i, j);
                    if (numCerc != 0 && !board.hasMine(i, j)) {
                        strNum = "" + numCerc;
                        g.setColor(Variables.getColorCantidad(board.getNumberOfAdjacentMines(i, j)));
                        g.drawString(strNum, i * dx + dx / 2 - fm.stringWidth(strNum) / 3, j * dy + dy / 2 + fm.getHeight() / 3);
                    }
                } else if (board.cellIsMarked(i, j)) {
                    if (imgBandera != null) {
                        g.drawImage(imgBandera, i * dx + imgDx, j * dy + imgDy, dx - (dx / 3), dy - (dy / 3), this);
                    } else {
                        g.setPaint(new GradientPaint(11, 10, new Color(0, 255, 0), 25, 25, new Color(0, 200, 0), true));
                        g.fillRect(i * dx, j * dy, dx, dy);
                    }
                }
            }
        }
        g.setColor(Color.red);
        g.drawRect(mira.x * dx + 1, mira.y * dy + 1, dx - 2, dy - 2);
    }

    public void lostGame() {
        TopPanel.UpdateIconStart(iconLooser, Variables.LOOSER_TEXT);
        board.openAllCells();
        repaint();
    }

    public void wonGame() {
        TopPanel.UpdateIconStart(iconWiner, Variables.WINNER_TEXT);
    }

    private void abrir(int x, int y, int op) {
        if (x < 0 || x >= Variables.width || y < 0 || y >= Variables.height) {
            return;
        }
        if (minasMain.Ganador) {
            return;
        }
        if (!minasMain.isPlaying()) {
            minasMain.StartGame();

            // se reliza una pequeña pausa por si la primer casilla abierta
            // contiene una mina, o el cronometro podría continuar la ejecucion
            try {
                Thread.sleep(5);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }

        switch (op) {
            case 0:
                if (!board.cellIsMarked(x, y)) {
                    if (!board.openCell(x, y)) {
                        minasMain.LostGame();
                        board.openMine(x, y);
                    }
                    if (valida()) {
                        minasMain.WinGame();
                    }
                }
                break;
            case 1:
                if (!board.isCellOpen(x, y)) {
                    if (board.toggleMarkCell(x, y)) {
                        --contMinasMarcadas;
                    } else {
                        ++contMinasMarcadas;
                    }
                    TopPanel.UpdateMinas(contMinasMarcadas);
                }
                break;
        }
        repaint();
    }

    private boolean valida() {
        return board.getOpenedCells() == (Variables.width * Variables.height - Variables.numberOfMines);
    }

    private void loadAllImages() {
        imgBandera = loadImage(Variables.FLAG_ICON_PATH).getImage();
        imgExplosion = loadImage(Variables.EXPLOSION_ICON_PATH).getImage();
        iconNormal = loadImage(Variables.NORMAL_ICON_PATH);
        iconClick = loadImage(Variables.CLICK_ICON_PATH);
        iconMarca = loadImage(Variables.MARK_ICON_PATH);
        iconLooser = loadImage(Variables.LOOSER_ICON_PATH);
        iconWiner = loadImage(Variables.WINNER_ICON_PATH);
        iconRiendo = loadImage(Variables.LAUGHING_ICON_PATH);
    }

    private ImageIcon loadImage(String path) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            return icon;
        } catch (NullPointerException e) {
            new ErrorReporter().CreateLog(path);
            return null;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1) {
            TopPanel.UpdateIconStart(iconClick, Variables.CLICK_TEXT);
        } else {
            TopPanel.UpdateIconStart(iconMarca, Variables.MARK_TEXT);
        }

        int x = e.getX() / dx;
        int y = e.getY() / dy;
        if (x != mira.getX() || y != mira.getY()) {
            mira = new Point(x, y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        TopPanel.UpdateIconStart(iconNormal, Variables.NORMAL_TEXT);
        int x = e.getX() / dx;
        int y = e.getY() / dy;
        if (mira.x != x || mira.y != y) {
            return;
        }

        if (e.getButton() == 3 || e.getButton() == 2) {
            abrir(x, y, 1);
        } else if (e.getButton() == 1) {
            abrir(x, y, 0);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int x = e.getX() / dx;
        int y = e.getY() / dy;
        if (x != mira.getX() || y != mira.getY()) {
            mira = new Point(x, y);
            if (minasMain.isPlaying()) {
                repaint();
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int x = (int) mira.getX();
        int y = (int) mira.getY();

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (x > 0) {
                    mira = new Point(x - 1, y);
                    repaint();
                } else {
                    mira = new Point(Variables.width - 1, y);
                    repaint();
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (x < Variables.width - 1) {
                    mira = new Point(x + 1, y);
                    repaint();
                } else {
                    mira = new Point(0, y);
                    repaint();
                }
                break;
            case KeyEvent.VK_UP:
                if (y > 0) {
                    mira = new Point(x, y - 1);
                    repaint();
                } else {
                    mira = new Point(x, Variables.height - 1);
                    repaint();
                }
                break;
            case KeyEvent.VK_DOWN:
                if (y < Variables.height - 1) {
                    mira = new Point(x, y + 1);
                    repaint();
                } else {
                    mira = new Point(x, 0);
                    repaint();
                }
                break;
            case KeyEvent.VK_ENTER:
                TopPanel.UpdateIconStart(iconClick, Variables.CLICK_TEXT);
                abrir(x, y, 0);
                break;
            case KeyEvent.VK_SPACE:
                TopPanel.UpdateIconStart(iconClick, Variables.CLICK_TEXT);
                abrir(x, y, 0);
                break;
            case KeyEvent.VK_CONTROL:
                TopPanel.UpdateIconStart(iconMarca, Variables.MARK_TEXT);
                abrir(x, y, 1);
                break;
            case KeyEvent.VK_N:
                TopPanel.UpdateIconStart(iconRiendo, Variables.LAUGHING_TEXT);
                minasMain.RestartGame();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (minasMain.Ganador) {
            TopPanel.UpdateIconStart(iconWiner, Variables.WINNER_TEXT);
        } else {
            TopPanel.UpdateIconStart(iconNormal, Variables.NORMAL_TEXT);
        }
    }
}
