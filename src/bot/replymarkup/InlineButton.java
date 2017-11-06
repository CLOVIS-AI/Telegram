/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import minimaljson.JsonObject;
import minimaljson.JsonValue;

/**
 *
 * @author CLOVIS
 */
public abstract class InlineButton {
    
    private final String TEXT;
    
    /**
     * Creates an InlineKeyboardButton
     * @param text label text on the button
     */
    public InlineButton(String text){
        TEXT = text;
        
        if(text == null)
            throw new IllegalArgumentException("'text' should not be null.");
    }
    
    /**
     * Serializes this button as a JSON object.
     * @return The serialized version of this object.
     */
    public JsonObject toJson(){
        return new JsonObject().add("text", TEXT);
    }
}
