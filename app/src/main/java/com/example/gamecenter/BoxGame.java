package com.example.gamecenter;

public class BoxGame {

    private int[][] matriz = new int[4][4];
    private int[][] matrizAnterior;
    private boolean lose;

    public BoxGame() {
        iniciarMatriz();
        generateNumber();
        matrizAnterior = copyMatriz(matriz);
        lose = false;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(int[][] matriz) {
        this.matriz = matriz;
    }

    public int[][] getMatrizAnterior() {
        return matrizAnterior;
    }

    public void setMatrizAnterior(int[][] matrizA) {
        this.matrizAnterior = matrizA;
    }

    public boolean isLose() {
        return lose;
    }

    public void setLose(boolean lose) {
        this.lose = lose;
    }

    public void swipeUp() {
        matrizAnterior = copyMatriz(matriz);
        if(!lose) {
            updateUp(matriz);
            if (!checkZero()) {
                if (checkAdd()) {
                    lose = true;
                } else {
                    generateNumber();
                }
            } else {
                generateNumber();
            }
        }
    }

    public void swipeDown() {
        matrizAnterior = copyMatriz(matriz);
        if(!lose) {
            updateDown(matriz);
            if (!checkZero()) {
                if (checkAdd()) {
                    lose = true;
                } else {
                    generateNumber();
                }
            } else {
                generateNumber();
            }
        }
    }

    public void swipeLeft() {
        matrizAnterior = copyMatriz(matriz);
        if(!lose) {
            updateLeft(matriz);
            if (!checkZero()) {
                if (checkAdd()) {
                    lose = true;
                } else {
                    generateNumber();
                }
            } else {
                generateNumber();
            }
        }
    }

    public void swipeRight() {
        matrizAnterior = copyMatriz(matriz);
        if(!lose) {
            updateRight(matriz);
            if (!checkZero()) {
                if (checkAdd()) {
                    lose = true;
                } else {
                    generateNumber();
                }
            } else {
                generateNumber();
            }
        }
    }

    public void iniciarMatriz() {
        for(int i =0; i< matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++) {
                matriz[i][j] = 0;
            }
        }
        generateNumber();
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

    public boolean matrizIgual(int[][] m, int[][] n) {
        for(int i=0; i<m.length; i++) {
            for (int j= 0; j<m[0].length; j++) {
                if (m[i][j] != n[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void generateNumber() {
        boolean run = true;
        if (checkZero()) {
            while (run) {
                int r1 = (int) (Math.random() * 4);
                int r2 = (int) (Math.random() * 4);
                if (matriz[r1][r2] == 0) {
                    run = false;
                    int r = (int) (Math.random() * 10+1);
                    if (r == 1) {
                        matriz[r1][r2] = 4;
                    } else {
                        matriz[r1][r2] = 2;
                    }
                }
            }
        }
    }

    public void undo() {
        lose = false;
        matriz = copyMatriz(matrizAnterior);
    }

    public boolean checkZero() {
        for(int i = 0; i<matriz.length; i++) {
            for(int j=0; j<matriz[0].length; j++) {
                if (matriz[i][j] == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkAdd() {
        int[][] mAux;
        mAux = copyMatriz(matriz);
        updateDown(mAux);
        if(!matrizIgual(mAux, matriz)) {
            return false;
        }
        mAux = copyMatriz(matriz);
        updateUp(mAux);
        if(!matrizIgual(mAux, matriz)) {
            return false;
        }
        mAux = copyMatriz(matriz);
        updateLeft(mAux);
        if(!matrizIgual(mAux, matriz)) {
            return false;
        }
        mAux = copyMatriz(matriz);
        updateRight(mAux);
        if(!matrizIgual(mAux, matriz)) {
            return false;
        }
        return true;
    }

    public void updateUp(int[][] m) {
        for (int col = 0; col<m.length; col++) {
            movementUp(col, m);
            addUp(col, m);
            movementUp(col, m);
        }
    }

    public void movementUp(int col, int[][] m) {
        for (int i=0; i<3; i++) {
            for (int fil = 0; fil < m.length - 1; fil++) {
                if (m[fil][col] == 0 && !(m[fil+1][col] == 0)) {
                    m[fil][col] = m[fil+1][col];
                    m[fil+1][col] = 0;
                }
            }
        }
    }

    public void addUp(int col, int[][] m) {
        for (int fil = 0; fil<m.length-1; fil++) {
            if (m[fil][col] == m[fil+1][col]) {
                int aux = m[fil][col] + m[fil+1][col];
                m[fil][col] = aux;
                m[fil+1][col] = 0;
            }
        }
    }

    public void updateDown(int[][] m) {
        for (int col = 0; col<m.length; col++) {
            movementDown(col, m);
            addDown(col, m);
            movementDown(col, m);
        }
    }

    public void movementDown(int col, int[][] m) {
        for (int i=0; i<3; i++) {
            for (int fil = m.length-1; fil > 0; fil--) {
                if (m[fil][col] == 0 && !(m[fil-1][col] == 0)) {
                    m[fil][col] = m[fil-1][col];
                    m[fil-1][col] = 0;
                }
            }
        }
    }

    public void addDown(int col, int[][] m) {
        for (int fil = m.length-1; fil>0; fil--) {
            if (m[fil][col] == m[fil-1][col]) {
                int aux = m[fil][col] + m[fil-1][col];
                m[fil][col] = aux;
                m[fil-1][col] = 0;
            }
        }
    }

    public void updateLeft(int[][] m) {
        for (int fil = 0; fil<m.length; fil++) {
            movementLeft(fil, m);
            addLeft(fil, m);
            movementLeft(fil, m);
        }
    }

    public void movementLeft(int fil, int[][] m) {
        for (int i=0; i<3; i++) {
            for (int col = 0; col <m.length-1; col++) {
                if (m[fil][col] == 0 && !(m[fil][col+1] == 0)) {
                    m[fil][col] = m[fil][col+1];
                    m[fil][col+1] = 0;
                }
            }
        }
    }

    public void addLeft(int fil, int[][] m) {
        for (int col = 0; col<m.length-1; col++) {
            if (m[fil][col] == m[fil][col+1]) {
                int aux = m[fil][col] + m[fil][col+1];
                m[fil][col] = aux;
                m[fil][col+1] = 0;
            }
        }
    }

    public void updateRight(int[][] m) {
        for (int fil = 0; fil<m.length; fil++) {
            movementRight(fil, m);
            addRight(fil, m);
            movementRight(fil, m);
        }
    }

    public void movementRight(int fil, int[][] m) {
        for (int i=0; i<3; i++) {
            for (int col = m.length-1; col >0; col--) {
                if (m[fil][col] == 0 && !(m[fil][col-1] == 0)) {
                    m[fil][col] = m[fil][col-1];
                    m[fil][col-1] = 0;
                }
            }
        }
    }

    public void addRight(int fil, int[][] m) {
        for (int col = m.length-1; col>0; col--) {
            if (m[fil][col] == m[fil][col-1]) {
                int aux = m[fil][col] + m[fil][col-1];
                m[fil][col] = aux;
                m[fil][col-1] = 0;
            }
        }
    }
}
