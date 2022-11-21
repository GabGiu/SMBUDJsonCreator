package it.polimi.gabrielegiusti.Models;

import java.util.List;

public class Section {

    private int sectionNumber;

    private String sectionTitle;

    private String sectionText;

    private List<Subsection> subsection;

    public Section(){}

    public Section(String sectionTitle,
                   String sectionText, List<Subsection> subsection) {
        this.sectionTitle = sectionTitle;
        this.sectionText = sectionText;
        this.subsection = subsection;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Subsection> getSubsection() {
        return subsection;
    }

    public void setSubsection(List<Subsection> subsection) {
        this.subsection = subsection;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public String getSectionText() {
        return sectionText;
    }

    public void setSectionText(String sectionText) {
        this.sectionText = sectionText;
    }
}
