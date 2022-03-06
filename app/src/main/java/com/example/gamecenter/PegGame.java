package com.example.gamecenter;

import android.view.View;

import java.io.Serializable;

public class PegGame implements Serializable {

    private final static int OUT_OF_BOUNDS = -1;
    private final static int EMPTY = 0;
    private final static int PEG = 1;
    private final static int SELECTED_PEG = 2;

    private int[][] board = new int[7][7]; //-1: outOfBounds, 0: no peg, 1: peg, 2: selected peg
    private int[][] boardAnterior;
    private boolean selected;
    private boolean win;
    private boolean lose;
    private boolean undoPressed;
    private int selectedPegI;
    private int selectedPegJ;
    private int pegsLeft;

    public PegGame() {
        generateBoard();
        boardAnterior = copyMatriz(board);
        selected = false;
        win = false;
        lose = false;
        undoPressed = true;
        pegsLeft = 32;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getPegsLeft() {
        return pegsLeft;
    }

    public void setPegsLeft(int pegsLeft) {
        this.pegsLeft = pegsLeft;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public void generateBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((i < 2 || i > 4) && (j < 2 || j > 4)) {
                    board[i][j] = OUT_OF_BOUNDS;
                } else if (i == 3 && j == 3) {
                    board[i][j] = EMPTY;
                } else {
                    board[i][j] = PEG;
                }
            }
        }
    }

    public int[][] copyMatriz(int[][] orig) {
        int[][] matrizC = new int[orig.length][orig[0].length];
        for (int i = 0; i < orig.length; i++) {
            for (int j = 0; j < orig[0].length; j++) {
                matrizC[i][j] = orig[i][j];
            }
        }
        return matrizC;
    }

    //arreglar contador de pegs
    public void undo() {
        if (!undoPressed) {
            board = copyMatriz(boardAnterior);
            selected = false;
            lose = false;
            undoPressed = true;
            pegsLeft++;
        }
    }

    public void clickPeg(View view) {
        int i = Character.getNumericValue(view.getTag().toString().charAt(0));
        int j = Character.getNumericValue(view.getTag().toString().charAt(1));
        if (!selected) {
            if (board[i][j] == PEG) {
                selectPeg(i, j);
            }
        } else {
            if (selectedPegI == i && selectedPegJ == j) {
                deselect(i, j);
            } else if (board[i][j] == PEG) {
                deselect(selectedPegI, selectedPegJ);
                selectPeg(i, j);
            } else if (board[i][j] == EMPTY) {
                movimiento(i, j);
            }
        }
        checkState();
    }

    public void selectPeg(int i, int j) {
        selectedPegI = i;
        selectedPegJ = j;
        board[i][j] = SELECTED_PEG;
        selected = true;
    }

    public void deselect(int i, int j) {
        board[i][j] = PEG;
        selected = false;
    }

    public void deselectAll(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                if (m[i][j] == SELECTED_PEG) {
                    m[i][j] = PEG;
                }
            }
        }
    }

    public void movimiento(int i, int j) {
        if (selectedPegI == i) {
            if ((selectedPegJ > j) && (selectedPegJ - j == SELECTED_PEG)) {
                if (board[i][selectedPegJ - 1] == PEG) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i][selectedPegJ - 1] = EMPTY;
                    pegMovement(i, j);
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegJ < j) && (j - selectedPegJ == SELECTED_PEG)) {
                if (board[i][j - 1] == PEG) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i][j - 1] = EMPTY;
                    pegMovement(i, j);
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else {
                deselect(selectedPegI, selectedPegJ);
            }
        } else if (selectedPegJ == j) {
            if ((selectedPegI > i) && (selectedPegI - i == SELECTED_PEG)) {
                if (board[selectedPegI - 1][j] == 1) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[selectedPegI - 1][j] = EMPTY;
                    pegMovement(i, j);
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegI < i) && (i - selectedPegI == SELECTED_PEG)) {
                if (board[i - 1][j] == PEG) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i - 1][j] = EMPTY;
                    pegMovement(i, j);
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else {
                deselect(selectedPegI, selectedPegJ);
            }
        } else {
            deselect(selectedPegI, selectedPegJ);
        }
        selected = false;
        lose = gameOver();
    }

    public void pegMovement(int i, int j) {
        board[selectedPegI][selectedPegJ] = EMPTY;
        board[i][j] = PEG;
        pegsLeft--;
        undoPressed = false;
    }

    public void checkState() {
        if (pegsLeft == 1) {
            win = true;
        }
    }

    //arreglar en los lados
    public boolean gameOver() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == PEG) {
                    if (i != 6 && i != 0) {
                        if (board[i + 1][j] == PEG) {
                            if (board[i - 1][j] == EMPTY) {
                                return false;
                            }
                        }
                    }
                    if (i != 6 && i != 0) {
                        if (board[i - 1][j] == PEG) {
                            if (board[i + 1][j] == EMPTY) {
                                return false;
                            }
                        }
                    }
                    if (j != 6 && j != 0) {
                        if (board[i][j + 1] == PEG) {
                            if (board[i][j - 1] == EMPTY) {
                                return false;
                            }
                        }
                    }
                    if (j != 6 && j != 0) {
                        if (board[i][j - 1] == PEG) {
                            if (board[i][j + 1] == EMPTY) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

}
