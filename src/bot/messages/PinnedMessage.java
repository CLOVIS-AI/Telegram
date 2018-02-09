/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import com.eclipsesource.json.JsonObject;

/**
 * An object which represents the message received by a bot when a new Message
 * is pinned.
 * @author CLOVIS
 */
public class PinnedMessage extends Message {
    
    /**
     * The message that was pinned. Note that this object will not contain further
     * {@link Message#REPLY} field even if it itself is a reply.
     */
    public final Message MESSAGE;
    
    /**
     * Creates a PinnedMessage instance.
     * @param json data from the Telegram servers.
     * @throws MandatoryFieldOmittedException if any mandatory field is omitted.
     */
    public PinnedMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        
        MESSAGE = Message.newMessage(json.get("pinned_message").asObject());
        
        if(MESSAGE == null)
            throw new MandatoryFieldOmittedException("The parameter pinned_message should not be omitted", json);
    }
    
    @Override
    public String toString(){
        return super.toString() + " pinned the message : " + MESSAGE.toString();
    }
    
}
