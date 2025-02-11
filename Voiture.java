import java.util.Scanner;

public class Voiture {
    //Attributs
     String marque;
     String modele;
     String couleur;
     public static String coloAutorized = {"Rouge", "Bleu", "Vert", "Blanc", "Noir"};


    //Methodes
    void demarrer(){
        System.out.println("La voiture demarre");
    }
    void accelerer(){
        System.out.println("La voiture accelere");
    }
    void freiner(){
        System.out.println("La voiture freine");
    }
    
}
