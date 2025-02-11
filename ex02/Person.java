public class Person {
    private String nom;
    private int age;

    //*Methode */
    public Person(String nom, int age) {
        this.nom = nom;
        this.age = age;
    }

    public String getNom() {
        return this.nom;
    }

    public int getAge() {
        return this.age;
    }
    
    public void sePresenter(){
        System.out.println("Bonjour " + this.nom + " et j'ai " + this.age + " ans");
    }

}
