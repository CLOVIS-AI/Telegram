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
import bot.messages.VideoNoteMessage;
import bot.messages.VoiceMessage;
import bot.send.SendableText;
import bot.updates.ChannelPostUpdate;
import bot.updates.EditedChannelPostUpdate;
import bot.updates.EditedMessageUpdate;
import bot.messages.LeftMember;
import bot.messages.NewChatPhoto;
import bot.updates.MessageUpdate;
import bot.messages.NewMembers;
import bot.send.Sendable;
import bot.send.SendableAudio;
import bot.send.SendablePhoto;
import bot.send.SendableUpload;
import bot.send.SendableVideo;
import bot.updates.Update;
import bot.utils.MultipartUtility;
import bot.utils.others.PhotoSize;
import java.io.BufferedReader;
import java.io.File;
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
import minimaljson.WriterConfig;

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
    @SuppressWarnings("OverridableMethodCallInConstructor")
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
        
        setup();
    }
    
    /**
     * Use this method to initialize your values.
     * @see #getChat(long) Create a Chat
     */
    public void setup(){}
    
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
    public void onNewMessage(TextMessage message)       {}
    public void onNewMessage(AudioMessage message)      {}
    public void onNewMessage(PhotoMessage message)      {}
    public void onNewMessage(StickerMessage message)    {}
    public void onNewMessage(DocumentMessage message)   {}
    public void onNewMessage(VoiceMessage message)      {}
    public void onNewMessage(VideoMessage message)      {}
    public void onNewMessage(VideoNoteMessage message)  {}
    
    /**
     * This method is called when a message is edited in a group where the bot is.
     * @param message the message that was edited
     */
    public void onEditedMessage(Message message){}
    public void onEditedMessage(TextMessage message)       {}
    public void onEditedMessage(AudioMessage message)      {}
    public void onEditedMessage(PhotoMessage message)      {}
    public void onEditedMessage(StickerMessage message)    {}
    public void onEditedMessage(DocumentMessage message)   {}
    public void onEditedMessage(VoiceMessage message)      {}
    public void onEditedMessage(VideoMessage message)      {}
    public void onEditedMessage(VideoNoteMessage message)  {}
    
    /**
     * This method is called when a message is sent in a channel where the bot is.
     * @param message the message that was sent
     */
    public void onNewPost(Message message){}
    public void onNewPost(TextMessage message)       {}
    public void onNewPost(AudioMessage message)      {}
    public void onNewPost(PhotoMessage message)      {}
    public void onNewPost(StickerMessage message)    {}
    public void onNewPost(DocumentMessage message)   {}
    public void onNewPost(VoiceMessage message)      {}
    public void onNewPost(VideoMessage message)      {}
    public void onNewPost(VideoNoteMessage message)  {}
    
    /**
     * This method is called when a message is edited in a channel where the bot is.
     * @param message the message that was edited
     */
    public void onEditedPost(Message message){}
    public void onEditedPost(TextMessage message)       {}
    public void onEditedPost(AudioMessage message)      {}
    public void onEditedPost(PhotoMessage message)      {}
    public void onEditedPost(StickerMessage message)    {}
    public void onEditedPost(DocumentMessage message)   {}
    public void onEditedPost(VoiceMessage message)      {}
    public void onEditedPost(VideoMessage message)      {}
    public void onEditedPost(VideoNoteMessage message)  {}
    
    /**
     * This method is called when new users join a chat where the bot is
     * (the bot itself may be one of these members).
     * @param members a Message object of the members who joined.
     */
    public void onMembersJoining(NewMembers members){}
    
    /**
     * This method is called when a user leaves a chat where the bot is
     * (the use may be the bot itself).
     * @param member a Message object of the member who left.
     */
    public void onMemberLeaving(LeftMember member){}
    
    /**
     * This method is called when a chat's photo is modified.
     * @param photo a Message object of the photo that changed
     */
    public void onNewChatPhoto(NewChatPhoto photo){}
    
    /**
     * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
     * @param id unique identifier for the target chat
     * @return The Chat object.
     */
    public Chat getChat(long id){
        JsonObject j = new JsonObject();
        j.add("chat_id", id);
        return Chat.newChat(((JsonObject)http("getChat", j)).get("result").asObject());
    }
    
    /**
     * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
     * @param username unique identifier for the target chat
     * @return The Chat object.
     */
    public Chat getChat(String username){
        JsonObject j = new JsonObject();
        j.add("chat_id", username);
        return Chat.newChat(((JsonObject)http("getChat", j)).get("result").asObject());
    }
    
    /**
     * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
     * @param chat the Chat you want more infos on
     * @return The Chat object.
     */
    public Chat getChat(Chat chat){
        return getChat(chat.ID);
    }
    
    /**
     * Use this method to get the photos of a chat.
     * @param chat the Chat you want more infos on
     * @return The Photo object this chat should have.
     */
    public Chat.Photo getPhotos(Chat chat){
        return getChat(chat).PHOTO;
    }
    
    /**
     * Use this method to get up to date information about the chat (current name of the user for one-on-one conversations, current username of a user, group or channel, etc.). Returns a Chat object on success.
     * @param user the Chat you want more infos on
     * @return The Chat object.
     */
    public User getUser(User user){
        return (User)getChat(user);
    }
    
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
                if(m instanceof TextMessage){        onNewMessage((TextMessage)m); onNewMessage(m);}
                else if(m instanceof AudioMessage){  onNewMessage((AudioMessage)m); onNewMessage(m);}
                else if(m instanceof PhotoMessage){  onNewMessage((PhotoMessage)m); onNewMessage(m);}
                else if(m instanceof StickerMessage){onNewMessage((StickerMessage)m); onNewMessage(m);}
                else if(m instanceof DocumentMessage){onNewMessage((DocumentMessage)m); onNewMessage(m);}
                else if(m instanceof VoiceMessage){  onNewMessage((VoiceMessage)m); onNewMessage(m);}
                else if(m instanceof VideoMessage){  onNewMessage((VideoMessage)m); onNewMessage(m);}
                else if(m instanceof VideoNoteMessage){     onNewMessage((VideoNoteMessage)m); onNewMessage(m);}
            }else if(u instanceof EditedMessageUpdate){
                Message m = ((EditedMessageUpdate)u).MESSAGE;
                if(m instanceof TextMessage){        onEditedMessage((TextMessage)m); onEditedMessage(m);}
                else if(m instanceof AudioMessage){  onEditedMessage((AudioMessage)m); onEditedMessage(m);}
                else if(m instanceof PhotoMessage){  onEditedMessage((PhotoMessage)m); onEditedMessage(m);}
                else if(m instanceof StickerMessage){onEditedMessage((StickerMessage)m); onEditedMessage(m);}
                else if(m instanceof DocumentMessage){onEditedMessage((DocumentMessage)m); onEditedMessage(m);}
                else if(m instanceof VoiceMessage){  onEditedMessage((VoiceMessage)m); onEditedMessage(m);}
                else if(m instanceof VideoMessage){  onEditedMessage((VideoMessage)m); onEditedMessage(m);}
                else if(m instanceof VideoNoteMessage){     onEditedMessage((VideoNoteMessage)m); onEditedMessage(m);}
            }else if(u instanceof ChannelPostUpdate){
                Message m = ((ChannelPostUpdate)u).MESSAGE;
                if(m instanceof TextMessage){        onNewPost((TextMessage)m); onNewPost(m);}
                else if(m instanceof AudioMessage){  onNewPost((AudioMessage)m); onNewPost(m);}
                else if(m instanceof PhotoMessage){  onNewPost((PhotoMessage)m); onNewPost(m);}
                else if(m instanceof StickerMessage){onNewPost((StickerMessage)m); onNewPost(m);}
                else if(m instanceof DocumentMessage){onNewPost((DocumentMessage)m); onNewPost(m);}
                else if(m instanceof VoiceMessage){  onNewPost((VoiceMessage)m); onNewPost(m);}
                else if(m instanceof VideoMessage){  onNewPost((VideoMessage)m); onNewPost(m);}
                else if(m instanceof VideoNoteMessage){     onNewPost((VideoNoteMessage)m); onNewPost(m);}
            }else if(u instanceof EditedChannelPostUpdate){
                Message m = ((EditedChannelPostUpdate)u).MESSAGE;
                if(m instanceof TextMessage){        onEditedPost((TextMessage)m); onEditedPost(m);}
                else if(m instanceof AudioMessage){  onEditedPost((AudioMessage)m); onEditedPost(m);}
                else if(m instanceof PhotoMessage){  onEditedPost((PhotoMessage)m); onEditedPost(m);}
                else if(m instanceof StickerMessage){onEditedPost((StickerMessage)m); onEditedPost(m);}
                else if(m instanceof DocumentMessage){onEditedPost((DocumentMessage)m); onEditedPost(m);}
                else if(m instanceof VoiceMessage){  onEditedPost((VoiceMessage)m); onEditedPost(m);}
                else if(m instanceof VideoMessage){  onEditedPost((VideoMessage)m); onEditedPost(m);}
                else if(m instanceof VideoNoteMessage){     onEditedPost((VideoNoteMessage)m); onEditedPost(m);}
            }else if(u instanceof MessageUpdate){
                Message m = ((MessageUpdate) u).MESSAGE;
                if(m instanceof NewMembers){
                    onMembersJoining((NewMembers)m);
                }else if(m instanceof LeftMember){
                    onMemberLeaving((LeftMember)m);
                }else if(m instanceof NewChatPhoto){
                    onNewChatPhoto((NewChatPhoto)m);
                }else{
                    throw new UnsupportedOperationException("Unsupported message update : " + m.toString());
                }
            }else{
                throw new UnsupportedOperationException("Unsupported update : " + u.toString());
            }
            onEveryUpdate(u);
            maxUpdateID = max(u.UPDATE_ID, maxUpdateID)+1;
        }
    }
    
    /**
     * Call to get automatical updates. This method will never end, as long as
     * the thread calling it is alive.<br>
     * To get updates without blocking the code flow, use {@link #update(int) update(int)}.
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
            System.gc();
        }
        autoUpdateActivated = false;
    }
    
    // ******************************** METHODS ********************************
    
    /**
     * Forwards a message and notifies the users in the group.<br><br>
     * Same as calling {@link #forward(bot.messages.Message, bot.Chat, boolean) forward}<code>(message, destination, true)</code>.
     * @param message which message the bot should forward
     * @param destination where the message should be forwarded to
     * @return The message.
     */
    public final Message forward(Message message, Chat destination){
        return forward(message, destination, true);
    }
    
    /**
     * Forwards a message.
     * @param message which message the bot should forward
     * @param destination where the message should be forwarded to
     * @param notification do you want to notify the user ? If <code>false</code>,
     *                     the user will receive a notification with no sound.
     *                     Otherwise, this method is the same as {@link #forward(bot.messages.Message, bot.Chat) }
     * @return The sent message.
     */
    public final Message forward(Message message, Chat destination, boolean notification){
        System.out.println("Fwd " + message.toString() + " [to " + destination.toString() + "]");
        return forward(destination.ID,
                       message.CHAT.ID,
                       message.ID, notification);
    }
    
    /**
     * Forwards a message.
     * @param destination destination of the forward
     * @param source source of the forward
     * @param message the message
     * @deprecated 
     * @return The sent message.
     */
    public final Message forward(Chat destination, Chat source, Message message){
        return forward(destination.ID,
                       source.ID,
                       message.ID, true);
    }
    
    /**
     * Forwards a message
     * @param destinationID the destination
     * @param sourceID the source of the message
     * @param messageID the message
     * @param notification should the user be notified ?
     * @return On success, the sent message.
     */
    private Message forward(long destinationID, long sourceID, long messageID, boolean notification){
        JsonObject j = new JsonObject();
        j.add("chat_id", destinationID);
        j.add("from_chat_id", sourceID);
        j.add("message_id", messageID);
        j.add("disable_notification", !notification);
        return Message.newMessage(((JsonObject)http("forwardMessage", j)).get("result").asObject());
    }
    
    /**
     * Sends any message.
     * @param message the message
     * @param chat the chat where it should be sent
     * @return The sent message.
     */
    public Message send(Sendable message, Chat chat){
        JsonObject j = message.toJson();
        j.add("chat_id", chat.ID);
        if(message instanceof SendableUpload){
            SendableUpload upload = (SendableUpload) message;
            if(upload.isNewUpload()){
                return Message.newMessage(((JsonObject)upload(upload.methodName(), upload.type(), upload.file(), j)).get("result").asObject());
            }else{
                j.add(upload.type(), upload.id());
            }
        }
        return Message.newMessage(((JsonObject)http(message.methodName(), j)).get("result").asObject());
    }
    
    /**
     * Sends a text message.<br><br>
     * To get access to more options, use {@link #send(bot.send.Sendable, bot.Chat) }
     * with {@link SendableText}
     * @param text text of the message 
     * @param chat the chat where it should be sent
     * @return The sent message.
     */
    public Message send(String text, Chat chat){
        return send(new SendableText(text), chat);
    }
    
    /**
     * Sends a photo.<br><br>
     * To get access to more options, use {@link #send(bot.send.Sendable, bot.Chat) }
     * with {@link SendablePhoto}
     * @param photo photo you want to send 
     * @param chat the chat where it should be sent
     * @return The sent message.
     */
    public Message send(PhotoMessage photo, Chat chat){
        return send(photo(photo), chat);
    }
    
    /**
     * Sends an audio message.<br><br>
     * To get access to more options, use {@link #send(bot.send.Sendable, bot.Chat) }
     * with {@link SendableAudio}
     * @param audio audio you want to send 
     * @param chat the chat where it should be sent
     * @return The sent message.
     */
    public Message send(AudioMessage audio, Chat chat){
        return send(audio(audio), chat);
    }
    
    /**
     * Sends a video message.<br><br>
     * To get access to more options, use {@link #send(bot.send.Sendable, bot.Chat) }
     * with {@link SendableVideo}
     * @param video video you want to send 
     * @param chat the chat where it should be sent
     * @return The sent message.
     */
    public Message send(VideoMessage video, Chat chat){
        return send(video(video), chat);
    }
    
    /**
     * Replies to a message.
     * @param message the message you want to send
     * @param replyTo the message you are replying to
     * @return The sent message.
     */
    public Message reply(Sendable message, Message replyTo){
        JsonObject j = message.toJson();
        j.add("chat_id", replyTo.CHAT.ID);
        j.add("reply_to_message_id", replyTo.ID);
        if(message instanceof SendableUpload){
            SendableUpload upload = (SendableUpload) message;
            if(upload.isNewUpload()){
                return Message.newMessage(((JsonObject)upload(upload.methodName(), upload.type(), upload.file(), j)).get("result").asObject());
            }else{
                j.add(upload.type(), upload.id());
            }
        }
        return Message.newMessage(((JsonObject)http(message.methodName(), j)).get("result").asObject());
    }
    
    /**
     * Replies with a text message.<br><br>
     * To get access to more options, use {@link #reply(bot.send.Sendable, bot.messages.Message) reply(Sendable, Message) }
     * with {@link SendableText}.
     * @param text text of the message you want to send
     * @param replyTo the message you are replying to
     * @return The sent message.
     */
    public Message reply(String text, Message replyTo){
        return reply(new SendableText(text), replyTo);
    }
    
    /**
     * Replies with an audio message.<br><br>
     * To get access to more options, use {@link #reply(bot.send.Sendable, bot.messages.Message) }
     * with {@link SendablePhoto}
     * @param photo photo you want to send
     * @param message the message you're replying to
     * @return The sent message.
     */
    public Message reply(PhotoMessage photo, Message message){
        return reply(photo(photo), message);
    }
    
    /**
     * Replies with an audio message.<br><br>
     * To get access to more options, use {@link #reply(bot.send.Sendable, bot.messages.Message) }
     * with {@link SendableAudio}
     * @param audio audio you want to send 
     * @param message the message you're replying to
     * @return The sent message.
     */
    public Message reply(AudioMessage audio, Message message){
        return reply(audio(audio), message);
    }
    
    /**
     * Replies with an audio message.<br><br>
     * To get access to more options, use {@link #reply(bot.send.Sendable, bot.messages.Message) }
     * with {@link SendableVideo}
     * @param video video you want to send 
     * @param message the message you're replying to
     * @return The sent message.
     */
    public Message reply(VideoMessage video, Message message){
        return reply(video(video), message);
    }
    
    /**
     * Convenience method for text messages.
     * @param text the text of the message
     * @return <code>new SendableText(text)</code>
     */
    public SendableText text(String text){
        return new SendableText(text);
    }
    
    /**
     * Convenience method for photo messages.
     * @param url the url of the photo to send
     * @return <code>return new SendablePhoto(url);</code>
     */
    public SendablePhoto photo(String url){
        return new SendablePhoto(url);
    }
    
    /**
     * Convenience method for photo messages.
     * @param photo the photo to send again
     * @return <code>return new SendablePhoto(photo);</code>
     */
    public SendablePhoto photo(PhotoMessage photo){
        return new SendablePhoto(photo);
    }
    
    /**
     * Convenience method for photo messages.
     * @param photo the photo to send again
     * @return <code>return new SendablePhoto(photo);</code>
     */
    public SendablePhoto photo(PhotoSize photo){
        return new SendablePhoto(photo);
    }
    
    /**
     * Convenience method for photo messages.
     * @param file the photo to upload
     * @return <code>return new SendablePhoto(photo);</code>
     */
    public SendablePhoto photo(File file){
        return new SendablePhoto(file);
    }
    
    /**
     * Convenience method for audio messages.
     * @param audio the audio to send again
     * @return <code>return new SendableAudio(audio);</code>
     */
    public SendableAudio audio(AudioMessage audio){
        return new SendableAudio(audio);
    }
    
    /**
     * Convenience method for audio messages.
     * @param url the url of the audio message
     * @return <code>return new SendableAudio(url);</code>
     */
    public SendableAudio audio(String url){
        return new SendableAudio(url);
    }
    
    /**
     * Convenience method for audio messages.
     * @param file the file you want to upload
     * @return <code>return new SendableAudio(file);</code>
     */
    public SendableAudio audio(File file){
        return new SendableAudio(file);
    }
    
    /**
     * Convenience method for video messages.
     * @param url the url of the video message
     * @return <code>return new SendableVideo(url);</code>
     */
    public SendableVideo video(String url){
        return new SendableVideo(url);
    }
    
    /**
     * Convenience method for video messages.
     * @param message the video you want to resend
     * @return <code>return new SendableVideo(url);</code>
     */
    public SendableVideo video(VideoMessage message){
        return new SendableVideo(message);
    }
    
    /**
     * Convenience method for video messages.
     * @param file the video you want to upload
     * @return <code>return new SendableVideo(file);</code>
     */
    public SendableVideo video(File file){
        return new SendableVideo(file);
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
    private JsonValue http(String method, JsonObject parameters) throws CannotSendMessageException{
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL("https://api.telegram.org/bot" + TOKEN + "/" + method);
            byte[] postDataBytes = parameters.toString().getBytes("UTF-8");

            conn = (HttpURLConnection) url.openConnection();
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
            throw new CannotSendMessageException(ex, parameters, conn);
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
    
    /**
     * Uploads a file.
     * @param method the method of the Telegram bot API (eg. sendPhoto)
     * @param type type of the file (eg. photo)
     * @param file the file
     * @param parameters other parameters
     * @return The reply from the Telegram servers.
     */
    public JsonValue upload(String method, String type, File file, JsonObject parameters){
        System.out.print("Uploading " + type + " : " + file);
        
        if(!file.exists())
            throw new IllegalArgumentException("Error on " + file.getAbsolutePath() + " :\nThe file doesn't exist (see File.exists()).");
        if(!file.isFile())
            throw new IllegalArgumentException("Error on " + file.getAbsolutePath() + " :\nThe object provided is not a file (see File.isFile()).");
        if(!file.canRead())
            throw new IllegalArgumentException("Error on " + file.getAbsolutePath() + " :\nThe file is not readable (see File.canRead()).");
        
        try {
            System.out.print(" BUILD");
            MultipartUtility mu = new MultipartUtility("https://api.telegram.org/bot" + TOKEN + "/" + method);
            mu.addFilePart(type, file);
            for(JsonObject.Member j : parameters)
                mu.addFormField(j.getName(), j.getValue().toString());
            System.out.print(" UPLOAD");
            String response = "";
            for(String s : mu.finish())
                response += s;
            System.out.println(" DONE.");
            System.gc();
            return Json.parse(response);
        } catch (IOException ex) {
            throw new CannotSendMessageException(ex, parameters, null);
        }
    }
    
    /**
     * If you want Telegram to show bold, italic, fixed-width texts or URLs.<br>
     * The syntax depends on your choice ; see them below.
     * @see #MARKDOWN
     * @see #HTML
     */
    public static enum ParseMode{
        /**
         * <a href="https://core.telegram.org/bots/api#formatting-options">Formatting options.</a>
         */
        MARKDOWN{
            @Override
            public String toString(){
                return "Markdown";
            }
        },
        
        /**
         * <a href="https://core.telegram.org/bots/api#formatting-options">Formatting options.</a>
         */
        HTML{
            @Override
            public String toString(){
                return "HTML";
            }
        }
    }
    
    public class CannotSendMessageException extends RuntimeException {
        public CannotSendMessageException(IOException ex, JsonValue j, HttpURLConnection conn){
            super("ERROR WHILE SENDING MESSAGE :\n" + ex.getMessage()
                + "\nWITH OPTIONS : \n" + j.toString(WriterConfig.PRETTY_PRINT)
                + "\nFROM SERVER :\n" + (conn != null ? MultipartUtility.getStringFromInputStream(conn.getErrorStream()) : "NO DATA"));
        }
    }
}
