
public class Vente {
    private int produitVenduId;
    private java.sql.Date dateVente;
    private int quantiteVendue;

    public Vente(int produitVenduId, java.sql.Date dateVente, int quantiteVendue) {
        this.produitVenduId = produitVenduId;
        this.dateVente = dateVente;
        this.quantiteVendue = quantiteVendue;
    }

    public int getProduitVenduId() {
        return produitVenduId;
    }

    public void setProduitVendu(int produitVenduId) {
        this.produitVenduId = produitVenduId;
    }

    public java.sql.Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(java.sql.Date dateVente) {
        this.dateVente = dateVente;
    }

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }
}
