package com.example.gamecenter;

import android.view.View;

public class PegGame {

    private int[][] board = new int[7][7]; //-1: outOfBounds, 0: no peg, 1: peg, 2: selected peg
    private int[][] boardAnterior;
    private boolean selected;
    private boolean win;
    private int selectedPegI;
    private int selectedPegJ;
    private int pegsLeft;

    public PegGame() {
        generateBoard();
        boardAnterior = copyMatriz(board);
        selected = false;
        win = false;
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

    public void generateBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if ((i < 2 || i > 4) && (j < 2 || j > 4)) {
                    board[i][j] = -1;
                } else if (i == 3 && j == 3) {
                    board[i][j] = 0;
                } else {
                    board[i][j] = 1;
                }
            }
        }
    }

    public int[][] copyMatriz(int[][] orig) {
        int[][] matrizC = new int[orig.length][orig[0].length];
        for (int i=0; i<orig.length; i++) {
            for (int j = 0; j< orig[0].length; j++) {
                matrizC[i][j] = orig[i][j];
            }
        }
        return matrizC;
    }

    //arreglar contador de pegs
    public void undo() {
        board = copyMatriz(boardAnterior);
        selected = false;
    }

    public void clickPeg(View view) {
        int i = Character.getNumericValue(view.getTag().toString().charAt(0));
        int j = Character.getNumericValue(view.getTag().toString().charAt(1));
        if (!selected) {
            if (board[i][j] == 1) {
                selectPeg(i, j);
            }
        } else {
            if (selectedPegI == i && selectedPegJ == j) {
                deselect(i, j);
            } else if (board[i][j] == 1) {
                deselect(selectedPegI, selectedPegJ);
                selectPeg(i, j);
            } else if (board[i][j] == 0) {
                movimiento(i, j);
            }
        }
        checkState();
    }

    public void selectPeg(int i, int j) {
        selectedPegI = i;
        selectedPegJ = j;
        board[i][j] = 2;
        selected = true;
    }

    public void deselect(int i, int j) {
        board[i][j] = 1;
        selected = false;
    }

    public void deselectAll(int [][] m) {
        for (int i = 0; i< m.length; i++) {
            for (int j = 0; j<m[0].length; j++) {
                if (m[i][j] == 2) {
                    m[i][j] = 1;
                }
            }
        }
    }

    public void movimiento(int i, int j) {
        if (selectedPegI == i) {
            if ((selectedPegJ > j) && (selectedPegJ - j == 2)) {
                if (board[i][selectedPegJ - 1] == 1) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i][selectedPegJ - 1] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegJ < j) && (j - selectedPegJ == 2)) {
                if (board[i][j - 1] == 1) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i][j - 1] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else {
                deselect(selectedPegI, selectedPegJ);
            }
        } else if (selectedPegJ == j) {
            if ((selectedPegI > i) && (selectedPegI - i == 2)) {
                if (board[selectedPegI - 1][j] == 1) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[selectedPegI - 1][j] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegI < i) && (i - selectedPegI == 2)) {
                if (board[i - 1][j] == 1) {
                    boardAnterior = copyMatriz(board);
                    deselectAll(boardAnterior);
                    board[i - 1][j] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    pegsLeft--;
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
    }

    public void checkState() {
        if (pegsLeft == 1) {
            win = true;
        }
    }

}
