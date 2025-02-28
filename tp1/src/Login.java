import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Login {
    private static final String USER_DATABASE_PATH = "/home/ensai/ENSAI-2A-Java-TP/tp1/data/user_hashpwd.csv";
    private static final int MAX_ATTEMPTS = 3;

    public static void main(String[] args) {
        HashMap<String, String> userDatabase = loadUserDatabase(USER_DATABASE_PATH);
        if (userDatabase.isEmpty()) {
            System.out.println("La base de données des utilisateurs est vide ou introuvable.");
            return;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Entrez le nom d'utilisateur : ");
                String username = scanner.nextLine().trim();

                if (username.isEmpty()) {
                    System.out.println("Le nom d'utilisateur ne peut pas être vide. Veuillez réessayer.");
                    continue;
                }

                if (!userDatabase.containsKey(username)) {
                    System.out.println("Nom d'utilisateur non trouvé. Veuillez réessayer.");
                    continue;
                }

                if (authenticateUser(scanner, username, userDatabase.get(username))) {
                    System.out.println("Connexion réussie !");
                    break;
                } else {
                    System.out.println("Nombre maximal de tentatives atteint. Redémarrage de la saisie du nom d'utilisateur.");
                }
            }
        }
    }

    private static boolean authenticateUser(Scanner scanner, String username, String storedHashedPassword) {
        int attemptsLeft = MAX_ATTEMPTS;

        while (attemptsLeft > 0) {
            System.out.print("Entrez le mot de passe : ");
            String password = scanner.nextLine();

            if (password.isEmpty()) {
                System.out.println("Le mot de passe ne peut pas être vide. Veuillez réessayer.");
                continue;
            }

            if (Password.hashPassword(password).equals(storedHashedPassword)) {
                return true;
            } else {
                attemptsLeft--;
                System.out.println("Mot de passe incorrect. " + (attemptsLeft > 0 ? "Tentatives restantes : " + attemptsLeft : ""));
            }
        }
        return false;
    }

    /**
     * Charge une base de données utilisateur à partir d'un fichier CSV.
     * Le fichier CSV doit comporter deux colonnes : nom d'utilisateur et mot de passe haché.
     *
     * @param filename Le chemin vers le fichier CSV contenant les données utilisateur.
     * @return Une HashMap où les clés sont les noms d'utilisateur et les valeurs sont les mots de passe hachés.
     */
    public static HashMap<String, String> loadUserDatabase(String filename) {
        HashMap<String, String> userDatabase = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Ignorer l'en-tête
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    userDatabase.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement du fichier : " + e.getMessage());
        }
        return userDatabase;
    }
}
