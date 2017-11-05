/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;
import bot.types.others.PhotoSize;

/**
 *
 * @author CLOVIS
 */
public class VideoMessage extends FileMessage {

    /** Video width as defined by sender. */
    public final int WIDTH;
    
    /** Video height as defined by sender. */
    public final int HEIGHT;
    
    /** Duration of the video (seconds) as defined by sender. */
    public final int DURATION;
    
    /** (Optional)<br>Video thumbnail. */
    public final PhotoSize THUMBNAIL;
    
    /**
     * Creates a VideoMessage.
     * @param json data from the Telegram servers.
     * @throws MandatoryFieldOmittedException if any mandatory field is omitted
     */
    public VideoMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json, json.get("video").asObject());
        
        JsonObject ja = json.get("video").asObject();
        WIDTH = ja.getInt("width", -1);
        HEIGHT = ja.getInt("height", -1);
        DURATION = ja.getInt("duration", -1);
        
        if(WIDTH == -1 || HEIGHT == -1 || DURATION == -1)
            throw new MandatoryFieldOmittedException("Fields 'width', 'height' and 'duration' should not be omitted", json);
        
        if(ja.get("thumb") != null)
            THUMBNAIL = new PhotoSize(ja.get("thumb").asObject());
        else
            THUMBNAIL = null;
    }
    
    
    
}
