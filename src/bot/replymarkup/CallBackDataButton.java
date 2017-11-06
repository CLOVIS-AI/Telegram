/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class CallBackDataButton extends InlineButton {
    
    private final String callback;

    /**
     * Creates a Callback button.
     * @param text label text on the button
     * @param callback data to be sent in a callback query to the bot when the button is pressed, 1-64 bytes
     */
    public CallBackDataButton(String text, String callback) {
        super(text);
        this.callback = callback;
        
        if(callback.length() > 64 || callback.length() < 1)
            throw new IllegalArgumentException("'callback' should be 1-64 bytes long ; found : " + callback.length());
    }
    
    @Override
    public JsonObject toJson(){
        return super.toJson().add("callback_data", callback);
    }
}
