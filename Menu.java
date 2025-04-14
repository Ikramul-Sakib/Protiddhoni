import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Abstract class demonstrating Abstraction
abstract class Menu {
    protected Scanner scanner = new Scanner(System.in);
    abstract void displayMenu();
    abstract void handleChoice();
}