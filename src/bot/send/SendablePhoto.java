/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.PhotoMessage;
import bot.types.others.PhotoSize;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class SendablePhoto extends SendableFile {

    private final String ID;
    private String caption = null;
    
    /**
     * Creates a sendable photo message, using the URL to an image.
     * @param url 
     */
    public SendablePhoto(String url){
        ID = url;
    }
    
    /**
     * Creates a sendable photo message, using an image that was already sent to the bot.
     * @param photo the photo you want to send again
     */
    public SendablePhoto(PhotoMessage photo){
        this(photo.PHOTO[0].ID);
    }
    
    /**
     * Creates a sendable photo message, using an image that was already sent to the bot.
     * @param photo the photo you want to send again
     */
    public SendablePhoto(PhotoSize photo){
        ID = photo.ID;
    }
    
    /**
     * Adds a caption to the photo. Can be used even if the photo is a repost.
     * @param caption the caption, 0-200 characters.
     * @return This object, to allow method-chaining.
     */
    public SendablePhoto setCaption(String caption){
        if(caption.length() > 200)
            throw new IllegalArgumentException("The field 'caption' should not be longer than 200 characters ; found " + caption.length());
        this.caption = caption;
        return this;
    }
    
    @Override
    public String methodName() {
        return "sendPhoto";
    }
    
    @Override
    public JsonObject toJson(){
        JsonObject j = super.toJson();
        j.add("photo", ID);
        j.add("caption", caption);
        return j;
    }
    
}
