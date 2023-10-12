package com.android.msm.exemplo;


public class animaisVO {
    private int rankig;
    private  String especie;

    public int getRankig() {
        return rankig;
    }

    public void setRankig(int rankig) {
        this.rankig = rankig;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public double getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(double ratingBar) {
        this.ratingBar = ratingBar;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    private String img;
	private boolean checked = false;
	private double ratingBar = 0.0;
    private  String raca;
}
