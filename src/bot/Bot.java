/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.messages.AudioMessage;
import bot.messages.DocumentMessage;
import bot.messages.Message;
import bot.messages.PhotoMessage;
import bot.messages.StickerMessage;
import bot.messages.TextMessage;
import bot.messages.VideoMessage;
import bot.messages.VideoNote;
import bot.messages.VoiceMessage;
import bot.updates.ChannelPostUpdate;
import bot.updates.EditedChannelPostUpdate;
import bot.updates.EditedMessageUpdate;
import bot.updates.LeftMember;
import bot.updates.MessageUpdate;
import bot.updates.NewMembers;
import bot.updates.Update;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import static java.lang.Math.max;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 Bot myBot = new Bot(PUT_YOUR_TOKEN_HERE){
     public void onEveryUpdate(Update u){
         // What the bot should do when it recieves an update (new messages, edited messages, queries, ...)
     }
 };
 myBot.autoUpdate(); // To launch the bot (see {@link Bot#autoUpdate() autoUpdate()} for more informations).
 *     </pre>
 *   </li>
 *   <li>That's it ! You can run your code, the bot is working ! You just have to 
 *       fill the {@link #onEveryUpdate(bot.updates.Update) onEveryUpdate()} method now.<br>
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
    private long updateTimeout = 1000;
    
    private boolean autoUpdateActivated = false;
    
    private long maxUpdateID = 0;
    
    /**
     * Creates a bot and connects it to the Telegram servers, and prints to the
     * standard output the identity of the bot.<br>
     * By default, the bot will start a thread to update itself every 5 seconds.
     * To set the update frequency, see {@link}.
     * @param token token provided by BotFather
     */
    public Bot(String token){
        System.out.println("Connecting ...");
        
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
     * @param time time between each update, in seconds ; default = 1000s
     * @exception IllegalArgumentException if time is lesser than 0.
     */
    public final void setAutoUpdateFrequency(long time) throws IllegalArgumentException {
        if(time >= 0){
            updateTimeout = time;
        }else{
            throw new IllegalArgumentException("The argument 'time' should be greater or equal to 100 : " + time);
        }
    }
    
    /**
     * This method is called whenever the bot recieves a new {@link Update update}.
     * Override this method to act when this happens. This method is called in last.
     * @param update an update from the Telegram servers
     */
    public abstract void onEveryUpdate(Update update);
    
    /**
     * This method is called when a new message is sent in a group where the bot is.
     * @param message the message that was sent
     */
    public void onNewMessage(Message message){}
    public void onNewMessage(TextMessage message)       {onNewMessage((Message)message);}
    public void onNewMessage(AudioMessage message)      {onNewMessage((Message)message);}
    public void onNewMessage(PhotoMessage message)      {onNewMessage((Message)message);}
    public void onNewMessage(StickerMessage message)    {onNewMessage((Message)message);}
    public void onNewMessage(DocumentMessage message)   {onNewMessage((Message)message);}
    public void onNewMessage(VoiceMessage message)      {onNewMessage((Message)message);}
    public void onNewMessage(VideoMessage message)      {onNewMessage((Message)message);}
    public void onNewMessage(VideoNote message)         {onNewMessage((Message)message);}
    
    /**
     * This method is called when a message is edited in a group where the bot is.
     * @param message the message that was edited
     */
    public void onEditedMessage(Message message){System.out.println("raté");}
    public void onEditedMessage(TextMessage message)       {onEditedMessage((Message)message);}
    public void onEditedMessage(AudioMessage message)      {onEditedMessage((Message)message);}
    public void onEditedMessage(PhotoMessage message)      {onEditedMessage((Message)message);}
    public void onEditedMessage(StickerMessage message)    {onEditedMessage((Message)message);}
    public void onEditedMessage(DocumentMessage message)   {onEditedMessage((Message)message);}
    public void onEditedMessage(VoiceMessage message)      {onEditedMessage((Message)message);}
    public void onEditedMessage(VideoMessage message)      {onEditedMessage((Message)message);}
    public void onEditedMessage(VideoNote message)         {onEditedMessage((Message)message);}
    
    /**
     * This method is called when a message is sent in a channel where the bot is.
     * @param message the message that was sent
     */
    public void onNewPost(Message message){}
    public void onNewPost(TextMessage message)       {onNewPost((Message)message);}
    public void onNewPost(AudioMessage message)      {onNewPost((Message)message);}
    public void onNewPost(PhotoMessage message)      {onNewPost((Message)message);}
    public void onNewPost(StickerMessage message)    {onNewPost((Message)message);}
    public void onNewPost(DocumentMessage message)   {onNewPost((Message)message);}
    public void onNewPost(VoiceMessage message)      {onNewPost((Message)message);}
    public void onNewPost(VideoMessage message)      {onNewPost((Message)message);}
    public void onNewPost(VideoNote message)         {onNewPost((Message)message);}
    
    /**
     * This method is called when a message is edited in a channel where the bot is.
     * @param message the message that was edited
     */
    public void onEditedPost(Message message){}
    public void onEditedPost(TextMessage message)       {onEditedPost((Message)message);}
    public void onEditedPost(AudioMessage message)      {onEditedPost((Message)message);}
    public void onEditedPost(PhotoMessage message)      {onEditedPost((Message)message);}
    public void onEditedPost(StickerMessage message)    {onEditedPost((Message)message);}
    public void onEditedPost(DocumentMessage message)   {onEditedPost((Message)message);}
    public void onEditedPost(VoiceMessage message)      {onEditedPost((Message)message);}
    public void onEditedPost(VideoMessage message)      {onEditedPost((Message)message);}
    public void onEditedPost(VideoNote message)         {onEditedPost((Message)message);}
    
    /**
     * This method is called when new users join a chat where the bot is
     * (the bot itself may be one of these members).
     * @param users the users who joined the group
     */
    public void onMembersJoining(User[] users){}
    
    /**
     * This method is called when a user leaves a chat where the bot is
     * (the use may be the bot itself).
     * @param user the user who left the group
     */
    public void onMemberLeaving(User user){}
    
    /**
     * Connects to the Telegram server to get new updates, then calls
     * {@link #onEveryUpdate(bot.updates.Update) onEveryUpdate()} on every update.
     * @param longPolling if no updates are available, how long should the
     * Telegram servers take to end the connection (seconds) ?
     */
    public synchronized final void update(int longPolling){
        JsonValue request = http("getUpdates?offset=" + maxUpdateID + "&timeout="+longPolling).asObject().get("result");
        JsonArray updates = request.asArray();
        for(JsonValue value : updates){
            Update u = Update.newUpdate((JsonObject) value);
            if(u instanceof MessageUpdate){
                Message m = ((MessageUpdate)u).MESSAGE;
                if(m instanceof TextMessage)        onNewMessage((TextMessage)m);
                else if(m instanceof AudioMessage)  onNewMessage((AudioMessage)m);
                else if(m instanceof PhotoMessage)  onNewMessage((PhotoMessage)m);
                else if(m instanceof StickerMessage)onNewMessage((StickerMessage)m);
                else if(m instanceof DocumentMessage)onNewMessage((DocumentMessage)m);
                else if(m instanceof VoiceMessage)  onNewMessage((VoiceMessage)m);
                else if(m instanceof VideoMessage)  onNewMessage((VideoMessage)m);
                else if(m instanceof VideoNote)     onNewMessage((VideoNote)m);
            }else if(u instanceof EditedMessageUpdate){
                Message m = ((EditedMessageUpdate)u).MESSAGE;
                if(m instanceof TextMessage)        onEditedMessage((TextMessage)m);
                else if(m instanceof AudioMessage)  onEditedMessage((AudioMessage)m);
                else if(m instanceof PhotoMessage)  onEditedMessage((PhotoMessage)m);
                else if(m instanceof StickerMessage)onEditedMessage((StickerMessage)m);
                else if(m instanceof DocumentMessage)onEditedMessage((DocumentMessage)m);
                else if(m instanceof VoiceMessage)  onEditedMessage((VoiceMessage)m);
                else if(m instanceof VideoMessage)  onEditedMessage((VideoMessage)m);
                else if(m instanceof VideoNote)     onEditedMessage((VideoNote)m);
            }else if(u instanceof ChannelPostUpdate){
                Message m = ((ChannelPostUpdate)u).MESSAGE;
                if(m instanceof TextMessage)        onNewPost((TextMessage)m);
                else if(m instanceof AudioMessage)  onNewPost((AudioMessage)m);
                else if(m instanceof PhotoMessage)  onNewPost((PhotoMessage)m);
                else if(m instanceof StickerMessage)onNewPost((StickerMessage)m);
                else if(m instanceof DocumentMessage)onNewPost((DocumentMessage)m);
                else if(m instanceof VoiceMessage)  onNewPost((VoiceMessage)m);
                else if(m instanceof VideoMessage)  onNewPost((VideoMessage)m);
                else if(m instanceof VideoNote)     onNewPost((VideoNote)m);
            }else if(u instanceof EditedChannelPostUpdate){
                Message m = ((EditedChannelPostUpdate)u).MESSAGE;
                if(m instanceof TextMessage)        onEditedPost((TextMessage)m);
                else if(m instanceof AudioMessage)  onEditedPost((AudioMessage)m);
                else if(m instanceof PhotoMessage)  onEditedPost((PhotoMessage)m);
                else if(m instanceof StickerMessage)onEditedPost((StickerMessage)m);
                else if(m instanceof DocumentMessage)onEditedPost((DocumentMessage)m);
                else if(m instanceof VoiceMessage)  onEditedPost((VoiceMessage)m);
                else if(m instanceof VideoMessage)  onEditedPost((VideoMessage)m);
                else if(m instanceof VideoNote)     onEditedPost((VideoNote)m);
            }else if(u instanceof NewMembers){
                onMembersJoining(((NewMembers)u).USERS);
            }else if(u instanceof LeftMember){
                onMemberLeaving(((LeftMember)u).USER);
            }else{
                throw new UnsupportedOperationException("Unsupported update : " + u.toString());
            }
            onEveryUpdate(u);
            maxUpdateID = max(u.UPDATE_ID, maxUpdateID)+1;
        }
    }
    
    // ******************************** METHODS ********************************
    
    public final Message forward(Chat destination, Chat source, Message message){
        return forward(destination.ID,
                       source.ID,
                       message.ID);
    }
    
    public final Message forward(long destinationID, long sourceID, long messageID){
        JsonObject j = new JsonObject();
        j.add("chat_id", destinationID);
        j.add("from_chat_id", sourceID);
        j.add("message_id", messageID);
        return Message.newMessage((JsonObject)http("forwardMessage", j));
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
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println(dateFormat.format(new Date()) + "\tAuto-updating bot ...");
            update((int) updateTimeout);
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
            System.err.println("The command " + method + " does not exist : " + ex.getMessage());
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
