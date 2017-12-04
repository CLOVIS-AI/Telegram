/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.messages.TextMessage;

/**
 * This class represents a command.
 * @author CLOVIS
 */
abstract class Command{
    
    private final String command;
    private String separator = " ";
    
    /**
     * Creates a command you will be able to register.
     * @param name the command itself ; example : "getInfos"
     */
    public Command(String name){
        command = name;
    }
    
    /**
     * What should be used to separate arguments in the reply ?
     * <p>By default, <code>s=" "</code>. Use <code>null</code> if you do not
     * want the arguments to be split.
     * @param s the separator
     * @return this object, to allow method-chaining.
     */
    public final Command setSeparator(String s){
        separator = s;
        return this;
    }
    
    /**
     * Checks wether this command is appliable or not.
     * @param message the message the bot got
     * @return <code>true</code> if this command is appliable.
     */
    public final boolean reception(TextMessage message){
        int indexof = message.TEXT.indexOf('/' + command);
        System.out.print("Test de " + message.TEXT);
        if(separator == null){
            System.out.println(" valide");
            String args = message.TEXT.substring(command.length()+1, message.TEXT.length());
            onCommand(new String[]{args}, message);
            return true;
        }else if(indexof == 0){
            System.out.println(" valide");
            String args = message.TEXT.substring(command.length()+1, message.TEXT.length());
            onCommand(args.split(separator), message);
            return true;
        }
        System.out.println(" invalide : " + indexof + " et commande : /" + command);
        return false;
    }
    
    /**
     * The action you want to do when the user activates this command.
     * @param args the arguments of the command.
     * @see #setSeparator(java.lang.String) 
     */
    public abstract void onCommand(String[] args, TextMessage message);
}
