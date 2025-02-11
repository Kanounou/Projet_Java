package Ex05;

public class Main {
    public static void division(int a, int b) throws ArithmeticException {
        if (b == 0) {
            throw new ArithmeticException("Division par zero");
        }
        System.out.println(a / b);
    }
    
    public static void main(String[] args) {
        try {
            // Correction de l'assignation de args : on crée un nouveau tableau de String
            args = new String[]{"1", "2", "3", "4", "5"};
            for (int i = 0; i < args.length; i++) {
                System.out.println(args[i]);
            }
            division(10, 0);
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}