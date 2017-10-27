/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;
import types.others.PhotoSize;

/**
 *
 * @author CLOVIS
 */
public class DocumentMessage extends FileMessage {

    /** (Optional)<br>Document thumbnail as defined by sender. */
    public final PhotoSize THUMBNAIL;
    
    /** (Optional)<br>Original filename as defined by the sender. */
    public final String NAME;
    
    /**
     * Creates a document.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException 
     */
    public DocumentMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json, json.get("document").asObject());
        
        JsonObject ja = json.get("document").asObject();
        if(ja.get("thumb") != null)
            THUMBNAIL = new PhotoSize(ja.get("thumb").asObject());
        else
            THUMBNAIL = null;
        
        NAME = ja.getString("file_name", null);
    }
    
    @Override
    public String toString(){
        return super.toString() + " sent a document of type " + MIME_TYPE + " : " + NAME;
    }
    
}
