package http.project.networks.ii.logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * A logger for the application
 */
public class Logger {

    public static final int INFO = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;

    private static final String DATE_FORMAT = "yyyy-MM-dd_HH_mm_ss";
    private static final String USER_DIR_PROPERTY = "user.dir";
    private static final String DIRECTORY_PATH = Paths.get(System.getProperty(USER_DIR_PROPERTY), "app", "src", "main", "java", "http", "project", "networks", "ii", "logger", "logs").toString();
    private static final String LOG_FILE_EXTENSION = ".txt";

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private String logName;
    private SimpleDateFormat simpleDateFormat;
    String dateText;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;


    /**
     * Create a new Logger
     * @param logName The path to the log file
     */
    public Logger(String logName) {
        this.logName = logName;
        simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateText = simpleDateFormat.format(new Date());
        try {
            this.fileOutputStream = new FileOutputStream(DIRECTORY_PATH + "/" + logName + dateText + LOG_FILE_EXTENSION, true);
            this.outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log(e.getMessage(), ERROR);
        }
    }

    /**
     * Log a message
     * @param message The message
     * @param type The type of the message
     */
    public void log(String message, int type) {
        try {
            if(type == ERROR) {
                outputStreamWriter.write(ANSI_RED + LocalDateTime.now().toString() + "-" + message + "\n" + ANSI_RESET);
                outputStreamWriter.flush();
            } else if (type == WARNING) {
                outputStreamWriter.write(ANSI_YELLOW + LocalDateTime.now().toString() + "-" + message + "\n"+ ANSI_RESET);
                outputStreamWriter.flush();
            } else {
                outputStreamWriter.write(LocalDateTime.now().toString() + "-" + message + "\n");
                outputStreamWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            log(e.getMessage(), ERROR);
        }
    }

    /**
     * Close the logger
     */
    public void close() {
        try {
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            log(e.getMessage(), ERROR);
        }
    }

    /**
     * Clear the log
     */
    public void clear() {
        try {
            this.fileOutputStream = new FileOutputStream(System.getProperty(USER_DIR_PROPERTY) + DIRECTORY_PATH + logName + dateText + LOG_FILE_EXTENSION);
            this.outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log(e.getMessage(), ERROR);
        }
    }

    /**
     * Get the path to the log file
     * @return The path to the log file
     */
    public String getLogName() {
        return logName;
    }

    /**
     * Set the path to the log file
     * @param logPath The path to the log file
     */
    public void setLogName(String logPath) {
        this.logName = logPath;
    }

}
