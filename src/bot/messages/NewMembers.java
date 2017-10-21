/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.User;
import minimaljson.JsonArray;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
class NewMembers extends Message {

    /** The users who just joined the chat. The bot may be one of these members. */
    public final User[] USERS;
    
    /**
     * Created when users join the chat.
     * @param json data from the Telegram servers
     */
    public NewMembers(JsonObject json) {
        super(json);
        JsonArray ja = json.get("new_chat_members").asArray();
        USERS = new User[ja.values().size()];
        for(int i = 0; i < USERS.length; i++){
            USERS[i] = new User(ja.get(i).asObject());
        }
    }
    
}
