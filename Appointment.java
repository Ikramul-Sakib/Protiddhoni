import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
class Appointment {
    private String patientName, age, gender, phone, doctorName, date, time, reason, paymentMethod;
    private int isCompleted;

    public Appointment(String patientName, String age, String gender, String phone, String doctorName,
                       String date, String time, String reason, String paymentMethod, int isCompleted) {
        this.patientName = patientName;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
        this.doctorName = doctorName;
        this.date = date;
        this.time = time;
        this.reason = reason;
        this.paymentMethod = paymentMethod;
        this.isCompleted = isCompleted;
    }

    // Getters and setters
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getReason() { return reason; }
    public int getIsCompleted() { return isCompleted; }
    public void setIsCompleted(int isCompleted) { this.isCompleted = isCompleted; }

    public String toFileString() {
        return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%d",
                patientName, age, gender, phone, doctorName, date, time, reason, paymentMethod, isCompleted);
    }
}