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
    /** (Optional)<br> True if a group has "All members are admins" enabled. */
    public final boolean ALL_MEMBERS_ADMIN;
    /** (Optional : only with {@link Bot#getChat(bot.Chat) getChat})<br>Chat photo.<br><br>
     This field is not available through updates. You can use {@link Bot#getPhotos(bot.Chat)} to get it.*/
    public final Photo PHOTO;
    /** (Optional : only with Bot.getChat)<br> Description of the group for supergroups and channels. */
    public final String DESCRIPTION;
    /** (Optional : only with Bot.getChat, supergroup or channel)<br> Invite link to this chat. */
    public final String INVITE_LINK;
    // @TODO : pinned message
    
    /**
     * Creates a Chat object.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException If the field ID is omitted
     * @deprecated
     */
    public Chat(JsonObject json) throws MandatoryFieldOmittedException{
        ID =                json.getLong("id", 0);
        TYPE =              Type.fromString(json.getString("type", null));
        TITLE =             json.getString("title", null);
        USERNAME =          json.getString("username", null);
        ALL_MEMBERS_ADMIN = json.getBoolean("all_members_are_administrators", false);
        DESCRIPTION =       json.getString("description", null);
        INVITE_LINK =       json.getString("invite_link", null);
        
        if(json.get("photo") != null)
            PHOTO = new Photo(json.get("photo").asObject());
        else
            PHOTO = null;
        
        if(ID == 0){
            throw new MandatoryFieldOmittedException("Field ID is mandatory.", json);
        }
    }
    
    /**
     * Creates a Chat object.
     * @param json data from the Telegram servers
     * @return The created Chat. Might be a User.
     * @throws MandatoryFieldOmittedException If the field ID is omitted
     */
    public static Chat newChat(JsonObject json) throws MandatoryFieldOmittedException{
        if(json.getString("type", null).equals("private"))
            return new User(json);
        else
            return new Chat(json);
    }
    
    /**
     * Creates an empty Chat object.
     * @param ID unique identifier of this Chat
     * @deprecated Use {@link Bot#getChat(long) Bot.getChat(ID)} instead.
     */
    public Chat(long ID){
        this.ID = ID;
        TYPE = null;
        TITLE = null;
        USERNAME = null;
        ALL_MEMBERS_ADMIN = false;
        DESCRIPTION = null;
        INVITE_LINK = null;
        PHOTO = null;
    }
    
    @Override
    public String toString(){
        return (TITLE == null ? "" : TITLE + " (") + (USERNAME == null ? ID : "@" + USERNAME) + (TITLE != null ? ")" : "");
    }
    
    /**
     * Casts this chat as a user, to get the user's informations.
     * @return This chat as a user object.
     * @throws IllegalStateException this method should only be called if this chat is a private chat.
     *         You can know if this is the case by checking {@link #TYPE} and {@link Type#PRIVATE}.
     */
    public User asUser(){
        if(TYPE == Type.PRIVATE)
            return (User) this;
        else
            throw new IllegalStateException("The method asUser should only be called when Chat.TYPE == Type.PRIVATE. Here, Chat.TYPE == " + TYPE.toString());
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
            if(type == null)    return null;
            switch(type){
                case "private":     return PRIVATE;
                case "group":       return GROUP;
                case "supergroup":  return SUPERGROUP;
                case "channel":     return CHANNEL;
                default:            throw new IllegalArgumentException(type + " is not an allowed argument.");
            }
        }
    }
    
    public class Photo{
        /**
         * Unique file identifier of small (160x160) chat photo. This file_id can be used only for photo download.
         */
        public final String SMALL;
        
        /**
         * Unique file identifier of big (640x640) chat photo. This file_id can be used only for photo download.
         */
        public final String BIG;
        
        public Photo(JsonObject json){
            SMALL = json.getString("small_file_id", null);
            BIG = json.getString("big_file_id", null);
        }
    }
}
