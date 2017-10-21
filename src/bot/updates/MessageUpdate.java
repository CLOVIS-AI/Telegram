/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.updates;

import bot.messages.Message;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class MessageUpdate extends Update {

    /** Message object of the sent message. */
    public final Message MESSAGE;
    
    /**
     * Creates a MessageUpdate object.
     * @param json data from the Telegram servers
     */
    public MessageUpdate(JsonObject json) {
        super(json);
        MESSAGE = Message.newMessage(json.get("message").asObject());
    }
    
}
