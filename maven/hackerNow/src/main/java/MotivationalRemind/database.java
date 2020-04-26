package MotivationalRemind;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors
import java.util.HashMap;

public class database {
    public HashMap<String, String[]> readFile(String path) throws IOException {
        //String body = file.
        HashMap<String, String[]> hash = new HashMap<String, String[]>();
        try {
            String filePath = path;
        
            String line;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while ((line = reader.readLine()) != null)
            {
                String[] parts = line.split("=", 2);
                if (parts.length >= 2)
                {
                    String key = parts[0];
                    String[] val = parts[1].split(";");
                    for(int i = 0; i < val.length; i++) {
                        System.out.println(key + ": " + val[i]);
                    }
                    hash.put(key, val);
                } else {
                    System.out.println("ignoring line: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {}
        return hash;
    }

    public void writeToFile(String phoneNumber, String body) {
        try {
            String path = "INSERT_DATABASE_PATH";
            HashMap<String, String[]> hash = readFile(path);
            FileWriter myWriter = new FileWriter(path);
            File file = new File(path);
            if(hash.isEmpty() || !hash.containsKey(phoneNumber)) { //If the phone number is new, lets add it! Lets add: Int representing where in the process they are (0-Welcome, 1-Yes/No 2-Name of Assignment, 3-Time of assignment), Name of assignment, time of assignment
                String[] temp = {"0", "-1", "-1", "-1"};
                hash.put(phoneNumber, temp);
            } else { // What needs to happen?
                String[] inf = hash.get(phoneNumber);
                if(Integer.parseInt(inf[0]) == 0) {
                    inf[0] = "1";
                    if(body.toLowerCase().equals("yes")) inf[1] = "1";
                    else hash.remove(phoneNumber);
                } else if(Integer.parseInt(inf[0]) == 1) {
                    inf[0] = "2";
                    inf[2] = body;
                } else if(Integer.parseInt(inf[0]) == 2) {
                    inf[0] = "3";
                    inf[3] = body;
                } else {
                    hash.remove(phoneNumber);
                    String[] temp = {"0", "-1", "-1", "-1"};
                    hash.put(phoneNumber, temp);
                }
            } 
            // Now, read the hashmap to the file
            String line = "";
            for(String word : hash.keySet()) {
                line += word + "=";
                String[] arr = hash.get(word);
                for(String str : arr) {
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
        }
    }
}