package http.project.networks.ii.logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import java.time.LocalDateTime;

public class Logger {

    public static final int INFO = 0;
    public static final int WARNING = 1;
    public static final int ERROR = 2;

    private static final String USER_DIR_PROPERTY = "user.dir";
    private static final String DIRECTORY_PATH = "/app/src/main/java/http/project/networks/ii/logger/logs/";
    private static final String LOG_FILE_EXTENSION = ".log";

    private static String ANSI_RESET = "\u001B[0m";
    private static String ANSI_RED = "\u001B[31m";
    private static String ANSI_YELLOW = "\u001B[33m";

    private String logPath;
    private FileOutputStream fileOutputStream;
    private OutputStreamWriter outputStreamWriter;


    public Logger(String logPath) {
        this.logPath = logPath;
        try {
            this.fileOutputStream = new FileOutputStream(System.getProperty(USER_DIR_PROPERTY) + DIRECTORY_PATH + logPath + LocalDateTime.now().toString() + LOG_FILE_EXTENSION, true);
            this.outputStreamWriter = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void log(String message, int type) {
        try {
            if(type == ERROR) {
                outputStreamWriter.write(ANSI_RED + LocalDateTime.now().toString() + " - " + message + "\n" + ANSI_RESET);
                outputStreamWriter.flush();
            } else if (type == WARNING) {
                outputStreamWriter.write(ANSI_YELLOW + LocalDateTime.now().toString() + " - " + message + "\n"+ ANSI_RESET);
                outputStreamWriter.flush();
            } else {
                outputStreamWriter.write(LocalDateTime.now().toString() + " - " + message + "\n");
                outputStreamWriter.flush();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            outputStreamWriter.close();
            fileOutputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

}
