/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.send;

/**
 *
 * @author CLOVIS
 */
public class SendablePhoto extends SendableFile {

    @Override
    public String methodName() {
        return "sendPhoto";
    }
    
}
