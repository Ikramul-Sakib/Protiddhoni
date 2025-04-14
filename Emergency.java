import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Emergency {
    private String fullName, address, phoneNumber;

    public Emergency(String fullName, String address, String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String toFileString() {
        return fullName + "\n" + address + "\n" + phoneNumber;
    }

    public String toString() {
        return String.format("Full Name: %s\nHome Address: %s\nPhone Number: %s",
                fullName, address, phoneNumber);
    }
}