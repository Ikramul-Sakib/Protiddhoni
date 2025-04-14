import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class MainMenu extends Menu {
    private User currentUser;

    public MainMenu(User currentUser) {
        this.currentUser = currentUser;
    }

    void displayMenu() {
        System.out.println("\n\t\t\t ------------------  Main Menu  ------------------\n");
        System.out.println("| Welcome, Sir...");
        System.out.println("| 1. Appointment\n| 2. Note\n| 3. Medicine\n| 4. Emergency\n| 5. Log out");
        System.out.print("| Enter your choice: ");
    }


    void handleChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch (choice) {
            case 1: new AppointmentMenu().run(); break;
            case 2: new NoteMenu().run(); break;
            case 3: new MedicineMenu().run(); break;
            case 4: new EmergencyMenu().run(); break;
            case 5: System.out.println("| Log Out from the program. Goodbye!"); clearScreen(); new Protiddhoni().run(); break;
            default: System.out.println("\033[1;31m| Invalid choice. Try again.\033[0m"); clearScreen(); break;
        }
    }

    private void clearScreen() {
        // Print 50 blank lines to simulate clearing the screen
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("------------------------------");
        System.out.println("| Screen cleared. Press Enter to continue...");
        scanner.nextLine(); // Assuming 'scanner' is a Scanner object defined elsewhere
    }


    public void run() {
        while (true) {
            displayMenu();
            handleChoice();
        }
    }
}