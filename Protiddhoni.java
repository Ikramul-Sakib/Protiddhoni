import java.io.*;
import java.util.*;

public class Protiddhoni {
    private Scanner sc = new Scanner(System.in);

    public void run() {
        while (true) {
            showTitle();
            int choice = sc.nextInt();
            sc.nextLine();
            clearScreen();
            switch (choice) {
                case 1:
                    if (showLogin()) new MainMenu(loginUser()).run();
                    else {
                        System.out.println("\033[1;31m| Invalid login. Try again or sign up.\033[0m");
                        clearScreen();
                    }
                    break;
                case 2: showRegister(); break;
                case 3: System.out.println("\033[1;31m| Exiting program...\033[0m"); System.exit(0);
                default: System.out.println("\033[1;31m| Invalid option. Try again.\033[0m"); clearScreen(); break;
            }
        }
    }

    private void showTitle() {
        System.out.println("\n\t\t\t -------------------------- ");
        System.out.println("\t\t\t|       PROTIDDHONI        |");
        System.out.println("\t\t\t -------------------------- \n");
        System.out.println("| 1. Log in\n| 2. Sign up\n| 3. Exit");
        System.out.print("| Choose an option: ");
    }

    private void showRegister() {
        System.out.println("\n\t\t\t--- Sign Up Screen ---\n");
        System.out.print("| Enter your full name: "); String name = sc.nextLine();
        String email;
        while (true) {
            System.out.print("| Enter your email address: "); email = sc.nextLine();
            if (!User.isValidEmail(email)) {
                System.out.println("\033[1;31m| Invalid email format.\033[0m");
                continue;
            }
            try {
                if (User.isEmailExists(email)) {
                    System.out.println("\033[1;31m| Email already registered.\033[0m");
                    continue;
                }
            } catch (IOException e) {
                System.out.println("| Error checking email.");
            }
            break;
        }
        String username = email.split("@")[0];
        System.out.println("| Your username: " + username);
        System.out.print("| Enter password (at least 5 characters): "); String password = sc.nextLine();
        if (password.length() < 5) {
            System.out.println("\033[1;31m| Password too short.\033[0m");
            clearScreen();
            return;
        }
        User user = new User(name, email, username, password);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("login.txt", true))) {
            writer.write(String.format("%s %s %s %s\n", username, password, name, email));
            System.out.println("| Sign up successful !!");
        } catch (IOException e) {
            System.out.println("| Error during signup.");
        }
        clearScreen();
    }

    private boolean showLogin() {
        System.out.println("\n\t\t\t--- Login Screen ---\n");
        System.out.print("| Enter username: "); String username = sc.nextLine();
        System.out.print("| Enter password: "); String password = sc.nextLine();
        try (BufferedReader reader = new BufferedReader(new FileReader("login.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 4 && parts[0].equals(username) && parts[1].equals(password)) {
                    System.out.println("| Log in successful !!");
                    clearScreen();
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("| Error during login.");
        }
        return false;
    }

    private User loginUser() {
        // Simplified for demo; in practice, return the logged-in user
        return new User("User", "user@example.com", "user", "pass");
    }

    private boolean confirm(String msg, String msg1) {
        System.out.print("| Confirm " + msg + "? (y/n): ");
        String choice = sc.nextLine();
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("\033[1;31m| " + msg + " not saved.\033[0m");
            return false;
        }
        System.out.println("| " + msg1 + " successfully!!");
        return true;
    }

    private void clearScreen() {
        System.out.println("\n------------------------------\n| Press Enter to continue...");
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main(String[] args) {
        new Protiddhoni().run();
    }
}