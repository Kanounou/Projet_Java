package AppliEdu;

import java.util.ArrayList;
import java.util.Scanner;



public class Main {
    private ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
 
    public void Menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("1. Ajouter un etudiant");
        System.out.println("2. Afficher les etudiants");
        System.out.println("3. Supprimer un etudiant");
        System.out.println("4. Quitter");
        System.out.println("Entrez votre choix");
    }

    public void AjouterEtudiant() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de l'etudiant");
        String nom = sc.nextLine();
        System.out.println("Entrez le prenom de l'etudiant");
        String prenom = sc.nextLine();
        System.out.println("Entrez la classe de l'etudiant");
        String classe = sc.nextLine();

        // Créer un nouvel étudiant et l'ajouter à la liste
        Etudiant etudiant = new Etudiant(nom, prenom, classe);
        etudiants.add(etudiant);
        System.out.println("Etudiant ajouté avec succès !");
    }

    public void AfficherEtudiants() {
        if (etudiants.isEmpty()) {
            System.out.println("Aucun étudiant à afficher.");
        } else {
            for (Etudiant etudiant : etudiants) {
                System.out.println(etudiant);
            }
        }
    }

    public void SupprimerEtudiant() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de l'etudiant à supprimer");
        String nom = sc.nextLine();

        boolean trouve = false;
        for (Etudiant etudiant : etudiants) {
            if (etudiant.getNom().equals(nom)) {
                etudiants.remove(etudiant);
                trouve = true;
                System.out.println("Etudiant supprimé avec succès !");
                break;
            }
        }

        if (!trouve) {
            System.out.println("Aucun étudiant trouvé avec ce nom.");
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        Scanner sc = new Scanner(System.in);
        int choix = 0;

        while (choix != 4) {
            main.Menu();
            choix = sc.nextInt();
            sc.nextLine();   

            switch (choix) {
                case 1:
                    main.AjouterEtudiant();
                    break;
                case 2:
                    main.AfficherEtudiants();
                    break;
                case 3:
                    main.SupprimerEtudiant();
                    break;
                case 4:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
                    break;
            }
        }
    }
}