package MotivationalRemind;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import io.jsonwebtoken.io.IOException;

/**
 * Simple demo that uses java.util.Timer to schedule a task to execute once 5
 * seconds have passed.
 */

public class Reminder {
    Timer timer;
    final String path = "INSERT_REMINDER_PATH";
    long milliseconds;
    String phoneNumber, assignment;
    public Reminder(int milliseconds, String phoneNumber, String assignment) {
        this.milliseconds = milliseconds;
        this.phoneNumber = phoneNumber;
        this.assignment = assignment;
        this.timer = timer;
        if(milliseconds < 0) return;
        timer = new Timer();
        timer.schedule(new RemindTask(phoneNumber, assignment, milliseconds), milliseconds);
        System.out.println("Scheduled the remind...");
    }
    class RemindTask extends TimerTask {
        String phoneNumber;
        String assignment;
        long milliseconds;
        public RemindTask(String phoneNumber, String assignment, long milliseconds) {
            this.phoneNumber = phoneNumber;
            this.milliseconds = milliseconds;
            this.assignment = assignment;
		}

		public void run() {
            try {
                // Send the message!
                Date current = new Date(); 
                long currentSeconds = current.getTime(); 
                if(!phoneNumber.equals("")) {
                    receiveSMS sms = new receiveSMS();
                    System.out.println("A reminder went off!");
                    sms.sendText("This is your reminder to do your assignment: \"" + assignment + "\"!", phoneNumber);
                    sms.sendText("Also, here's a quote to keep you motivated, " + Quotes.inspirationalMessage(), phoneNumber);
                    ArrayList<String[]> arr = readFile(path);
                    ArrayList<String[]> newArr = new ArrayList<String[]>();
                    newArr.addAll(arr);
                    for(String[] sub : arr) {
                        if(Long.parseLong(sub[1]) == milliseconds || milliseconds - currentSeconds < 0) {
                            newArr.remove(newArr.indexOf(sub));
                        }
                    }
                    arr = newArr;
                    // Terminate the timer thread
                    // Now, read the hashmap to the file
                    String line = "";
                    for(String[] word : arr) {
                        for(String str : word) {
                            line += str + ";";
                            }
                            line += "\n";
                        }
                        FileWriter myWriter = new FileWriter(path);
                        myWriter.write(line);
                        System.out.println("Successfully wrote to the file.");
                        myWriter.close();
                    }
            timer.cancel();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rehashReminders() {
        // File path to reminder database
        Date current = new Date(); 
        long currentSeconds = current.getTime(); 
        try {
            ArrayList<String[]> arr = readFile(path);
            for(String[] word : arr) {
                if((Long.parseLong(word[1]))-currentSeconds > 0) {
                    new Reminder(Integer.parseInt(Long.toString(Long.parseLong(word[1])-currentSeconds)), word[0], word[2]);
                }
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void newReminder() {
        try {
            Date current = new Date(); 
            long currentSeconds = current.getTime(); 
            ArrayList<String[]> arr = readFile(path);

            String[] temp = {phoneNumber, Long.toString(milliseconds+currentSeconds), assignment};
            arr.add(temp);

            String line = "";
            FileWriter myWriter = new FileWriter(path);
            // Now, read the hashmap to the file
            for(String[] word : arr) {
                for(String str : word) {
                    line += str + ";";
                }
                line += "\n";
            }
            myWriter.write(line);
            System.out.println("Successfully wrote to the file.");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<String[]> readFile(String path) throws IOException, java.io.IOException {
        //String body = file.
        ArrayList<String[]> arr = new ArrayList<String[]>();
        try {
            String filePath = path;
        
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split(";");
                arr.add(parts);
            }
            reader.close();
        } catch (IOException e) {}
        return arr;
    }
}