package com.inhwa.nan05.activity;

import java.io.Serializable;

/**
 * Created by Inhwa_ on 2017-06-19.
 */

public class Performance implements Serializable{

    private int pnumber;
    private int artist;
    private String title;
    private String content;
    private String region;
    private String genre;
    private String pdate;
    private String ptime;

    public Performance() {
    }

    public Performance(int pnumber, int artist, String title, String content, String region, String genre, String pdate, String ptime) {
        this.pnumber = pnumber;
        this.artist = artist;
        this.title = title;
        this.content = content;
        this.region = region;
        this.genre = genre;
        this.pdate = pdate;
        this.ptime = ptime;
    }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public int getPnumber() { return pnumber; }

    public void setPnumber(int pnumber) { this.pnumber = pnumber; }

    public int getArtist() { return artist; }

    public void setArtist(int artist) { this.artist = artist; }

    public String getRegion() { return region; }

    public void setRegion(String region) { this.region = region; }

    public String getGenre() { return genre; }

    public void setGenre(String genre) { this.genre = genre; }

    public String getPdate() { return pdate; }

    public void setPdate(String pdate) { this.pdate = pdate; }

    public String getPtime() { return ptime; }

    public void setPtime(String ptime) { this.ptime = ptime; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
