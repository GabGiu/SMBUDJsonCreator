package it.polimi.gabrielegiusti.Models;

import java.util.List;

public class Bibliography {

    private List<Reference> references;

    public Bibliography(){}

    public Bibliography(List<Reference> references) {
        this.references = references;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }
}
