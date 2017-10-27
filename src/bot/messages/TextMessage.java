/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonArray;
import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class TextMessage extends Message {

    /** Actual UTF-8 text of the message, 0-4096 characters. */
    public final String TEXT;
    
    /** (Optional)<br>Special entities like usernames, URLs, bot commands that appear in the text.
     <br><br>If not present, initialized as an empty array (<code>ENTITIES.length = 0</code>)*/
    public final MessageEntity[] ENTITIES;
    
    public TextMessage(JsonObject json) throws MandatoryFieldOmittedException {
        super(json);
        TEXT = json.getString("text", null);
        JsonArray entities;
        try{
            entities = json.get("entities").asArray();
        }catch(NullPointerException e){
            entities = null;
        }
        if(entities != null){
            ENTITIES = new MessageEntity[entities.size()];
            for(int i = 0; i < entities.size(); i++){
                ENTITIES[i] = new MessageEntity(entities.get(i).asObject(), TEXT);
            }
        }else{
            ENTITIES = new MessageEntity[0];
        }
    }
    
    @Override
    public String toString(){
        return super.toString() + " : " + TEXT.replace('\n', '\\');
    }
}
