public class CompteBancaire {
    private String titulaire;
    private double solde;

    public CompteBancaire(String titulaire, double solde) {
        this.titulaire = titulaire;
        this.solde = solde;
    }

    public void deposer(double montant) {
        if(montant > 0){
        this.solde += montant;
        System.out.println(montant + "Le montant a été déposé avec succès");
        }

    }
    public void retirer(double montant) { //*ou encore if((this.solde - montant)>0) */
        if(montant > 0 && montant <= this.solde){
        this.solde -= montant;
        System.out.println(montant + "Le montant a été retiré avec succès");
        }
        else{
            System.out.println("Le montant est insuffisant");
        }
    }
    
}
