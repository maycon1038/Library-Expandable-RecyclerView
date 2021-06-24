package com.android.msm.exemplo;

import lombok.Data;

@Data
public class animais {
    private int ranking;
    private  String raca;
    public animais(int rancking, String raca) {
        ranking = rancking;
        this.raca = raca;
    }

}
