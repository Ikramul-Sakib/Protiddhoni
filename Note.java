import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Note {
    private String date, content;

    public Note(String date, String content) {
        this.date = date;
        this.content = content;
    }

    public String getDate() { return date; }
    public String getContent() { return content; }
    public String toFileString() { return date + " " + content; }
}