/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.*;
import bot.replymarkup.ReplyMarkup;
import com.eclipsesource.json.JsonObject;

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
    
    /**
     * Converts this object to JSON.
     * @return This object in JSON.
     */
    public JsonObject toJson(){
        JsonObject j = new JsonObject();
        j.add("disable_notification", !notifications);
        j.add("disable_web_page_preview", !webPreview);
        if(markup != null)      j.add("reply_markup", markup.toJson());
        return j;
    }
    
    /**
     * Creates a Sendable object of this message.
     * @param m the message to send again
     * @return A Sendable object of a message.
     */
    public static Sendable newSendable(Message m){
        if(m instanceof TextMessage)            return new SendableText(((TextMessage) m).TEXT);
        else if(m instanceof PhotoMessage)      return new SendablePhoto((PhotoMessage) m);
        else if(m instanceof VideoMessage)      return new SendableVideo((VideoMessage) m);
        else if(m instanceof AudioMessage)      return new SendableAudio((AudioMessage) m);
        else
            throw new UnsupportedOperationException("This message type can not yet be converted as a Sendable : " + m.toString());
    }
    
    /**
     * The name of the method.
     * @return The name of the method.
     */
    public abstract String methodName();
}
