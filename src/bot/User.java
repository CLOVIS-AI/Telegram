/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import com.eclipsesource.json.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class User extends Chat {
    
    /** Is this user a bot ? */
    public final boolean IS_BOT;
    
    /** First name of the user. */
    public final String FIRST_NAME;
    
    /** (Optional)<br>Last name of the user. */
    public final String LAST_NAME;
    
    /** (Optional)<br><a href="https://en.wikipedia.org/wiki/IETF_language_tag">
     * IETF language tag</a> of the user's language. */
    public final String LANGUAGE;
    
    /**
     * Creates a User object.
     * @param json the source of the data as provided by Telegram
     * @throws MandatoryFieldOmittedException if the ID or first name are omitted.
     */
    public User(JsonObject json) throws MandatoryFieldOmittedException{
        super(json);
        IS_BOT =     json.getBoolean("is_bot", false);
        FIRST_NAME = json.getString("first_name", null);
        LAST_NAME =  json.getString("last_name", null);
        LANGUAGE =   json.getString("language_code", null);
        
        if(ID == 0 || FIRST_NAME == null){
            throw new MandatoryFieldOmittedException("Either the ID or the first name was omitted.", json);
        }
    }
    
    /**
     * Creates an empty User object. <b>DO NOT USE OUTSIDE OF THE SEND... METHODS !</b>
     * @param ID ID of this user
     * @deprecated Use {@link Bot#getChat(long) Bot.getChat(ID)} instead.
     */
    public User(long ID){
        super(ID);
        IS_BOT = false;
        FIRST_NAME = null;
        LAST_NAME = null;
        LANGUAGE = null;
    }
    
    @Override
    public String toString(){
        return (USERNAME == null ? FIRST_NAME : "@" + USERNAME) + " (" + ID + ")";
    }
    
    /**
     * Gets a link usable in {@link Bot#send(String, bot.Chat) send}
     * that links to this user, using the appropriate markdown style.
     * @param display what does the link display
     * @param markdown the markdown style you specified in {@link Bot#send(String, bot.Chat) send}
     * @return A usable link.
     */
    public String toMention(String display, Bot.ParseMode markdown){
        if(markdown != null)
            switch (markdown) {
            case MARKDOWN:
                return "[" + display + "](tg://user?id=" + ID + ")";
            case HTML:
                return "<a href=\"tg://user?id=" + ID + "\">" + display + "</a>";
            default:
                throw new IllegalArgumentException("The argument 'markdown' should be either MARKDOWN or HTML ; found : " + markdown);
        }else{
            throw new NullPointerException("The parameter 'markdown' cannot be null.");
        }
    }
    
    /**
     * Gets a link usable in {@link Bot#send(bot.send.Sendable, bot.Chat) send}
     * that links to this user, using the appropriate markdown style.<br>
     * The text of the link will be the first name of the user.
     * @param markdown the markdown style you specified in {@link Bot#send(bot.send.Sendable, bot.Chat) send}
     * @return A usable link.
     */
    public String toMention(Bot.ParseMode markdown){
        return toMention(FIRST_NAME, markdown);
    }
}
