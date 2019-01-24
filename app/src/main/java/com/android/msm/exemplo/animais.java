package com.android.msm.exemplo;

public class animais {
    private int ranking;
    private  String raca;

    public int getranking() {
        return ranking;
    }

    public void setranking(int rancking) {
        ranking = rancking;
    }

    public animais(int rancking, String raca) {
        ranking = rancking;
        this.raca = raca;
    }

    public String getNome() {
        return raca;
    }

    public void setNome(String raca) {
        this.raca = raca;
    }
}
