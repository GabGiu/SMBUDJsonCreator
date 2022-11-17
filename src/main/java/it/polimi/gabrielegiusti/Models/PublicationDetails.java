package it.polimi.gabrielegiusti.Models;

import java.util.Date;

public class PublicationDetails {

    private String journalName;

    private String volume;

    private int number;

    private String date;

    private String pages;

    private String editor;

    private int numberOfCopiesSold;

    public PublicationDetails(){}

    public PublicationDetails(String journalName, String volume,
                              int number, String date, String pages,
                              String editor, int numberOfCopiesSold) {
        this.journalName = journalName;
        this.volume = volume;
        this.number = number;
        this.date = date;
        this.pages = pages;
        this.editor = editor;
        this.numberOfCopiesSold = numberOfCopiesSold;
    }

    public String getJournalName() {
        return journalName;
    }

    public void setJournalName(String journalName) {
        this.journalName = journalName;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getNumberOfCopiesSold() {
        return numberOfCopiesSold;
    }

    public void setNumberOfCopiesSold(int numberOfCopiesSold) {
        this.numberOfCopiesSold = numberOfCopiesSold;
    }
}
