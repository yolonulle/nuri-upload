package com.inhwa.nan05.activity;

/**
 * Created by Inhwa_ on 2017-06-19.
 */

public class Performance {
    private String title;
    private String region;
    private String genre;
    private String pdate;
    private String ptime;

    public Performance() {
    }

    public Performance(String title, String region, String genre, String pdate, String ptime) {
        this.title = title;
        this.region = region;
        this.genre = genre;
        this.pdate = pdate;
        this.ptime = ptime;

    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}