/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.Chat;
import bot.MandatoryFieldOmittedException;
import bot.User;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public abstract class Message{
    
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
    
    /** (Optional : forwarded)<br> Sender of the original message. */
    public final User FORWARD_FROM;
    /** (Optional : forwarded from channel)<br> Information about the channel. */
    public final Chat FORWARD_FROM_CHANNEL;
    /** (Optional : forwarded from channel)<br> Identifier of the orginal message. */
    public final int  FORWARD_FROM_MESSAGE_ID;
    /** (Optional : forwarded from channel)<br> Signature of the post author if present. */
    public final String FORWARD_SIGNATURE;
    /** (Optional : forwarded)<br> Date the original message was sent in Unix time. */
    public final int  FORWARD_DATE;
    
    /** (Optional : replies)<br> The original message. 
     * It will not contain further <code>reply_to_message</code> fields even if it
     * is itself is a reply. */
    public final Message REPLY_TO_MESSAGE;
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
        CHAT = new Chat(json.get("chat").asObject());
        
        User fwdFrm;
        try{ fwdFrm = new User(json.get("forward_from").asObject()); }
        catch(NullPointerException e){ fwdFrm = null; }
        FORWARD_FROM = fwdFrm;
        
        Chat fwdFrmChn;
        try{ fwdFrmChn = new Chat(json.get("forward_from_chat").asObject()); }
        catch(NullPointerException e){ fwdFrmChn = null; }
        FORWARD_FROM_CHANNEL = fwdFrmChn;
        
        FORWARD_FROM_MESSAGE_ID = json.getInt("forward_from_message_id", 0);
        FORWARD_SIGNATURE = json.getString("forward_signature", null);
        FORWARD_DATE = json.getInt("forward_date", 0);
        
        Message rpl;
        try{ rpl = newMessage(json.get("reply_to_message").asObject()); }
        catch(NullPointerException e){ rpl = null; }
        REPLY_TO_MESSAGE = rpl;
        
        LAST_EDIT = json.getInt("edit_date", 0);
    }
    
    @Override
    public String toString(){
        return CHAT.toString() + " > " + (FROM == null ? FROM.toString() : (AUTHOR_SIGNATURE == null ? "" : AUTHOR_SIGNATURE));
    }
    
    public static final Message newMessage(JsonObject json) throws MandatoryFieldOmittedException{
        if(json.getString("text", null) != null){ return new TextMessage(json); }
        else if(json.getInt("duration", -1) != -1){ return new AudioMessage(json); }
        else if(json.get("new_chat_members") != null){ return new NewMembers(json); }
        else if(json.get("left_chat_member") != null){ return new LeftMember(json); }
        else if(json.get("photo") != null){ return new PhotoMessage(json); }
        else if(json.get("sticker") != null){ return new StickerMessage(json); }
        else{
            throw new UnsupportedOperationException("This type of message is not supported : " + json.toString());
        }
    }
}
