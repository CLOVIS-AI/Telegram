/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.User;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class LeftMember extends Message {

    /** User who left the chat. May be the bot itself. */
    public final User USER;
    
    /**
     * Created when a user leaves a chat.
     * @param json 
     */
    public LeftMember(JsonObject json) {
        super(json);
        USER = new User(json.get("left_chat_member").asObject());
    }
    
    @Override
    public String toString(){
        return super.toString() + " left the group.";
    }
}
