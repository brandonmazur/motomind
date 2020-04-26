package MotivationalRemind;

import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;

import spark.Request;

import static spark.Spark.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Set;
import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.type.PhoneNumber;

public class receiveSMS {
    public static final String ACCOUNT_SID = "INSERT_YOUR_ACCOUNT_SSID";
    public static final String AUTH_TOKEN = "INSERT_AUTH_TOKEN";

    public static String getParameter(String body, String param) {
        for (String s : body.split("&")) {
            String[] kv = s.split("=");
            if(kv[0].equals(param)) return URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
        }
        return "";
    }
    public void sendText(String text, String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator (
                new com.twilio.type.PhoneNumber(phoneNumber), // <-- the recipient's number
                new com.twilio.type.PhoneNumber("YourPhoneNumber"), // <-- the sender's number (static information)
                text)
            .create();
    }
    public static void sendTextStatic(String text, String phoneNumber) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator (
                new com.twilio.type.PhoneNumber(phoneNumber), // <-- the recipient's number
                new com.twilio.type.PhoneNumber("YourPhoneNumber"), // <-- the sender's number (static information)
                text)
            .create();
    }
 
    public static void main(String[] args) {
        get("/", (req, res) -> "Hello World");
        post("/sms", (req, res) -> {
            res.type("application/xml");
            String rawBody = req.body();
            System.out.println(rawBody);
            String modBody = getParameter(rawBody, "Body"); // Pass it in to be parsed
            String phoneNumber = getParameter(rawBody, "From");
            Messages text = new Messages(modBody);
            database ex = new database();
            text.convo(phoneNumber); 
            String newMessage = text.msg; 
            Body body = new Body // Maybe remove?
                    .Builder("")
                    .build();
            Message sms = new Message
                    .Builder()
                    .body(body)
                    .build();
            MessagingResponse twiml = new MessagingResponse
                    .Builder()
                    .message(sms)
                    .build();
                    
            return twiml.toXml();

        });
    }
}