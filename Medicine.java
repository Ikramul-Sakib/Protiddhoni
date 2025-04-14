import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Medicine {
    private String name, dosage, frequency, mealTime, startDate;
    private int duration, daysLeft, taken;

    public Medicine(String name, String dosage, String frequency, String mealTime, String startDate, int duration) {
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
        this.mealTime = mealTime;
        this.startDate = startDate;
        this.duration = duration;
        this.daysLeft = duration;
        this.taken = 0;
    }

    // Getters and setters
    public String getName() { return name; }
    public int getDaysLeft() { return daysLeft; }
    public int getTaken() { return taken; }
    public void markTaken() { if (daysLeft > 0) { taken++; daysLeft--; } }

    public String toFileString() {
        return String.format("%s,%s,%s,%s,%s,%d,%d,%d",
                name, dosage, frequency, mealTime, startDate, duration, daysLeft, taken);
    }

    public String toString() {
        String status = daysLeft > 0 ? "Ongoing" : "Complete";
        return String.format("| %-20s | %-10s | %-15s | %-15s | %-10s | %-8d | %-6d | %-10s |",
                name, dosage, frequency, mealTime, startDate, daysLeft, taken, status);
    }
}