package it.polimi.gabrielegiusti.Models;

public class Affiliation {

    private String affiliationName;

    private String affiliationDepartment;

    private Location location;

    public Affiliation(String affiliationName, String affiliationDepartment, Location location) {
        this.affiliationName = affiliationName;
        this.affiliationDepartment = affiliationDepartment;
        this.location = location;
    }

    public String getAffiliationName() {
        return affiliationName;
    }

    public void setAffiliationName(String affiliationName) {
        this.affiliationName = affiliationName;
    }

    public String getAffiliationDepartment() {
        return affiliationDepartment;
    }

    public void setAffiliationDepartment(String affiliationDepartment) {
        this.affiliationDepartment = affiliationDepartment;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
