import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class AppointmentMenu extends Menu {
    @Override
    void displayMenu() {
        System.out.println("\n\t\t\t ------------------  Appointment Menu  ------------------\n");
        System.out.println("| 1. Add Appointment\n| 2. View Appointment\n| 3. Mark as Completed\n| 4. Cancel Appointment\n| 5. Back");
        System.out.print("| Enter your choice: ");
    }

    @Override
    void handleChoice() {
        int choice = scanner.nextInt();
        scanner.nextLine();
        clearScreen();
        switch (choice) {
            case 1: addAppointment(); break;
            case 2: displayAppointment(); break;
            case 3: updateAppointmentStatus("mark as completed", 1); break;
            case 4: updateAppointmentStatus("cancel the appointment", -1); break;
            case 5: System.out.println("| Returning to Main Menu..."); clearScreen(); return;
            default: System.out.println("\033[1;31m| Invalid choice. Try again.\033[0m"); clearScreen(); break;
        }
    }

    private void addAppointment() {
        System.out.println("\n\t\t\t ------------------  Doctor's Regular Checkup Appointment Form  ------------------\n");
        System.out.print("| Full Name: "); String name = scanner.nextLine();
        System.out.print("| Age: "); String age = scanner.nextLine();
        System.out.println("| Gender: \n| 1. Male\n| 2. Female\n| Enter your choice (1 or 2): ");
        int genderChoice = scanner.nextInt(); scanner.nextLine();
        String gender = genderChoice == 1 ? "Male" : "Female";
        System.out.print("| Phone Number: "); String phone = scanner.nextLine();
        System.out.print("| Doctor Name (Neurologist): "); String doctor = scanner.nextLine();
        System.out.print("| Preferred Date (dd-mm-yyyy): "); String date = scanner.nextLine();
        System.out.print("| Preferred Time (hh:mm am/pm): "); String time = scanner.nextLine();
        System.out.print("| Reason for Checkup: "); String reason = scanner.nextLine();
        System.out.println("| Payment Method: \n| 1. Cash\n| 2. Card\n| 3. Online Banking\n| 4. Insurance");
        System.out.print("| Enter your choice: "); int payChoice = scanner.nextInt(); scanner.nextLine();
        String payment = payChoice == 1 ? "Cash" : payChoice == 2 ? "Card" : payChoice == 3 ? "Online Banking" : "Insurance";

        Appointment appt = new Appointment(name, age, gender, phone, doctor, date, time, reason, payment, 0);
        if (Utils.confirm(scanner,"Appointment details", "Appointment registration")) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointment.txt", true))) {
                writer.write(appt.toFileString() + "\n");
                System.out.println("| Appointment registration successfully!!");
            } catch (IOException e) {
                System.out.println("| Error saving appointment.");
            }
        }
        clearScreen();
    }

    public static boolean confirm(Scanner scanner, String msg, String msg1) {
        System.out.print("| Confirm " + msg + "? (y/n): ");
        String choice = scanner.nextLine();
        if (!choice.equalsIgnoreCase("y")) {
            System.out.println("\033[1;31m| " + msg + " not saved.\033[0m");
            return false;
        }
        System.out.println("| " + msg1 + " successfully!!");
        return true;
    }

    private void displayAppointment() {
        System.out.print("| Enter the patient's full name to view appointments: ");
        String name = scanner.nextLine();
        List<Appointment> appointments = readAppointments(name);
        if (appointments.isEmpty()) {
            System.out.println("\033[1;31m| No appointments found for patient: " + name + "\033[0m");
            clearScreen();
            return;
        }
        displayAppointmentList(appointments);
        System.out.print("| Enter the number of the appointment to view details: ");
        int choice = scanner.nextInt(); scanner.nextLine();
        clearScreen();
        if (choice < 1 || choice > appointments.size()) {
            System.out.println("\033[1;31m| Invalid choice.\033[0m");
            clearScreen();
            return;
        }
        Appointment selected = appointments.get(choice - 1);
        System.out.println("\n\t\t           ___________________Patient Information___________________\n");
        System.out.println("| Full Name: " + selected.getPatientName());
        System.out.println("| Age: " + selected.getPatientName());
        System.out.println("| Gender: " + selected.getPatientName());
        System.out.println("| Phone Number: " + selected.getPatientName());
        System.out.println("\n\t\t          ___________________Appointment Details___________________\n");
        System.out.println("| Doctor Name: " + selected.getDoctorName());
        System.out.println("| Date: " + selected.getDate());
        System.out.println("| Time: " + selected.getTime());
        System.out.println("| Reason: " + selected.getReason());
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

    private void updateAppointmentStatus(String statusMessage, int statusValue) {
        System.out.print("| Enter the patient's full name to " + statusMessage + ": ");
        String name = scanner.nextLine();
        List<Appointment> appointments = readAppointments(name);
        if (appointments.isEmpty()) {
            System.out.println("\033[1;31m| No appointments found for " + name + ".\033[0m");
            clearScreen();
            return;
        }
        displayAppointmentList(appointments);
        System.out.print("| Select the appointment number to " + statusMessage + ": ");
        int choice = scanner.nextInt(); scanner.nextLine();
        if (choice < 1 || choice > appointments.size()) {
            System.out.println("\033[1;31m| Invalid choice.\033[0m");
            clearScreen();
            return;
        }
        Appointment selected = appointments.get(choice - 1);
        selected.setIsCompleted(statusValue);
        saveAppointments(appointments);
        System.out.println("| Appointment " + statusMessage + " successfully!");
        clearScreen();
    }

    private List<Appointment> readAppointments(String name) {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("appointment.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String patientName = line;
                String age = reader.readLine();
                String gender = reader.readLine();
                String phone = reader.readLine();
                String doctorName = reader.readLine();
                String date = reader.readLine();
                String time = reader.readLine();
                String reason = reader.readLine();
                String payment = reader.readLine();
                int isCompleted = Integer.parseInt(reader.readLine());
                if (patientName.equals(name)) {
                    appointments.add(new Appointment(patientName, age, gender, phone, doctorName, date, time, reason, payment, isCompleted));
                }
            }
        } catch (IOException e) {
            System.out.println("| Error reading appointments.");
        }
        return appointments;
    }

    private void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("appointment.txt"))) {
            for (Appointment appt : appointments) {
                writer.write(appt.toFileString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("| Error saving appointments.");
        }
    }

    private void displayAppointmentList(List<Appointment> appointments) {
        System.out.println("\n| Found " + appointments.size() + " appointment(s):");
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appt = appointments.get(i);
            System.out.printf("| Appointment %d:\n| Date: %s, Time: %s, Doctor: %s, Reason: %s\n",
                    i + 1, appt.getDate(), appt.getTime(), appt.getDoctorName(), appt.getReason());
        }
    }

    public void run() {
        while (true) {
            displayMenu();
            handleChoice();
        }
    }
}