/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

import bot.messages.AudioMessage;
import minimaljson.JsonObject;

/**
 * Use this method to send audio files, if you want Telegram clients to display
 * them in the music player. Your audio must be in the .mp3 format. Bots can
 * currently send audio files of up to 50 MB in size, this limit may be changed
 * in the future.<br><br>
 * 
 * For sending voice messages, use the sendVoice method instead.
 * @author CLOVIS
 */
public class SendableAudio extends SendableFile {

    private String ID;
    private int duration = -1;
    private String performer = null;
    private String title = null;
    
    /**
     * Creates an audio message that you will be allowed to send.
     * @param url url of the audio file
     */
    public SendableAudio(String url){
        ID = url;
    }
    
    /**
     * Creates an audio message.
     * @param audio an audio file already sent to the bot
     */
    public SendableAudio(AudioMessage audio){
        ID = audio.FILE_ID;
    }
    
    /**
     * Specifies a duration to the file.
     * @param dur the duration of this audio file
     * @return This object, to allow method-chaining.
     */
    public SendableAudio duration(int dur){
        if(dur < 0)
            throw new IllegalArgumentException("The duration of an audio file should not be lesser than 0 seconds ; found " + dur);
        duration = dur;
        return this;
    }
    
    /**
     * Specifies the performer of this file.
     * @param perf the performer's name
     * @return This object, to allow method-chaining.
     */
    public SendableAudio performer(String perf){
        performer = perf;
        return this;
    }
    
    /**
     * Specifies the title of this file.
     * @param title the title
     * @return This object, to allow method-chaining.
     */
    public SendableAudio title(String title){
        this.title = title;
        return this;
    }
    
    @Override
    public String methodName() {
        return "sendAudio";
    }
    
    @Override
    public JsonObject toJson(){
        JsonObject j = super.toJson();
        j.add("audio", ID);
        if(duration != -1)      j.add("duration", duration);
        if(performer != null)   j.add("performer", performer);
        if(title != null)       j.add("title", title);
        return j;
    }
}
