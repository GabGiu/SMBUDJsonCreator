package it.polimi.gabrielegiusti.Models;

import java.util.Date;

public class PublicationDetails {

    private String journalName;

    private String volume;

    private int number;

    private Date date;

    private int pages;

    private String editor;

    public PublicationDetails(){}

    public PublicationDetails(String journalName, String volume,
                              int number, Date date, int pages,
                              String editor) {
        this.journalName = journalName;
        this.volume = volume;
        this.number = number;
        this.date = date;
        this.pages = pages;
        this.editor = editor;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}
