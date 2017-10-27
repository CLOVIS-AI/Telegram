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
class EditedChannelPostUpdate extends Update {

    /** The channel post that was updated. */
    public final Message MESSAGE;
    
    /**
     * Created when a channel post is edited.
     * @param json data from the Telegram servers.
     */
    public EditedChannelPostUpdate(JsonObject json) {
        super(json);
        MESSAGE = Message.newMessage(json.get("edited_channel_post").asObject());
    }
    
    @Override
    public String toString(){
        return "EC " + MESSAGE.toString();
    }
}
