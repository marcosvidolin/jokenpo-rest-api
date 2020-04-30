package com.marcosvidolin.jokenpo.domain.exception;

/**
 * Throws a {@link ModelNotFoundException} whenever a model was not found.
 */
public class ModelNotFoundException extends Throwable {

    public ModelNotFoundException(String message) {
        super(message);
    }

}
