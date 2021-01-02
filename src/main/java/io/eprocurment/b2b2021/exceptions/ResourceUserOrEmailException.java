package io.eprocurment.b2b2021.exceptions;
/**
 * User: franc
 * Date: 09/09/2018
 * Time: 4:21
 */

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ResourceUserOrEmailException extends RuntimeException {
    public ResourceUserOrEmailException(String message) {
        super(message);
    }

    public ResourceUserOrEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
