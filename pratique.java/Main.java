
public class Main {
    //implimenter une methode de clalculerPrix dans une classe parent Produit et une classe enfant ProduitSoldé livres et dvd
    public static void main(String[] args) {
        Livre livre = new Livre("livre1", 20, 10);
        System.out.println(livre.calculerPrix());
        DVD dvd = new DVD("dvd1", 20, 10);
        System.out.println(dvd.calculerPrix());
    }
}
// Compare this snippet from exopratique.java/pratique.java/ProduitSoldé.java:
class Produit {
    private String nom;
    private double prix;
    private double remise;
    public Produit(String nom, double prix, double remise) {
        this.nom = nom;
        this.prix = prix;
        this.remise = remise;
    }
    public double calculerPrix() {
        return prix - (remise * prix / 100);
    }
}
class Livre extends Produit {
    public Livre(String nom, double prix, double remise) {
        super(nom, prix, remise);
    }
}
class DVD extends
Produit {
    public DVD(String nom, double prix, double remise) {
        super(nom, prix, remise);
    }
}
 