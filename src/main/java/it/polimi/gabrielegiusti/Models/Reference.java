package it.polimi.gabrielegiusti.Models;

import java.util.List;

public class Reference {

    private List<Object> author;

    private String title;

    private int year;

    private String DOI;

    public Reference(){}

    public Reference(List<Object> author, String title, int year, String DOI) {
        this.author = author;
        this.title = title;
        this.year = year;
        this.DOI = DOI;
    }

    public List<Object> getAuthor() {
        return author;
    }

    public void setAuthor(List<Object> author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDOI() {
        return DOI;
    }

    public void setDOI(String DOI) {
        this.DOI = DOI;
    }
}
