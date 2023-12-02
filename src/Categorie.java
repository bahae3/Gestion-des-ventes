public class Categorie {
    int id;
    private String designation, description;

    public Categorie(int id, String designation, String description) {
        this.id = id;
        this.designation = designation;
        this.description = description;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
