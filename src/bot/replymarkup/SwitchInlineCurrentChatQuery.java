/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import com.eclipsesource.json.JsonObject;

/**
 *
 * @author CLOVIS
 */
public class SwitchInlineCurrentChatQuery extends InlineButton {

    private final String data;
    
    /**
     * Pressing the button will insert the bot‘s username and the specified
     * inline query in the current chat's input field. Can be empty, in which
     * case only the bot’s username will be inserted.<br><br>This offers a quick
     * way for the user to open your bot in inline mode in the same chat – good
     * for selecting something from multiple options.
     * @param text label of the text button
     * @param switchQuery text of the query, can be empty (<code>""</code> or <code>null</code>).
     */
    public SwitchInlineCurrentChatQuery(String text, String switchQuery) {
        super(text);
        data = switchQuery == null ? "" : switchQuery;
    }
    
    @Override
    public JsonObject toJson(){
        return super.toJson().add("switch_inline_query_current_chat", data);
    }
}
