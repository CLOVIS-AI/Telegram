/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import bot.utils.others.PhotoSize;
import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

/**
 * Message is a photo.
 * @author CLOVIS
 */
public class PhotoMessage extends Message {

    /** Available sizes of the photo. */
    public final PhotoSize[] PHOTO;
    
    /**
     * Creates a Photo message.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException if 
     */
    public PhotoMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        
        JsonValue v = json.get("photo");
        if(v == null)
            throw new MandatoryFieldOmittedException("Field 'photo' should not be omitted", json);
        JsonArray ja = v.asArray();
        PHOTO = new PhotoSize[ja.size()];
        int i = 0;
        for(JsonValue j : ja){
            PHOTO[i] = new PhotoSize(ja.get(i).asObject());
            i++;
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " sent a photo ; " + PHOTO.length + " sizes (eg. " + PHOTO[0].ID + ").";
    }
    
}
