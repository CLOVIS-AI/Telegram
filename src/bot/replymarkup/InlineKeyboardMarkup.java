/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.replymarkup;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonValue;

/**
 *
 * @author CLOVIS
 */
public class InlineKeyboardMarkup implements ReplyMarkup {
    
    private final InlineButton[][] keyboard;
    
    public InlineKeyboardMarkup(int w, int h){
        keyboard = new InlineButton[w][h];
    }
    
    public void set(int x, int y, InlineButton button){
        keyboard[x][y] = button;
    }
    
    public void set(int x, int y, String text, Type type, String str){
        switch(type){
            case URL:                   set(x, y, new UrlButton(text, str)); break;
            case CALLBACK:              set(x, y, new CallBackDataButton(text, str)); break;
            case SWITCH_INLINE:         set(x, y, new SwitchInlineQuery(text, str)); break;
            case SWITCH_INLINE_CURRENT: set(x, y, new SwitchInlineCurrentChatQuery(text, str)); break;
        }
    }
    
    @Override
    public JsonValue toJson(){
        JsonArray j = new JsonArray();
        for(InlineButton[] buttonArray : keyboard){
            JsonArray ja = new JsonArray();
            for(InlineButton button : buttonArray){
                ja.add(button.toJson());
            }
            j.add(ja);
        }
        return j;
    }
    
    public static enum Type{
        URL,
        CALLBACK,
        SWITCH_INLINE,
        SWITCH_INLINE_CURRENT
    }
}
