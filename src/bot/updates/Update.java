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
    public final int ID;
    
    /**
     * Creates an update.
     * @param json JSON update from the Telegram servers.
     */
    public Update(JsonObject json){
        ID = json.getInt("update_id", 0);
    }
    
}
