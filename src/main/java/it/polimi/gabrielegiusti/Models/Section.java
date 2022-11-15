package it.polimi.gabrielegiusti.Models;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Section {

    private String sectionTitle;

    private List<Subsection> subsection;

    private String bibliography;

    public Section(){}

    public Section(String sectionTitle, List<Paragraph> paragraph,
                   List<Subsection> subsection,
                   String bibliography) {
        this.sectionTitle = sectionTitle;
        this.subsection = subsection;
        this.bibliography = bibliography;
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

    public String getBibliography() {
        return bibliography;
    }

    public void setBibliography(String bibliography) {
        this.bibliography = bibliography;
    }


}
