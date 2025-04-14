import java.util.Scanner;

class Utils {
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

    public static void clearScreen(Scanner scanner) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
        System.out.println("------------------------------");
        System.out.println("| Screen cleared. Press Enter to continue...");
        scanner.nextLine();
    }
}