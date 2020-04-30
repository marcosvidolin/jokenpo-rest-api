package com.marcosvidolin.jokenpo.domain.exception;

/**
 * Throws a {@link ModelAlreadyExistsException} whenever a model was not found.
 */
public class ModelAlreadyExistsException extends Throwable {

    public ModelAlreadyExistsException(String message) {
        super(message);
    }

}
