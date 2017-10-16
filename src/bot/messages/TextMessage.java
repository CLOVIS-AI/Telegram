/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonArray;
import minimaljson.JsonObject;
import minimaljson.JsonValue;

/**
 *
 * @author CLOVIS
 */
public class TextMessage extends Message {

    public final String TEXT;
    public final MessageEntity[] ENTITIES;
    
    public TextMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        TEXT = json.getString("text", null);
        JsonArray entities = json.get("entities").asArray();
        ENTITIES = new MessageEntity[entities.size()];
        for(int i = 0; i < entities.size(); i++){
            ENTITIES[i] = new MessageEntity(entities.get(i).asObject(), TEXT);
        }
    }
    
}
