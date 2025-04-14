import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class NoteMenu extends Menu {

    void displayMenu() {
        System.out.println("\n\t\t\t ------------------  Note Menu  ------------------\n");
        System.out.println("| 1. Add Note\n| 2. View Notes\n| 3. Delete Note by Date\n| 4. Back");
        System.out.print("| Enter your choice: ");
    }


    void handleChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch (choice) {
            case 1: addNote(); break;
            case 2: viewNotes(); break;
            case 3: deleteNote(); break;
            case 4: System.out.println("| Returning to Main Menu..."); clearScreen(); return;
            default: System.out.println("\033[1;31m| Invalid choice. Try again.\033[0m"); clearScreen(); break;
        }
    }

    private void addNote() {
        System.out.print("| Enter the date for the note (dd-mm-yyyy): ");
        String date = scanner.nextLine();
        System.out.print("| Write your note: ");
        String content = scanner.nextLine();
        Note note = new Note(date, content);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("note.txt", true))) {
            writer.write(note.toFileString() + "\n");
            System.out.println("| Note saved successfully!!");
        } catch (IOException e) {
            System.out.println("| Error saving note.");
        }
        clearScreen();
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

    private void viewNotes() {
        try (BufferedReader reader = new BufferedReader(new FileReader("note.txt"))) {
            String line;
            boolean found = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    System.out.println("| " + parts[0] + " :\t" + parts[1]);
                    found = true;
                }
            }
            if (!found) System.out.println("\033[1;31m| No notes written yet.\033[0m");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        clearScreen();
    }

    private void deleteNote() {
        System.out.print("| Enter the date of the note to delete (dd-mm-yyyy): ");
        String date = scanner.nextLine();
        List<Note> notes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("note.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) notes.add(new Note(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("| Error reading notes.");
        }
        notes.removeIf(note -> note.getDate().equals(date));
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("note.txt"))) {
            for (Note note : notes) writer.write(note.toFileString() + "\n");
            System.out.println("| Note deleted successfully!!");
        } catch (IOException e) {
            System.out.println("| Error saving notes.");
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