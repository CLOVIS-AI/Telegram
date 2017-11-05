/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.types.others;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class PhotoSize {
    
    /** Unique identifier for this file. */
    public final String ID;
    
    /** Photo width. */
    public final int WIDTH;
    
    /** Photo height. */
    public final int HEIGHT;
    
    /** (Optional)<br>File size. */
    public final int SIZE;
    
    /**
     * 
     * @param json data from the Telegram servers
     * @exception MandatoryFieldOmittedException if mandatory fields are omitted
     */
    public PhotoSize(JsonObject json) throws MandatoryFieldOmittedException {
        ID = json.getString("file_id", null);
        WIDTH = json.getInt("width", 0);
        HEIGHT = json.getInt("height", 0);
        SIZE = json.getInt("file_size", 0);
        
        if(ID == null || WIDTH == 0 || HEIGHT == 0)
            throw new MandatoryFieldOmittedException("file_id, width or height was omitted.", json);
    }
    
}
