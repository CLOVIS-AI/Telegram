/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.utils.stickers;

import bot.MandatoryFieldOmittedException;
import bot.utils.others.PhotoSize;
import com.eclipsesource.json.JsonObject;

/**
 * This object represents a sticker.
 * @author CLOVIS
 */
public class Sticker {
    
    /** Unique identifier for this file. */
    public final String ID;
    
    /** Sticker width. */
    public final int WIDTH;
    
    /** Sticker height. */
    public final int HEIGHT;
    
    /** (Optional)<br>Sticker thumbnail in <code>.webp</code> or 
     * <code>.jpg format.</code> */
    public final PhotoSize THUMBNAIL;
    
    /** (Optional)<br>Emoji associated with this sticker. */
    public final String EMOJI;
    
    /** (Optional)<br>Name of the sticker set to which the sticker belongs. */
    public final String SET_NAME;
    
    /** (Optional : mask stickers)<br>The position where the mask should be placed. */
    public final MaskPosition MASK_POSITION;
    
    /** (Optional)<br>File size. */
    public final int SIZE;
    
    /**
     * Creates a sticker from JSON data.
     * @param json data from the Telegram servers
     * @exception MandatoryFieldOmittedException if 'file_id', 'width', 'height' are omitted.
     */
    public Sticker(JsonObject json) throws MandatoryFieldOmittedException {
        ID = json.getString("file_id", null);
        WIDTH = json.getInt("width", -1);
        HEIGHT = json.getInt("height", -1);
        EMOJI = json.getString("emoji", null);
        SET_NAME = json.getString("set_name", null);
        SIZE = json.getInt("file_size", 0);
        
        THUMBNAIL = json.get("thumb") != null ?
                new PhotoSize(json.get("thumb").asObject())
              : null;
        MASK_POSITION = json.get("mask_position") != null ?
                new MaskPosition(json.get("mask_position").asObject())
              : null;
        
        if(ID == null || WIDTH == -1 || HEIGHT == -1)
            throw new MandatoryFieldOmittedException("'file_id', 'width' and 'height' should not be omitted", json);
    }
    
    @Override
    public String toString(){
        return ID + " in " + SET_NAME;
    }
}
