/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.Message;
import bot.replymarkup.ReplyMarkup;

/**
 * This class 
 * @author CLOVIS
 */
public abstract class Sendable {
    
    private boolean notifications = true;
    private boolean webPreview = true;
    private Message replyTo = null;
    private ReplyMarkup markup = null;
    
    public Sendable disableNotifications(){
        notifications = false;
        return this;
    }
    
    public Sendable disableWebPreview(){
        webPreview = false;
        return this;
    }
    
    public Sendable reply(Message reply){
        replyTo = reply;
        return this;
    }
    
    public Sendable markup(ReplyMarkup markup){
        this.markup = markup;
        return this;
    }
    
}
