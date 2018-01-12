/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import com.eclipsesource.json.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class UrlButton extends InlineButton {

    private final String URL;
    
    /**
     * Creates a URL inline button.
     * @param text label text on the button
     * @param URL HTTP url to be opened when button is pressed
     */
    public UrlButton(String text, String URL) {
        super(text);
        this.URL = URL;
        
        if(URL == null)
            throw new IllegalArgumentException("'URL' should not be null");
    }
    
    @Override
    public JsonObject toJson(){
        return super.toJson().add("url", URL);
    }
}
