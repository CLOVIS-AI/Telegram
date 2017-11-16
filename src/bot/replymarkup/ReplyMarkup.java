/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import minimaljson.JsonValue;

/**
 * This object can be used to specify buttons to the reply.
 * @author CLOVIS
 */
public interface ReplyMarkup {
    
    /**
     * Serializes this object to JSON.
     * @return This object in the JSON format.
     */
    public JsonValue toJson();
    
}
