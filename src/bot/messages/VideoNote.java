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
 * This object represents a video message.
 * @author CLOVIS
 */
public class VideoNote extends FileMessage {

    /** (Optional)<br>Video thumbnail. */
    public final PhotoSize THUMBNAIL;
    
    /** Video notes are circles, therefore they have <code>width == height</code>.
     *  Instead of having two variables, we only use <code>LENGTH == width == height</code>.*/
    public final int LENGTH;
    
    /** Duration of the video as defined by sender (seconds). */
    public final int DURATION;
    
    /**
     * Creates a VideoNote message. 
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException if any mandatory field is omitted
     */
    public VideoNote(JsonObject json) throws MandatoryFieldOmittedException {
        super(json, json.get("video_note").asObject());
        
        JsonObject ja = json.get("video_note").asObject();
        
        if(ja.get("thumb") != null)
            THUMBNAIL = new PhotoSize(ja.get("thumb").asObject());
        else
            THUMBNAIL = null;
        
        LENGTH = ja.getInt("length", -1);
        DURATION = ja.getInt("duration", -1);
        
        if(LENGTH == -1 || DURATION == -1)
            throw new MandatoryFieldOmittedException("Fields 'length' and 'duration' should not be omitted", json.get("video_note"));
    }
    
}
