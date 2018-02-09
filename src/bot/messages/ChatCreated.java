/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.Chat.Type;
import static bot.Chat.Type.CHANNEL;
import static bot.Chat.Type.GROUP;
import static bot.Chat.Type.SUPERGROUP;
import bot.MandatoryFieldOmittedException;
import com.eclipsesource.json.JsonObject;

/**
 * Service message : the group has been created.
 * <p>This cannot be a private group. For supergroups and channels, this message
 * can not be recieved through updates, because the bot can't be a member
 * when it's created - but it can be found in {@link Message#REPLY} if someone
 * replies to the very first message.
 * @author CLOVIS
 */
public class ChatCreated extends Message {
    
    /**
     * The group ({@link Message#CHAT}) has been created.
     */
    public final Type TYPE;

    /**
     * Creates a ChatCreated object.
     * @param json data from the Telegram servers.
     * @throws MandatoryFieldOmittedException if a field is omitted
     */
    public ChatCreated(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        
        if(json.getBoolean("group_chat_created", false))
            TYPE = GROUP;
        else if(json.getBoolean("channel_created", false))
            TYPE = CHANNEL;
        else if(json.getBoolean("supergroup_chat_created", false))
            TYPE = SUPERGROUP;
        else
            throw new MandatoryFieldOmittedException("Neither of the fields group_chat_created, channel_created or supergroup_chat_created were provided", json);
    }
    
}
