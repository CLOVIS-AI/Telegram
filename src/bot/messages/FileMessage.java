/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import com.eclipsesource.json.JsonObject;

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
     * @param message source of the content, as provided by the Telegram servers
     * @param json same, but targetted on the file
     * @throws MandatoryFieldOmittedException if the field FILE_ID is omitted
     */
    public FileMessage(JsonObject message, JsonObject json) throws MandatoryFieldOmittedException {
        super(message);
        
        FILE_ID = json.getString("file_id", null);
        MIME_TYPE = json.getString("mime_type", null);
        FILE_SIZE = json.getInt("file_size", 0);
        
        if(FILE_ID == null)
            throw new MandatoryFieldOmittedException("The fileID is a mandatory field", json);
    }
    
    @Override
    public String toString(){
        return super.toString() + " [" + ID + "]" ;
    }
}
