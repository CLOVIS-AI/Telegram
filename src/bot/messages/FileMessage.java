/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;

/**
 * The basic informations a message containing a File always has.
 * @author CLOVIS
 */
public abstract class FileMessage extends Message {

    /** Unique identifier for this file. */
    public final String FILE_ID;
    /** (Optional)<br> MIME type as defined by sender. */
    public final String MIME_TYPE;
    /** (Optional)<br> File size. */
    public final int FILE_SIZE;
    
    /**
     * Creates a FileMessage object.
     * @param json source of the content, as provided by the Telegram servers
     * @throws MandatoryFieldOmittedException if the field FILE_ID is omitted
     */
    public FileMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        FILE_ID = json.getString("file_id", null);
        MIME_TYPE = json.getString("mime_type", null);
        FILE_SIZE = json.getInt("file_size", 0);
        
        if(FILE_ID == null)
            throw new MandatoryFieldOmittedException("The file ID is a mandatory field.");
    }
    
}
