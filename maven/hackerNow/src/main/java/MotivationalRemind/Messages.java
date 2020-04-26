package MotivationalRemind;
import java.util.StringTokenizer;
import java.io.IOException;
import java.util.*;

public class Messages {
    String msg;
    String oneTime;
    
    public Messages(String body) {
        oneTime = body;
        msg = body.toLowerCase();
    }

    public void convo(String phoneNumber) throws IOException {
        String zeroInd = "0";
        database db = new database();
        try{
            String path = "INSERT_DB_PATH";
            HashMap<String, String[]> map = db.readFile(path);
            if(map.isEmpty() || !map.containsKey(phoneNumber)) {
                System.out.println("TESTSETT");
                intro(phoneNumber); 
            } else {
                String[] arr = map.get(phoneNumber);
                zeroInd = arr[0];
                System.out.println(zeroInd);
                switch(zeroInd) {
                    case "3":
                        intro(phoneNumber);
                        break;
                    case "0": 
                        yesVsNo(phoneNumber);
                        break;
                    case "1":
                        Assignment(phoneNumber);
                        break;
                    case "2":
                        DateAndTime(phoneNumber);
                        break;
                }
            }

            if(Integer.parseInt(zeroInd) == 1) {
                db.writeToFile(phoneNumber, oneTime);
            } else {
                db.writeToFile(phoneNumber, msg);
            }
        } catch(IOException e) {return;}

    }

    public void intro(String phoneNumber){
        receiveSMS forIntro = new receiveSMS();
        // After triggering the bot, it will greet you then ask you whether you want to add a reminder for an assignment
            String textForIntro = "Hello, I'm Motomind and I hope you've been staying safe today. Would you like to create a reminder today? Please reply with: Yes/No";
            forIntro.sendText(textForIntro, phoneNumber);
    }
    public boolean yesVsNo(String phoneNumber){
        receiveSMS yesNo = new receiveSMS();
        try{
            String[] yesOrNo = msg.split("\\s");
            // If response is yes:
            if(yesOrNo[0].equals("yes")) {
                // Ask for the name of the assignment
                String yesAnswer = "Great! What is the name of the assignment?";
                yesNo.sendText(yesAnswer, phoneNumber);
                return true; 
            } else if(yesOrNo[0].equals("no")){
                String noAnswer = "Okay, I'm always one text away if you want me to create a reminder for you. Please text me again if you want to reset me!";
                yesNo.sendText(noAnswer, phoneNumber);
                yesNo.sendText("Also, here's a quote to keep you motivated, " + Quotes.inspirationalMessage(), phoneNumber);
                return false; 
            }
            else{
                yesNo.sendText("I'm sorry but I think you may have inputted words that I have not learned yet. Please message me again to reset me!", phoneNumber);
            }
        } catch(Exception e){
            yesNo.sendText("I'm sorry but I think you may have inputted words that I have not learned yet. Please message me again to reset me!", phoneNumber);
        }
        return false;
    }
    public void Assignment(String phoneNumber){
        receiveSMS forAssignment = new receiveSMS();
        // nameOfAssignment string will carry the name of assignment
        String nameOfAssignment = oneTime;
        // After having the name of the assignment, 
        String textForAssignment =  "Got it! When do you have to do: \"" + nameOfAssignment + "\" by? Please say the date and time in this exact format: mm/dd/yy @ time. (Example: 05/25/20 @ 6:00pm).";
        forAssignment.sendText(textForAssignment, phoneNumber);
    }
    public long DateAndTime(String phoneNumber) throws IOException {
        receiveSMS dateTime = new receiveSMS();
        msg = msg.replaceAll("\\s", "");
        String send = "";
        String error = "I'm sorry but I think you may have inputted the date with an incorrect format. The correct format is: mm/dd/yy @ time. (Example: 05/25/20 @ 6:00pm). Please text me again to reset me!";
        long dueSeconds = -1;
        // BEFORE DOING ANYTHING DELETE SPACES FROM THE STRING SO IT WILL LOOK LIKE THIS mm/dd/yy@time
        try{
            String dateAndTime = msg;
            StringTokenizer str = new StringTokenizer(dateAndTime, "/"); // 06/25/20@6:00pm --> 06 --> 25 --> 20@6:00pm
            int i = 0;
            String month = "", day = "", year = ""; // 06 --> 25 --> 20@6:00pm
            while(str.hasMoreTokens()){
                
                String s = str.nextToken();
                if(i == 0){
                    month = s;
                    i++;
                    continue;
                }
                if(i == 1){
                    day = s;
                    i++;
                    continue;
                }
                year = dateAndTime.substring(6, 8);
                break;
            }
            dateAndTime = dateAndTime.substring(dateAndTime.indexOf("@") + 1); // 12:00pm or 6:00pm 
            StringTokenizer st = new StringTokenizer(dateAndTime, ":"); //12:00pm
            int j = 0;
            String hour = "", minute = "";
            String amOrPm = ""; 
            while(st.hasMoreTokens()){ //12 --> 00pm
                String ss = st.nextToken();
                if(j == 0){
                    hour = ss;
                    j++;
                    continue;
                }
                if(j == 1){
                    minute = ss.substring(0, 2);
                    amOrPm = ss.substring(2).toLowerCase();
                    continue;
                }
            }
            send = "Okay, your reminder has been set with a due date of: " + month + "/" + day + "/" + year + " @ " + hour + ":" + minute + amOrPm + ". Please text me again if you want to set another reminder!";
            if(amOrPm.equals("pm")) {
                hour = Integer.toString(Integer.parseInt(hour) + 12);
            }
            Date due = new Date(Integer.parseInt("20" + year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
            dueSeconds = due.getTime(); 
        } catch(Exception e){
           send = error;
        }
        dateTime.sendText(send, phoneNumber);
        if(!send.equals(error)) {
            Date current = new Date(); 
            long currentSeconds = current.getTime(); 
            database db = new database();
            HashMap<String, String[]> map;
            map = db.readFile("INSERT_DB_PATH");
            Reminder rem = new Reminder(Integer.parseInt(Long.toString(dueSeconds-currentSeconds)), phoneNumber, map.get(phoneNumber)[2]);
            rem.newReminder();
        }
        else{
            return 0; 
        }
        return dueSeconds; //If invalid then will be -1
    }
}