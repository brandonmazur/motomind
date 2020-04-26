package MotivationalRemind; 
import java.util.*; 

public class Quotes {
    public static String inspirationalMessage() {
        String[] quotes = {"\"To live a creative life, we must lose our fear of being wrong.\" -Anonymous", 
        "\"If you are not willing to risk the usual you will have to settle for the ordinary.\" -Jim Rohn", 
        "\"All our dreams can come true if we have the courage to pursue them.\" -Walt Disney", 
        "\"Good things come to people who wait, but better things come to those who go out and get them\". -Anonymous",
        "\"Success is walking from failure to failure with no loss of enthusiasm.\" -Winston Churchill", 
        "\"Opportunities don't happen, you create them\". -Chris Grosser", 
        "\"Try not to become a person of success, but rather try to become a person of value\". -Albert Einstein", 
        "\"The ones who are crazy enough to think they can change the world, are the ones who do\". -Anonymous",
        "\"Our greatest weakness lies in giving up. The most certain way to succeed is always to try just one more time.\" -Thomas A. Edison", 
        "\"The biggest adventure you can take is to live the life of your dreams.\" -Oprah Winfrey",
        "\"Start where you are. Use what you have. Do what you can\". -Arthur Ashe", 
        "\"The difference between a stumbling block and a stepping stone is how high you raise your foot.\" -Benny Lewis",
        "\"You can only become truly accomplished at something you love.\" -Maya Angelou", 
        "\"If you don’t go after what you want, you’ll never have it. If you don’t ask, the answer is always no. If you don’t step forward, you’re always in the same place.\" -Nora Roberts",
        "\"Live as if you were to die tomorrow. Learn as if you were to live forever.\" -Gandhi"
    
    };
        int random = (int)(Math.random() * (quotes.length-1) + 1);
        return quotes[random];
    }
}
