/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;

/**
 * This message represents an audio file to be treated as music by the Telegram clients.
 * @author CLOVIS
 */
public class AudioMessage extends FileMessage {

    /** Duration of the audio in seconds as defined by the sender. */
    public final int DURATION;
    /** (Optional)<br>Performer of the audio as defined by sender or by audio tags. */
    public final String PERFORMER;
    /** (Optional)<br>Title of the audio as defined by sender or by audio tags. */
    public final String TITLE;
    
    public AudioMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        DURATION = json.getInt("duration", -1);
        PERFORMER = json.getString("performer", null);
        TITLE = json.getString("title", null);
    }
    
}
