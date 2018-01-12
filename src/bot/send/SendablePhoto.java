/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.PhotoMessage;
import bot.utils.others.PhotoSize;
import com.eclipsesource.json.JsonObject;
import java.io.File;

/**
 *
 * @author CLOVIS
 */
public class SendablePhoto extends SendableUpload {

    private String ID;
    
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
    
    /**
     * Creates a sendable photo message, using a file that will be uploaded.
     * @param file the photo you want to upload
     */
    public SendablePhoto(File file){
        super(file);
    }
    
    @Override
    public String methodName() {
        return "sendPhoto";
    }
    
    @Override
    public JsonObject toJson(){
        return super.toJson();
    }

    @Override
    public String type() {
        return "photo";
    }

    @Override
    public String id() {
        return ID;
    }
    
}
