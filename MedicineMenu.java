import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class MedicineMenu extends Menu {
    private List<Medicine> medicines = new ArrayList<>();

    public MedicineMenu() {
        loadMedicines();
    }

    @Override
    void displayMenu() {
        System.out.println("\n\t\t\t ------------------  Medicine Menu  ------------------\n");
        System.out.println("| 1. Add Medicine\n| 2. Show Medicine Chart\n| 3. Mark Medicine as Taken\n| 4. Exit");
        System.out.print("| Enter your choice: ");
    }

    @Override
    void handleChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch (choice) {
            case 1: addMedicine(); break;
            case 2: showChart(); break;
            case 3: markAsTaken(); break;
            case 4: System.out.println("| Exiting program. Stay healthy!"); clearScreen(); return;
            default: System.out.println("\033[1;31m| Invalid choice! Please try again.\033[0m"); clearScreen(); break;
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

    private void addMedicine() {
        if (medicines.size() >= 100) {
            System.out.println("\033[1;31m| Medicine chart is full!\033[0m");
            clearScreen();
            return;
        }
        System.out.print("| Enter Medicine Name: "); String name = scanner.nextLine();
        System.out.print("| Enter Dosage (e.g., 500mg): "); String dosage = scanner.nextLine();
        System.out.print("| Enter Frequency (e.g., Morning): "); String frequency = scanner.nextLine();
        System.out.println("| Should the medicine be taken-\n| 1. Before Meals\n| 2. After Meals");
        System.out.print("| Enter your choice (1 or 2): "); int mealChoice = scanner.nextInt(); scanner.nextLine();
        String mealTime = mealChoice == 1 ? "Before Meals" : "After Meals";
        System.out.print("| Enter Start Date (dd-mm-yyyy): "); String startDate = scanner.nextLine();
        System.out.print("| Enter Duration (in days): "); int duration = scanner.nextInt(); scanner.nextLine();
        if (duration <= 0) {
            System.out.println("\033[1;31m| Invalid duration!\033[0m");
            clearScreen();
            return;
        }
        medicines.add(new Medicine(name, dosage, frequency, mealTime, startDate, duration));
        saveMedicines();
        System.out.println("| Medicine added successfully!");
        clearScreen();
    }

    private void showChart() {
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-10s | %-15s | %-15s | %-10s | %-8s | %-6s | %-10s |\n",
                "Name", "Dosage", "Frequency", "Meal Time", "Start Date", "Days Left", "Taken", "Status");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        for (Medicine med : medicines) System.out.println(med);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------");
        clearScreen();
    }

    private void markAsTaken() {
        System.out.print("| Enter the name of the medicine to mark as taken: ");
        String name = scanner.nextLine();
        for (Medicine med : medicines) {
            if (med.getName().equals(name)) {
                med.markTaken();
                saveMedicines();
                System.out.println("| Marked '" + name + "' as taken. Days left: " + med.getDaysLeft());
                clearScreen();
                return;
            }
        }
        System.out.println("\033[1;31m| Medicine not found!\033[0m");
        clearScreen();
    }

    private void loadMedicines() {
        try (BufferedReader reader = new BufferedReader(new FileReader("medicine.txt"))) {
            int count = Integer.parseInt(reader.readLine());
            for (int i = 0; i < count; i++) {
                String[] parts = reader.readLine().split(",");
                medicines.add(new Medicine(parts[0], parts[1], parts[2], parts[3], parts[4], Integer.parseInt(parts[5])));
            }
        } catch (IOException | NumberFormatException e) {
            // Ignore if file doesn't exist or is empty
        }
    }

    private void saveMedicines() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("medicine.txt"))) {
            writer.write(medicines.size() + "\n");
            for (Medicine med : medicines) writer.write(med.toFileString() + "\n");
        } catch (IOException e) {
            System.out.println("| Error saving medicines.");
        }
    }

    public void run() {
        while (true) {
            displayMenu();
            handleChoice();
        }
    }
}