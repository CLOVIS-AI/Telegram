/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.Bot.ParseMode;
import com.eclipsesource.json.JsonObject;

/**
 * A text message.
 * @author CLOVIS
 */
public class SendableText extends Sendable {

    private String text;
    private ParseMode mode = null;
    
    /**
     * Sets this message as a text.
     * @param text the written text
     */
    public SendableText(String text){
        this.text = text;
    }
    
    /**
     * Sets the parse mode.
     * @param mode the mode
     * @return this object, to allow method-chaining.
     */
    public SendableText parseMode(ParseMode mode){
        this.mode = mode;
        return this;
    }
    
    @Override
    public JsonObject toJson() {
        JsonObject j = super.toJson();
        j.add("text", text);
        if(mode != null)    j.add("parse_mode", mode.toString());
        return j;
    }

    @Override
    public String methodName() {
        return "sendMessage";
    }
    
}
