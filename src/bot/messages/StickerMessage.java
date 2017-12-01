/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import bot.utils.stickers.Sticker;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class StickerMessage extends Message {

    /** Information about the sticker. */
    public final Sticker STICKER;
    
    /**
     * Creates a StickerMessage from the Telegram servers.
     * @param json data from the Telegram servers
     * @throws MandatoryFieldOmittedException see {@link Sticker#Sticker(minimaljson.JsonObject) Sticker} for more informations
     */
    public StickerMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        STICKER = new Sticker(json.get("sticker").asObject());
    }
    
    @Override
    public String toString(){
        return super.toString() + " sent a sticker : " + STICKER.toString();
    }
}
