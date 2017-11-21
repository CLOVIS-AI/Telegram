/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.VideoMessage;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class SendableVideo extends SendableFile {

    private String ID;
    private int duration = -1;
    private int width = -1, height = -1;
    
    /**
     * Creates a sendable video.
     * @param url url of the video
     */
    public SendableVideo(String url){
        ID = url;
    }
    
    /**
     * Creates a sendable video.
     * @param video the video you'd like to resend
     */
    public SendableVideo(VideoMessage video){
        ID = video.FILE_ID;
    }
    
    /**
     * Sets the duration of the video.
     * @param dur duration of the video
     * @return This object, to allow method-chaining.
     */
    public SendableVideo duration(int dur){
        if(dur < 0)
            throw new IllegalArgumentException("The duration cannot be negative.");
        duration = dur;
        return this;
    }
    
    /**
     * Sets the sizes of the video.
     * @param x width of the video
     * @param y height of the video
     * @return This object, to allow method-chaining.
     */
    public SendableVideo sizes(int x, int y){
        if(x < 0)
            throw new IllegalArgumentException("The width of the video cannot be negative.");
        if(y < 0)
            throw new IllegalArgumentException("The height of the video cannot be negative.");
        width = x;
        height = y;
        return this;
    }
    
    @Override
    public String methodName() {
        return "sendVideo";
    }
    
    @Override
    public JsonObject toJson(){
        JsonObject j = super.toJson();
        j.add("video", ID);
        if(duration != -1)
            j.add("duration", duration);
        if(width != -1)
            j.add("width", width);
        if(height != -1)
            j.add("height", height);
        return j;
    }
    
}
