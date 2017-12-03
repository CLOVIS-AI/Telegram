/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.messages;

/**
 * This class represents a command.
 * @author CLOVIS
 */
public abstract class RegisteredCommand{
    
    private final String command;
    private String separator = " ";
    
    /**
     * Creates a command you will be able to register.
     * @param name the command itself ; example : "getInfos"
     */
    public RegisteredCommand(String name){
        command = name;
    }
    
    /**
     * What should be used to separate arguments in the reply ?
     * <p>By default, <code>s=" "</code>. Use <code>null</code> if you do not
     * want the arguments to be split.
     * @param s the separator
     */
    public final void setSeparator(String s){
        separator = s;
    }
    
    /**
     * Checks wether this command is appliable or not.
     * @param message the message the bot got
     * @return <code>true</code> if this command is appliable.
     */
    public final boolean reception(TextMessage message){
        int indexof = message.TEXT.indexOf('/' + command);
        if(separator == null){
            String args = message.TEXT.substring(command.length()+1, message.TEXT.length());
            onCommand(new String[]{args});
            return true;
        }else if(indexof == 0){
            String args = message.TEXT.substring(command.length()+1, message.TEXT.length());
            onCommand(args.split(separator));
            return true;
        }
        return false;
    }
    
    /**
     * The action you want to do when the user activates this command.
     * @param args the arguments of the command.
     * @see #setSeparator(java.lang.String) 
     */
    public abstract void onCommand(String[] args);
}
