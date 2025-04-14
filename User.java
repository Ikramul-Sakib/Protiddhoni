import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Encapsulation: User class with private fields and public methods
class User {
    private String fullName;
    private String email;
    private String username;
    private String password;

    public User(String fullName, String email, String username, String password) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    public static boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && !email.contains(" ");
    }

    public static boolean isEmailExists(String email) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("login.txt"));
        for (String line : lines) {
            String[] parts = line.split(" ");
            if (parts.length >= 4 && parts[3].equals(email)) return true;
        }
        return false;
    }
}