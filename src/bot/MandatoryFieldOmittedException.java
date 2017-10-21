/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import minimaljson.JsonValue;

/**
 *
 * @author CLOVIS
 */
public class MandatoryFieldOmittedException extends RuntimeException {

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
<<<<<<< HEAD
     * @param json the json object that caused it.
     */
    public MandatoryFieldOmittedException(String msg, JsonValue json) {
        super(msg + "\n" + json.asString());
=======
     */
    public MandatoryFieldOmittedException(String msg) {
        super(msg);
>>>>>>> c0b2730392b90987b1050c788874231d37f18858
    }
}
