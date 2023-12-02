public class Produit {
    int id;
    private int quantiteStock;
    private String designation;
    private double prixUnitaire;
    private Categorie categorie;

    public Produit(int id, int quantiteStock, String designation, double prixUnitaire, Categorie categorie) {
        this.id = id;
        this.quantiteStock = quantiteStock;
        this.designation = designation;
        this.prixUnitaire = prixUnitaire;
        this.categorie = categorie;
    }

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
