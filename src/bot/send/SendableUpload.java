/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import java.io.File;
import minimaljson.JsonObject;

/**
 * A file that can be sent by the bot.
 * @author CLOVIS
 */
public abstract class SendableUpload extends Sendable {
    
    private String caption = null;
    private File file = null;
    
    /**
     * Creates a new Sendable File.
     */
    protected SendableUpload(){
        super();
    }
    
    /**
     * Creates a new Sendable File.
     * @param upload the file you want to upload
     */
    protected SendableUpload(File upload){
        file = upload;
    }
    
    /**
     * Adds a caption to the photo. Can be used even if the photo is a repost.
     * @param caption the caption, 0-200 characters.
     * @return This object, to allow method-chaining.
     */
    public SendableUpload caption(String caption){
        if(caption.length() > 200)
            throw new IllegalArgumentException("The field 'caption' should not be longer than 200 characters ; found " + caption.length());
        this.caption = caption;
        return this;
    }
    
    @Override
    public JsonObject toJson(){
        if(caption == null)
            return super.toJson();
        else
            return super.toJson().add("caption", caption);
    }
    
    /**
     * Is this file a new upload ?
     * @return <code>true</code> if it is a new upload.
     */
    public boolean isNewUpload(){
        return file != null;
    }
    
    /**
     * Gets the type of the file : photo, audio, ...
     * @return The type of the file.
     */
    public abstract String type();
    
    /**
     * Gets the ID of the file.
     * @return The ID of the file.
     */
    public abstract String id();
    
    /**
     * Gets the file (if this is an upload !)
     * @return This file
     */
    public File file(){
        return file;
    }
}
