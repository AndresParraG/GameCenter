package com.example.gamecenter;

import android.view.View;

public class PegGame {

    private int[][] board = new int[7][7]; //-1: outOfBounds, 0: no peg, 1: peg, 2: selected peg
    private boolean selected;
    private boolean win;
    private int selectedPegI;
    private int selectedPegJ;
    private int moveCount;
    private int pegsLeft;

    public PegGame() {
        generateBoard();
        selected = false;
        win = false;
        moveCount = 0;
        pegsLeft = 32;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
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

    public void movimiento(int i, int j) {
        if (selectedPegI == i) {
            if ((selectedPegJ > j) && (selectedPegJ - j == 2)) { //abajo --> arriba
                if (board[i][selectedPegJ - 1] == 1) {
                    board[i][selectedPegJ - 1] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    moveCount++;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegJ < j) && (j - selectedPegJ == 2)) { //arriba --> abajo
                if (board[i][j - 1] == 1) {
                    board[i][j - 1] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    moveCount++;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else {
                deselect(selectedPegI, selectedPegJ);
            }
        } else if (selectedPegJ == j) {
            if ((selectedPegI > i) && (selectedPegI - i == 2)) { //derecha --> izquierda
                if (board[selectedPegI - 1][j] == 1) {
                    board[selectedPegI - 1][j] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    moveCount++;
                    pegsLeft--;
                } else {
                    deselect(selectedPegI, selectedPegJ);
                }
            } else if ((selectedPegI < i) && (i - selectedPegI == 2)) { //izquierda --> derecha
                if (board[i - 1][j] == 1) {
                    board[i - 1][j] = 0;
                    board[selectedPegI][selectedPegJ] = 0;
                    board[i][j] = 1;
                    moveCount++;
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
