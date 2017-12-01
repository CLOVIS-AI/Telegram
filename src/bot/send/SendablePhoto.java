/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.PhotoMessage;
import bot.utils.others.PhotoSize;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class SendablePhoto extends SendableFile {

    private final String ID;
    
    /**
     * Creates a sendable photo message, using the URL to an image.
     * @param url the photo you want to send
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
    
    @Override
    public String methodName() {
        return "sendPhoto";
    }
    
    @Override
    public JsonObject toJson(){
        JsonObject j = super.toJson();
        j.add("photo", ID);
        return j;
    }
    
}
