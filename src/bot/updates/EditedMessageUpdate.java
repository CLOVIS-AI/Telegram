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
class EditedMessageUpdate extends Update {

    /** The message that was updated in this update. */
    public final Message MESSAGE;
    
    /**
     * Creates an object to an update consisting of editing a message.
     * @param json data from the Telegram servers
     */
    public EditedMessageUpdate(JsonObject json) {
        super(json);
        MESSAGE = Message.newMessage(json.get("edited_message").asObject());
    }
    
    @Override
    public String toString(){
        return "EG " + MESSAGE.toString();
    }
}
