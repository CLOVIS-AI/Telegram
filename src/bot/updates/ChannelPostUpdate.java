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
class ChannelPostUpdate extends Update {

    /** The message that was sent to the channel. */
    public final Message MESSAGE;
    
    /**
     * Created when the bot recieves a channel post.
     * @param json data from the Telegram servers.
     */
    public ChannelPostUpdate(JsonObject json) {
        super(json);
        MESSAGE = Message.newMessage(json.get("channel_post").asObject());
    }
    
}
