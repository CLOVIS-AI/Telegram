/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class User {
    
    public final long ID;
    public final boolean IS_BOT;
    public final String FIRST_NAME;
    public final String LAST_NAME;
    public final String USERNAME;
    public final String LANGUAGE;
    
    /**
     * Creates a User object.
     * @param json the source of the data as provided by Telegram
     * @throws MandatoryFieldOmittedException if the ID or first name are omitted.
     */
    public User(JsonObject json) throws MandatoryFieldOmittedException{
        ID =         json.getLong("id", 0);
        IS_BOT =     json.getBoolean("is_bot", false);
        FIRST_NAME = json.getString("first_name", null);
        LAST_NAME =  json.getString("last_name", null);
        USERNAME =   json.getString("username", null);
        LANGUAGE =   json.getString("language_code", null);
        
        if(ID == 0 || FIRST_NAME == null){
            throw new MandatoryFieldOmittedException("Either the ID or the first name was omitted.", json);
        }
    }
    
}
