/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

/**
 *
 * @author CLOVIS
 */
public class MandatoryFieldOmittedException extends Exception {

    /**
     * Creates a new instance of <code>MandatoryFieldOmittedException</code>
     * without detail message.
     */
    public MandatoryFieldOmittedException() {
    }

    /**
     * Constructs an instance of <code>MandatoryFieldOmittedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MandatoryFieldOmittedException(String msg) {
        super(msg);
    }
}
