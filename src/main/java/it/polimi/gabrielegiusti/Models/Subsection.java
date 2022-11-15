package it.polimi.gabrielegiusti.Models;

import java.util.List;

public class Subsection {

    private int subsecNumber;

    private String subsecTitle;

    private String subsecText;

    private List<Paragraph> paragraphs;

    public Subsection(){}

    public Subsection(int subsecNumber, String subsecTitle, String subsecText, List<Paragraph> paragraphs) {
        this.subsecNumber = subsecNumber;
        this.subsecTitle = subsecTitle;
        this.subsecText = subsecText;
        this.paragraphs = paragraphs;
    }

    public int getSubsecNumber() {
        return subsecNumber;
    }

    public void setSubsecNumber(int subsecNumber) {
        this.subsecNumber = subsecNumber;
    }

    public String getSubsecTitle() {
        return subsecTitle;
    }

    public void setSubsecTitle(String subsecTitle) {
        this.subsecTitle = subsecTitle;
    }

    public String getSubsecText() {
        return subsecText;
    }

    public void setSubsecText(String subsecText) {
        this.subsecText = subsecText;
    }

    public List<Paragraph> getParagraphs() {
        return paragraphs;
    }

    public void setParagraphs(List<Paragraph> paragraphs) {
        this.paragraphs = paragraphs;
    }
}
