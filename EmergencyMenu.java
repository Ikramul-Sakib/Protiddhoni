import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class EmergencyMenu extends Menu {

    void displayMenu() {
        System.out.println("\n\t\t\t ------------------  Emergency Menu  ------------------\n");
        System.out.println("\t\t\t<<<<<<<<< Welcome Sir! Let me know how I can assist you? >>>>>>>>>");
        System.out.println("| 1. Add Emergency Information\n| 2. View Emergency Information\n| 3. Back");
        System.out.print("| Choose an option: ");
    }

    void handleChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch (choice) {
            case 1: addInfo(); break;
            case 2: displayEmergency(); break;
            case 3: clearScreen(); return;
            default: System.out.println("\033[1;31m| Invalid option. Try again.\033[0m"); clearScreen(); break;
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

    private void addInfo() {
        System.out.print("| Full Name: "); String name = scanner.nextLine();
        System.out.print("| Home Address: "); String address = scanner.nextLine();
        System.out.print("| Phone Number (11 digits): "); String phone = scanner.nextLine();
        if (phone.length() == 11 && phone.matches("\\d+")) {
            phone = "+88" + phone;
            Emergency emergency = new Emergency(name, address, phone);
            if (Utils.confirm(scanner,"Emergency details", "Emergency details added")) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("emergency.txt"))) {
                    writer.write(emergency.toFileString());
                } catch (IOException e) {
                    System.out.println("| Error saving emergency info.");
                }
            }
        } else {
            System.out.println("| Invalid phone number. Must be 11 digits.");
        }
        clearScreen();
    }

    private void displayEmergency() {
        try (BufferedReader reader = new BufferedReader(new FileReader("emergency.txt"))) {
            String name = reader.readLine(), address = reader.readLine(), phone = reader.readLine();
            if (name != null && address != null && phone != null) {
                Emergency emergency = new Emergency(name, address, phone);
                System.out.println(emergency);
            } else {
                System.out.println("\033[1;31m| No emergency information available.\033[0m");
            }
        } catch (IOException e) {
            System.out.println("\033[1;31m| No emergency information available.\033[0m");
        }
        clearScreen();
    }

    public void run() {
        while (true) {
            displayMenu();
            handleChoice();
        }
    }
}