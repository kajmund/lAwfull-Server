package lawscraper.shared;

public enum UserRole {
    Anonymous("ROLE_ANONYMOUS", "Anonym"),
    Reader("ROLE_READER", "Läsare"),
    Employee("ROLE_USER", "Användare"),
    Administrator("ROLE_ADMINISTRATOR", "Administratör"),
    SuperAdministrator("ROLE_SUPERADMIN", "Superadmin"),
    All("ROLE_ALL", "Alla"),;

    private String designation;
    private String descriptiveName;

    UserRole(String designation, String descriptiveName) {
        this.descriptiveName = descriptiveName;
        this.setDesignation(designation);
    }

    private void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public static UserRole getVal(String str) {

        for (UserRole userRole : values()) {
            if (str.equals(userRole.getDesignation())) {
                return userRole;
            }
        }

        return null;
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }
}