/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

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
 * Class that defines a bot.<br><br>
 * <b>Creating your own bot</b><br>
 * To create your bot, you just have to follow these simple steps.<br>
 * <ol>
 *   <li>Getting your token from Telegram :
 *     <ol>
 *       <li>Open Telegram, and go talk with <a href="t.me/BotFather">@BotFather</a>.</li>
 *       <li>Do <code>/newbot</code> to officially create your bot.
 *           BotFather will ask you for a name and a username.
 *       </li>
 *       <li>Copy the token BotFather gives you, this is what you are going to use to identity your bot.<br>
 *           It should look like <code>110201543:AAHdqTcvCH1vGWJxfSeofSAs0K5PALDsaw</code>.
 *       </li>
 *     </ol>
 *   </li>
 *   <li>Create a new Java project,</li>
 *   <li>In the <code>main</code> method, create your bot as such (this is only an example) :<br>
 *     <pre>
 * Bot myBot = new Bot(PUT_YOUR_TOKEN_HERE){
 *     public void onUpdate(Update u){
 *         // What the bot should do when it recieves an update (new messages, edited messages, queries, ...)
 *     }
 * };
 * myBot.autoUpdate(); // To launch the bot (see {@link Bot#autoUpdate() autoUpdate()} for more informations).
 *     </pre>
 *   </li>
 *   <li>That's it ! You can run your code, the bot is working ! You just have to 
 *       fill the {@link #onUpdate(bot.updates.Update) onUpdate()} method now.<br>
 *       You can find more informations on the different kinds of updates {@link Update here}.
 *   </li>
 * </ol>
 * 
 * @author CLOVIS
 */
public abstract class Bot{
    
    /** Token as provided by BotFather. */
    private final String TOKEN;
    /** User ID of this bot. */
    public final long ID;
    /** Name of this bot. */
    public final String NAME;
    /** Username of this bot. */
    public final String USERNAME;
    
    /** Time between two updates. */
    private long updateTimeout;
    
    private boolean autoUpdateActivated = false;
    
    /**
     * Creates a bot and connects it to the Telegram servers, and prints to the
     * standard output the identity of the bot.<br>
     * By default, the bot will start a thread to update itself every 5 seconds.
     * To set the update frequency, see {@link}.
     * @param token token provided by BotFather
     */
    public Bot(String token){
        TOKEN = token;
        JsonObject me = http("getMe").asObject().get("result").asObject();
        ID = me.getLong("id", 0);
        NAME = me.getString("first_name", null);
        USERNAME = me.getString("username", null);
        
        if(ID == 0 || NAME == null || USERNAME == null)
            throw new IllegalArgumentException("This bot has issues.");
        
        System.out.println("Hello, I am " + NAME + ".\n    Username : @"
                + USERNAME + "\n    ID : " + ID);
    }
    
    /**
     * Sets the frequency of the automatical updates.
     * @param time time between each update, in milliseconds ; time must be greater
     *              or equal to 100 ms.
     * @exception IllegalArgumentException if time is lesser than 100.
     */
    public void setAutoUpdateFrequency(long time) throws IllegalArgumentException {
        if(time >= 100){
            updateTimeout = time;
        }else{
            throw new IllegalArgumentException("The argument 'time' should be greater or equal to 100 : " + time);
        }
    }
    
    /**
     * This method is called whenever the bot recieves a new {@link Update update}.
     * Override this method to act when this happens.
     * @param update an update from the Telegram servers
     */
    public abstract void onUpdate(Update update);
    
    /**
     * Connects to the Telegram server to get new updates, then calls
     * {@link #onUpdate(bot.updates.Update) onUpdate()} on every update.
     */
    public synchronized final void update(){
        JsonValue request = http("getUpdates").asObject().get("result");
        JsonArray updates = request.asArray();
        for(JsonValue value : updates){
            onUpdate(Update.newUpdate((JsonObject) value));
        }
    }
    
    /**
     * Call to get automatical updates. This method will never end, as long as
     * the thread calling it is alive.<br>
     * To get updates without blocking the code flow, use {@link #update() }.
     * @exception IllegalThreadStateException if called twice at the same time
     */
    @SuppressWarnings("SleepWhileInLoop")
    public final void autoUpdate() throws IllegalThreadStateException {
        if(autoUpdateActivated){
            throw new IllegalThreadStateException("This method cannot be called by two threads.");
        }
        autoUpdateActivated = true;
        
        while(Thread.currentThread().isAlive()){
            update();
            try {
                Thread.sleep(updateTimeout);
            } catch (InterruptedException ex) {
                System.err.println(ex.toString());
            }
        }
        autoUpdateActivated = false;
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
            System.err.println("ERROR WHILE SENDING MESSAGE : " + ex.getMessage());
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
