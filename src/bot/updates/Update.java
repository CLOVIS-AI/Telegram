/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.updates;

import minimaljson.JsonObject;

/**
 * This object represents an incoming update.
 * @author CLOVIS
 */
public abstract class Update {
    
    /** ID of this update. */
    public final int UPDATE_ID;
    
    /**
     * Creates an update.
     * @param json JSON update from the Telegram servers.
     */
    public Update(JsonObject json){
        UPDATE_ID = json.getInt("update_id", 0);
    }
    
    @Override
    public String toString(){
        return "This is an update.";
    }
    
    public static Update newUpdate(JsonObject json){
        if     (json.get("message") != null){ return new MessageUpdate(json); }
        else if(json.get("edited_message") != null){ return new EditedMessageUpdate(json); }
        else if(json.get("channel_post") != null){ return new ChannelPostUpdate(json); }
        else if(json.get("edited_channel_post") != null){ return new EditedChannelPostUpdate(json); }
        // TODO
        else{
            throw new UnsupportedOperationException("This update is either invalid or not yet implemented in the API :\n" + json.toString());
        }
    }
}
