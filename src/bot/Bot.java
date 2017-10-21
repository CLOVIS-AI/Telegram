/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.messages.Message;
import bot.updates.Update;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import minimaljson.Json;
import minimaljson.JsonArray;
import minimaljson.JsonObject;
import minimaljson.JsonValue;

/**
 *
 * @author CLOVIS
 */
public class Bot {
    
    /** Token as provided by BotFather. */
    private final String TOKEN;
    /** User ID of this bot. */
    public final long ID;
    /** First name of this bot. */
    public final String FIRST_NAME;
    /** Username of this bot. */
    public final String USERNAME;
    
    /**
     * Creates a bot and connects it to the Telegram servers.
     * @param token token provided by BotFather
     */
    public Bot(String token){
        TOKEN = token;
        JsonObject me = http("getMe").asObject().get("result").asObject();
        ID = me.getLong("id", 0);
        FIRST_NAME = me.getString("first_name", null);
        USERNAME = me.getString("username", null);
        
        if(ID == 0 || FIRST_NAME == null || USERNAME == null)
            throw new IllegalArgumentException("This bot has issues.");
        
        System.out.println("Hello, I am " + FIRST_NAME + ".\n    Username : @"
                + USERNAME + "\n    ID : " + ID);
    }
    
    public void onUpdate(Update update){}
    
    public void onEditedMessage(Message message){onUpdate(message);}
    
    public final void update(){
        JsonArray updates = http("getUpdates").asArray();
        for(JsonValue value : updates){
            
        }
    }
    
    /**
     * Sends a HTTP request to the Telegram Bot API.<br/><br/>
     * This method is greatly inspired from 
     * <a href="https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily">Stack Overflow</a>.
     * @param method the command to the Telegram servers
     * @param parameters the parameters required by the servers
     * @return The reply from the Telegram servers.
     * @see http(String);
     */
    private JsonValue http(String method, JsonObject parameters){
        URL url; 
        try {
            url = new URL("https://api.telegram.org/bot" + TOKEN + "/" + method);
            byte[] postDataBytes = parameters.toString().getBytes("UTF-8");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            conn.setDoOutput(true);
            conn.getOutputStream().write(postDataBytes);
            
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            
            String output = "";
            for(int c; (c = in.read()) >= 0;){
                output += (char)c;
            }
            JsonValue result = Json.parse(output);
            return result;
        } catch (UnsupportedEncodingException | MalformedURLException ex){
            System.err.println("Unexpected error : " + ex.toString());
        } catch (FileNotFoundException ex){
            System.err.println("The command " + method + " does not exist.");
        } catch (IOException ex) {
            System.err.println("ERROR WHILE SENDING MESSAGE");
        }
        return null;
    }
    
    /**
     * Sends a HTTP request to the Telegram Bot API.
     * @param method the command to the Telegram servers.
     * @return The reply from the Telegram servers.
     * @see http(String, JsonObject)
     */
    private JsonValue http(String method){
        return http(method, Json.parse("{\"status\":null}").asObject());
    }
    
}
