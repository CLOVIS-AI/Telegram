/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import minimaljson.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class SwitchInlineQuery extends InlineButton {

    private String data;
    
    /**
     * Pressing the button will prompt the user to select one of their chats,
     * open that chat and insert the bot‘s username and the specified inline
     * query in the input field. Can be empty, in which case just the bot’s
     * username will be inserted.
     * @param text label of the button
     * @param switchQuery the query, can be empty (<code>""</code> or <code>null</code>) 
     */
    public SwitchInlineQuery(String text, String switchQuery) {
        super(text);
        data = switchQuery;
        
        if(data == null)
            data = "";
    }
    
    @Override
    public JsonObject toJson(){
        return super.toJson().add("switch_inline_query", data);
    }
}
