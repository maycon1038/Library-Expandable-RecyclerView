package com.android.msm.exemplo;

public class animais {
    private int ranking;

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    private  String raca;
    public animais(int rancking, String raca) {
        ranking = rancking;
        this.raca = raca;
    }
}
