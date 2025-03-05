package DevoirClasse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileWriter;

public class Main {
    public static void main(String[] args) {
        try {
            // écrire dans le fichier 
            // (La variable "writer" était créée mais jamais utilisée, on peut la supprimer)
            // FileWriter writer = new FileWriter("DevoirClasse/Produit.txt");
            File file = new File("DevoirClasse/Produit.txt");
            Scanner lecture = new Scanner(file);
            while (lecture.hasNextLine()) {
                System.out.println(lecture.nextLine());
                System.out.println("Lecture du fichier réussie");
                // Pour éviter de lancer nextLine() sans vérifier qu'il y a bien une ligne suivante :
                if (lecture.hasNextLine()) {
                    lecture.nextLine();
                }
            }
            lecture.close(); 
            try {
                FileWriter myWriter = new FileWriter("DevoirClasse/Produit.txt", true);
                myWriter.write("\nCoucou");
                myWriter.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de l'écriture dans le fichier");
                e.printStackTrace();
            }
            // ajoute des trucs 
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erreur IO");
            e.printStackTrace();
        }
    }
}+