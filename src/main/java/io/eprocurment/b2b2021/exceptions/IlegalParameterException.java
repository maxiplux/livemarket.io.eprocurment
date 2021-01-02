package io.eprocurment.b2b2021.exceptions;
/**
 * User: franc
 * Date: 09/09/2018
 * Time: 4:21
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IlegalParameterException extends RuntimeException {
    public IlegalParameterException(String message) {
        super(message);
    }

    public IlegalParameterException(String message, Throwable cause) {
        super(message, cause);
    }
}
