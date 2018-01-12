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

/**
 * A Chat photo was changed.
 * @author CLOVIS
 */
public class NewChatPhoto extends Message {
    
    /**
     * A Chat photo was changed to this value.
     */
    public final PhotoSize[] PHOTOS;
    
    /**
     * Creates a NewChatPhotoMessage object.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException see {@link Message#Message(minimaljson.JsonObject) super(json)}
     */
    public NewChatPhoto(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        JsonArray ja = json.get("new_chat_photo").asArray();
        PHOTOS = new PhotoSize[ja.size()];
        for(int i = 0; i < ja.size(); i++)
            PHOTOS[i] = new PhotoSize(ja.get(i).asObject());
    }
    
}
