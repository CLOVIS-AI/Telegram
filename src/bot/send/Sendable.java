/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.*;
import bot.replymarkup.ReplyMarkup;
import minimaljson.JsonObject;
import minimaljson.JsonValue;

/**
 * This class 
 * @author CLOVIS
 */
public abstract class Sendable {
    
    private boolean notifications = true;
    private boolean webPreview = true;
    private ReplyMarkup markup = null;
    
    /**
     * Sends the message silently. The users will recieve a notification with no sounds.
     * @return this object to allow method-chaining.
     */
    public Sendable disableNotifications(){
        notifications = false;
        return this;
    }
    
    /**
     * Disables links preview for links in this message.
     * @return this object to allow method-chaining.
     */
    public Sendable disableWebPreview(){
        webPreview = false;
        return this;
    }
    
    /**
     * Sets the markup of this message.
     * @param markup the markup you are interested in.
     * @return this object to allow method-chaining.
     */
    public Sendable markup(ReplyMarkup markup){
        this.markup = markup;
        return this;
    }
    
    public JsonObject toJson(){
        JsonObject j = new JsonObject();
        j.add("disable_notification", !notifications);
        j.add("disable_web_page_preview", !webPreview);
        if(markup != null)      j.add("reply_markup", markup.toJson());
        return j;
    }
    
    public static Sendable newSendable(Message m){
        if(m instanceof TextMessage)    return new SendableText(((TextMessage) m).TEXT);
        else
            throw new UnsupportedOperationException("This message type can not yet be converted as a Sendable : " + m.toString());
    }
    
    public abstract String methodName();
}
