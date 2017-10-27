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
    
    /** Unique identifier of this user. */
    public final long ID;
    
    /** Is this user a bot ? */
    public final boolean IS_BOT;
    
    /** First name of the user. */
    public final String FIRST_NAME;
    
    /** (Optional)<br>Last name of the user. */
    public final String LAST_NAME;
    
    /** (Optional)<br>The user's username. */
    public final String USERNAME;
    
    /** (Optional)<br><a href="https://en.wikipedia.org/wiki/IETF_language_tag">
     * IETF language tag</a> of the user's language. */
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
    
    @Override
    public String toString(){
        return (USERNAME == null ? FIRST_NAME : "@" + USERNAME) + " (" + ID + ")";
    }
}
