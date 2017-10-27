/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import bot.User;
import minimaljson.JsonObject;

/**
 * @author CLOVIS
 */
public class MessageEntity {
    
    /** The type of this entity.
     * @see Type
     */
    public final Type TYPE;
    /** Offset in UTF-16 code units to the start of the entity. */
    public final int OFFSET;
    /** Length of the entity in UTF-16 code units. */
    public final int LENGTH;
    /** Actual text contained in the entity. */
    public final String TEXT;
    /** (Optional : {@link Type#TEXT_URL})<br> The link that will be openned. */
    public final String URL;
    /** (Optional : {@link Type#MENTION})<br> The user that is mentionned. */
    public final User USER;
    
    /**
     * Creates a MessageEntity object.
     * @param json source of the content, as provided by the Telegram servers.
     * @param fullText text of the full {@link TextMessage}
     * @throws MandatoryFieldOmittedException if <code>offset</code>
     *                                        or <code>length</code> are omitted.
     */
    public MessageEntity(JsonObject json, String fullText) throws MandatoryFieldOmittedException{
        TYPE = Type.fromString(json.getString("type", null));
        OFFSET = json.getInt("offset", -1);
        LENGTH = json.getInt("length", -1);
        TEXT = fullText.substring(OFFSET, OFFSET+LENGTH);
        URL = json.getString("url", null);
        if(json.get("user") != null)
            USER = new User(json.get("user").asObject());
        else
            USER = null;
        
        if(OFFSET == -1 || LENGTH == -1)
            throw new MandatoryFieldOmittedException("Offset and Length are mandatory fields.", json);
    }
    
    /**
     * The type of the entity.
     * @see #USERNAME
     * @see #MENTION
     * @see #HASHTAG
     * @see #BOT_COMMAND
     * @see #URL
     * @see #TEXT_URL
     * @see #EMAIL
     * @see #BOLD
     * @see #ITALIC
     * @see #CODE
     * @see #CODE_BLOCK
     */
    public enum Type{
        /** Username of a user, if they have one.
         * @see #MENTION If they do not have a username*/
        USERNAME,
        /** Name of a user if they do not have a username.
         * @see #USERNAME If they have a username*/
        MENTION,
        /** A hastag. */
        HASHTAG,
        /** A bot command. */
        BOT_COMMAND,
        /** A URL. */
        URL,
        /** An email address. */
        EMAIL,
        /** A <b>bold</b> text. */
        BOLD,
        /** An <i>italic</i> text. */
        ITALIC,
        /** Preformatted code line.
         * @see #CODE_BLOCK Multiple lines
         */
        CODE,
        /** Preformatted code block (multiple lines).
         * @see #CODE Only one line
         */
        CODE_BLOCK,
        /** Clickable text URL. */
        TEXT_URL;
        
        /**
         * Converts a String from the Telegram servers to a Type object.
         * @param type the type according to the Telegram servers
         * @return An element of the Type enum.
         * @see MessageEntity.Type
         */
        public static Type fromString(String type){
            switch(type){
                case "mention":     return USERNAME;
                case "hashtag":     return HASHTAG;
                case "bot_command": return BOT_COMMAND;
                case "url":         return URL;
                case "email":       return EMAIL;
                case "bold":        return BOLD;
                case "italic":      return ITALIC;
                case "code":        return CODE;
                case "pre":         return CODE_BLOCK;
                case "text_link":   return TEXT_URL;
                case "text_mention":return MENTION;
                default: throw new IllegalArgumentException("The content of the String is invalid : " + type);
            }
        }
    }
}
