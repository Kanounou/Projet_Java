package AppliEdu;

// Classe Etudiant
class Etudiant {
    private String nom;
    private String prenom;
    private String classe;

    // Constructeur
    public Etudiant(String nom, String prenom, String classe) {
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getClasse() {
        return classe;
    }

    // Méthode toString pour afficher les informations de l'étudiant
    @Override
    public String toString() {
        return "Etudiant [nom=" + nom + ", prenom=" + prenom + ", classe=" + classe + "]";
    }
}
