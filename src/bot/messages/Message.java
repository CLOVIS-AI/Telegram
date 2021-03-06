/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.Chat;
import bot.MandatoryFieldOmittedException;
import bot.User;
import bot.send.Sendable;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import com.eclipsesource.json.WriterConfig;

/**
 *
 * @author CLOVIS
 */
public class Message{
    
    /** Unique message identifier inside this chat. */
    public final long ID;
    /** (Optional : not channels)<br> User who sent this message. */
    public final User FROM;
    /** (Optional : only channels)<br> Signature of the user who sent this message. */
    public final String AUTHOR_SIGNATURE;
    /** Date the message was sent in Unix time. */
    public final int DATE;
    /** Converstation this message belongs to. */
    public final Chat CHAT;
    
    /**
     * Informations about who forwarded this message. It can hold either :
     * <ul>
     * <li><code>null</code> - if this message was not forwarded (you can also
     * use {@link #isForwarded() isForwarded()} to know if this message was
     * forwarded),</li>
     * <li>A {@link ForwardDataUser} object - if this message was forwarded from a group : data about the original user,</li>
     * <li>A {@link ForwardDataChannel} object - if this message was forwarded from a channel : data about the channel.</li>
     * </ul>
     */
    public final ForwardData FORWARD;
    
    /**
     * Is this message forwarded from somewhere else ?<br><br>
     * 
     * If this method returns <code>true</code>, it is safe to use the
     * {@link #FORWARD} variable without fearing {@link NullPointerException}.
     * @return <code>true</code> if it was forwarded.
     */
    public final boolean isForwarded(){
        return FORWARD != null;
    }
    
    /** (Optional : replies)<br> The original message. 
     * It will not contain further <code>reply</code> fields even if it
     * is itself is a reply. */
    public final Message REPLY;
    /** (Optional : edited)<br> The date of the last edit in Unix time. */
    public final int LAST_EDIT;
    
    /**
     * Creates a Message object.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException if mandatory fields are omitted
     */
    public Message(JsonObject json) throws MandatoryFieldOmittedException{
        ID = json.getLong("message_id", 0);
        FROM = new User(json.get("from").asObject());
        AUTHOR_SIGNATURE = json.getString("author_signature", null);
        DATE = json.getInt("date", 0);
        CHAT = Chat.newChat(json.get("chat").asObject());
        
        if(json.get("forward_from") != null)
            FORWARD = new ForwardDataUser(json);
        else if(json.get("forward_from_channel") != null)
            FORWARD = new ForwardDataChannel(json);
        else
            FORWARD = null;
        
        Message rpl;
        try{ rpl = newMessage(json.get("reply_to_message").asObject()); }
        catch(NullPointerException e){ rpl = null; }
        REPLY = rpl;
        
        LAST_EDIT = json.getInt("edit_date", 0);
    }
    
    /**
     * Creates an empty Message object. <b>DO NOT USE ANYWHERE EXCEPT THE SEND... METHODS !</b>
     * @param ID ID of this message
     * @deprecated
     */
    public Message(long ID){
        this.ID = ID;
        FROM = null;
        AUTHOR_SIGNATURE = null;
        DATE = 0;
        CHAT = null;
        FORWARD = null;
        REPLY = null;
        LAST_EDIT = 0;
    }
    
    @Override
    public String toString(){
        return CHAT.toString() + " > " + (FROM != null ? FROM.toString() : (AUTHOR_SIGNATURE == null ? "" : AUTHOR_SIGNATURE));
    }
    
    /**
     * Searches for the correct subclass of Message for a specific JSON-encoded 
     * Message object, instantiates it, then returns it.
     * @param json data representing a Message as sent by the Telegram servers
     * @return The corresponding Message object.
     * @throws MandatoryFieldOmittedException if any mandatory field is omitted.
     */
    public static final Message newMessage(JsonObject json) throws MandatoryFieldOmittedException{
        if(json.getString("text", null) != null){ return new TextMessage(json); }
        else if(json.getInt("duration", -1) != -1){ return new AudioMessage(json); }
        else if(json.get("photo") != null){ return new PhotoMessage(json); }
        else if(json.get("sticker") != null){ return new StickerMessage(json); }
        else if(json.get("document") != null){ return new DocumentMessage(json); }
        else if(json.get("voice") != null){ return new VoiceMessage(json); }
        else if(json.get("video") != null){ return new VideoMessage(json); }
        else if(json.get("video_note") != null){ return new VideoNoteMessage(json); }
        else if(json.get("new_chat_members") != null){ return new NewMembers(json); }
        else if(json.get("left_chat_member") != null){ return new LeftMember(json); }
        else if(json.get("audio") != null){ return new AudioMessage(json); }
        else if(json.get("new_chat_photo") != null){ return new NewChatPhoto(json); }
        else if(json.get("pinned_message") != null){ return new PinnedMessage(json); }
        
        else if(json.get("group_chat_created") != null ||
                json.get("channel_created") != null ||
                json.get("supergroup_chat_created") != null){ return new ChatCreated(json); }
        else{
            throw new UnsupportedOperationException("This type of message is not yet supported : " + json.toString(WriterConfig.PRETTY_PRINT));
        }
    }
    
    /**
     * Convert this message to a Sendable object so you can send it/reply to it.
     * @return A Sendable object of this message.
     */
    public final Sendable toSendable(){
        return Sendable.newSendable(this);
    }
    
    /**
     * Data of a forward. Can be either {@link ForwardDataUser} or {@link ForwardDataChannel}.
     */
    public abstract class ForwardData{
        /** Date the original message was sent in Unix time. */
        public final int DATE;
        
        public ForwardData(JsonObject json){
            DATE = json.getInt("forward_date", 0);
        }
    }
    
    /**
     * Data of the forward from a user.
     */
    public final class ForwardDataUser extends ForwardData {
        /** Sender of the original message. */
        public final User USER;

        public ForwardDataUser(JsonObject json) {
            super(json);
            JsonValue ja = json.get("forward_from");
            if(ja != null)
                USER = new User(ja.asObject());
            else
                USER = null;
        }
    }
    
    /**
     * Data of the forward from a channel.
     */
    public final class ForwardDataChannel extends ForwardData {
        /** Information about the channel. */
        public final Chat CHANNEL;
        /** Identifier of the orginal message. */
        public final long ID;
        /** (Optional)<br> Signature of the post author if present. */
        public final String SIGNATURE;

        public ForwardDataChannel(JsonObject json) {
            super(json);
            
            JsonValue ja = json.get("forward_from_channel");
            if(ja != null)
                CHANNEL = Chat.newChat(ja.asObject());
            else
                CHANNEL = null;
            
            ID = json.getLong("forward_from_message_id", -1);
            SIGNATURE = json.getString("forward_signature", null);
        }
    }
}
