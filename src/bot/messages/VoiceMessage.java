/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class VoiceMessage extends FileMessage {

    /** Duration of the audio in seconds, as defined by the user. */
    public final int DURATION;
    
    /**
     * Creates a voice message.
     * @param json data of this object from the Telegram servers
     * @throws MandatoryFieldOmittedException 
     */
    public VoiceMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json, json.get("voice").asObject());
        
        DURATION = json.get("voice").asObject().getInt("duration", -1);
        
        if(DURATION == -1)
            throw new MandatoryFieldOmittedException("Field 'duration' should not be omitted", json);
    }
    
}
