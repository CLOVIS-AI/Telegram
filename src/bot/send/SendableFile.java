/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import minimaljson.JsonObject;

/**
 * A file that can be sent by the bot.
 * @author CLOVIS
 */
public abstract class SendableFile extends Sendable {
    
    private String caption = null;
    
    /**
     * Adds a caption to the photo. Can be used even if the photo is a repost.
     * @param caption the caption, 0-200 characters.
     * @return This object, to allow method-chaining.
     */
    public SendableFile setCaption(String caption){
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
}
