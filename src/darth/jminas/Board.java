package darth.jminas;

public class Board {

    private Cell board[][];
    private int openedCells;

    public Board() {
        board = new Cell[Variables.ancho][Variables.alto];
        for (int i = 0; i < Variables.ancho; i++) {
            for (int j = 0; j < Variables.alto; j++) {
                board[i][j] = new Cell();
            }
        }
        placeMines();
        openedCells = 0;
    }

    private void placeMines() {
        int x, y, cont = 0;
        while (cont < Variables.numeroMinas) {
            x = (int) (Math.random() * Variables.ancho - 0);
            y = (int) (Math.random() * Variables.alto - 0);
            if (board[x][y].hasMine) {
                continue;
            }
            board[x][y].hasMine = true;
            addAdjacentMineCounter(x - 1, y - 1);
            addAdjacentMineCounter(x, y - 1);
            addAdjacentMineCounter(x + 1, y - 1);
            addAdjacentMineCounter(x - 1, y);
            addAdjacentMineCounter(x + 1, y);
            addAdjacentMineCounter(x - 1, y + 1);
            addAdjacentMineCounter(x, y + 1);
            addAdjacentMineCounter(x + 1, y + 1);
            //System.out.println(x+"-"+y);
            cont++;
        }
    }

    private void addAdjacentMineCounter(int x, int y) {
        if (x < 0 || y < 0 || x >= Variables.ancho || y >= Variables.alto) {
            return;
        } else {
            board[x][y].numberOfAdjacentMines++;
        }
    }

    public boolean hasMine(int x, int y) {
        return board[x][y].hasMine;
    }

    public boolean isCellOpen(int x, int y) {
        return board[x][y].open;
    }

    /**
     * Returns false if the opened cell contains a mine
     *
     * @param x
     * @param y
     * @return
     */
    public boolean openCell(int x, int y) {
        if (board[x][y].hasMine) {
            return false;
        }

        openRecursively(x, y);
        return true;
    }

    private void openRecursively(int x, int y) {
        if (x < 0 || y < 0 || x >= Variables.ancho || y >= Variables.alto || board[x][y].open) {
        } else if (!board[x][y].hasMine && !board[x][y].isMarked) {
            board[x][y].open = true;
            openedCells++;

            if (board[x][y].numberOfAdjacentMines == 0) {
                openRecursively(x - 1, y - 1);
                openRecursively(x, y - 1);
                openRecursively(x + 1, y - 1);
                openRecursively(x - 1, y);
                openRecursively(x + 1, y);
                openRecursively(x - 1, y + 1);
                openRecursively(x, y + 1);
                openRecursively(x + 1, y + 1);
            }
        }
    }

    public boolean cellIsMarked(int x, int y) {
        return board[x][y].isMarked;
    }

    public boolean toggleMarkCell(int x, int y) {
        board[x][y].isMarked = !board[x][y].isMarked;
        return board[x][y].isMarked;
    }

    public void openMine(int x, int y) {
        board[x][y].open = true;
    }

    public void openAllCells() {
        for (int i = 0; i < Variables.ancho; i++) {
            for (int j = 0; j < Variables.alto; j++) {
                board[i][j].open = true;
            }
        }
    }

    public int getNumberOfAdjacentMines(int x, int y) {
        return board[x][y].numberOfAdjacentMines;
    }

    public int getOpenedCells() {
        return openedCells;
    }

    class Cell {

        boolean open = false;
        boolean hasMine = false;
        boolean isMarked = false;
        int numberOfAdjacentMines = 0;
    }
}
