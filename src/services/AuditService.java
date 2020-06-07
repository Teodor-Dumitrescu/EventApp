package services;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Record all major events in application workflow like adding client/organizer/ticket/event, update or remove them.
 */
public class AuditService {

    private static AuditService auditServiceInstance = null;

    private AuditService() {}

    public static AuditService getAuditServiceInstance() {

        if(auditServiceInstance == null){
            auditServiceInstance = new AuditService();
        }

        return auditServiceInstance;
    }

    /**
     * Will write a message in a single log file putting a time stamp for each operation registered.
     *
     * @param message message to be logged
     */
    public void addLogMessage(String message){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        String logFile = "src/logs.csv";

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(logFile,true))) {
                bufferedWriter.write(message + "," + formatter.format(date));
                bufferedWriter.newLine();
        } catch (IOException e) {
            System.out.println("Could not write data to file: " + e.getMessage());
        }
    }

}
