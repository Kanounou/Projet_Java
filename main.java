
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        boolean found = false;
        for(String color: Voiture.coloAutorized){
            if(color.equals("couleur")){
                found = true;
                break;
            }
        }
if (found){
      Voiture voiture = new Voiture("Toyota", "Corolla", "Bleu");

      System.out.println("Voiture : " + voiture.getMarque() + " " + voiture.getModele() + " " + voiture.getCouleur());
         

    }else{
        System.out.println("Couleur non trouvée");
    }
}
}