/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import com.eclipsesource.json.JsonValue;

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
     * @param json the json object that caused it.
     */
    public MandatoryFieldOmittedException(String msg, JsonValue json) {
        super(msg + "\n" + json.toString());
    }
}
