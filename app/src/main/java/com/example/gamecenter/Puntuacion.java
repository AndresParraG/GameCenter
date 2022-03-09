package com.example.gamecenter;

public class Puntuacion {
    private String userName;
    private String score;

    public Puntuacion(String userName, String score) {
        this.userName = userName;
        this.score = score;
    }

    public Puntuacion() {}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
