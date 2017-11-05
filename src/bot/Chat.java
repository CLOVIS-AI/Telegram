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
public class Chat {
    
    /** Unique identifier of this chat. */
    public final long ID;
    /** Type of this chat.
     * @see Chat.Type */
    public final Type TYPE;
    /** (Optional)<br> Chat title. */
    public final String TITLE;
    /** (Optional : private, supergroup, channel)<br> Group username. */
    public final String USERNAME;
    /** (Optional : private chat)<br> First name of the user in a private chat. */
    public final String FIRST_NAME;
    /** (Optional : private chat and user's choice)<br> Last name of the user in a private chat. */
    public final String LAST_NAME;
    /** (Optional)<br> True if a group has "All members are admins" enabled. */
    public final boolean ALL_MEMBERS_ADMIN;
    // @TODO : PHOTO
    /** (Optional : only with Bot.getChat)<br> Description of the group for supergroups and channels. */
    public final String DESCRIPTION;
    /** (Optional : only with Bot.getChat, supergroup or channel)<br> Invite link to this chat. */
    public final String INVITE_LINK;
    // @TODO : pinned message
    
    /**
     * Creates a Chat object.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException If the field ID is omitted
     */
    public Chat(JsonObject json) throws MandatoryFieldOmittedException{
        ID =                json.getLong("id", 0);
        TYPE =              Type.fromString(json.getString("type", null));
        TITLE =             json.getString("title", null);
        USERNAME =          json.getString("username", null);
        FIRST_NAME =        json.getString("first_name", null);
        LAST_NAME =         json.getString("last_name", null);
        ALL_MEMBERS_ADMIN = json.getBoolean("all_members_are_administrators", false);
        DESCRIPTION =       json.getString("description", null);
        INVITE_LINK =       json.getString("invite_link", null);
        
        if(ID == 0){
            throw new MandatoryFieldOmittedException("Field ID is mandatory.", json);
        }
    }
    
    /**
     * Creates an empty Chat object, to be used to make a request (ex. to hardcode the destination).
     * @param ID unique identifier of this Chat
     */
    public Chat(long ID){
        this.ID = ID;
        TYPE = null;
        TITLE = null;
        USERNAME = null;
        FIRST_NAME = null;
        LAST_NAME = null;
        ALL_MEMBERS_ADMIN = false;
        DESCRIPTION = null;
        INVITE_LINK = null;
    }
    
    @Override
    public String toString(){
        return (TITLE == null ? "No_Title" : TITLE) + " (" + (USERNAME == null ? ID : "@" + USERNAME) + ")";
    }
    
    /**
     * The type of a Chat object.
     * @see #PRIVATE
     * @see #GROUP
     * @see #SUPERGROUP
     * @see #CHANNEL
     */
    public enum Type{
        /** This chat is private : the bot is talking one-on-one to a user.
          * In this case, the fields {@link #FIRST_NAME first name} and
          * {@link #LAST_NAME last name} may be used to get info on the user.*/
        PRIVATE,
        /** This chat is a group. */
        GROUP,
        /** This chat is a supergroup. */
        SUPERGROUP,
        /** This chat is a channel. */
        CHANNEL;
        
        /**
         * Converts a string to a Chat type enum instance.
         * @param type the type of a Chat object. Can be either one of <code>private</code>,
         *             <code>group</code>, <code>supergroup</code> or <code>channel</code>.
         * @return The corresponding Type instance.
         */
        public static Type fromString(String type){
            switch(type){
                case "private":     return PRIVATE;
                case "group":       return GROUP;
                case "supergroup":  return SUPERGROUP;
                case "channel":     return CHANNEL;
                default:            throw new IllegalArgumentException(type + " is not an allowed argument.");
            }
        }
    }
}
