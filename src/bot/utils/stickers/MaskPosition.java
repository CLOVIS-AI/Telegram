/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.utils.stickers;

import bot.MandatoryFieldOmittedException;
import minimaljson.JsonObject;

/**
 * This objects describes the position on faces where a mask should be placed by default.
 * @author CLOVIS
 */
public class MaskPosition {
    
    /** The part of the face relative to which the mask should be placed. */
    public final Position POINT;
    
    /** Shift by X-axis in widths of the mask scaled to the face size, from left
     to right. For example, choosing -1.0 will place mask just to the left of
     the default mask position.*/
    public final float X_SHIFT;
    
    /** Shift by Y-axis in heights of the mask scaled to the face size, from top
     to bottom. For example 1.0 will place the mask just below mask position. */
    public final float Y_SHIFT;
    
    /** Mask scaling coefficient. For example, 2.0 means double size.*/
    public final float SCALE;
    
    /**
     * Creates a mask position.
     * @param json data from the Telegram servers
     * @exception MandatoryFieldOmittedException if a mandatory field is omitted
     */
    public MaskPosition(JsonObject json) throws MandatoryFieldOmittedException {
        POINT = Position.fromString(json.getString("point", null));
        X_SHIFT = json.getFloat("x_shift", -666);
        Y_SHIFT = json.getFloat("y_shift", -666);
        SCALE = json.getFloat("scale", -666);
        
        if(POINT == null || X_SHIFT == -666 || Y_SHIFT == -666 || SCALE == -666)
            throw new MandatoryFieldOmittedException("'point', 'x_shift', 'y_shift' and 'scale' should not be omitted", json);
    }
    
    /**
     * A part of the face.
     */
    public enum Position{
        /** The forehead. */
        FOREHEAD,
        
        /** The eyes. */
        EYES,
        
        /** The mouth. */
        MOUTH,
        
        /** The chin. */
        CHIN;
        
        /**
         * Get an instance of this enum from a string describing it.
         * @param pos data from the Telegram servers
         * @return The same data, as an enum.
         * @throws IllegalArgumentException if the data is corrupted or wrong.
         */
        public static Position fromString(String pos) throws IllegalArgumentException {
            switch(pos){
                case "forehead":
                    return FOREHEAD;
                case "eyes":
                    return EYES;
                case "mouth":
                    return MOUTH;
                case "chin":
                    return CHIN;
                default:
                    throw new IllegalArgumentException("'pos' should be either 'forehead', 'eyes', 'mouth' or 'chin' : '" + pos + "'");
            }
        }
    }
}
