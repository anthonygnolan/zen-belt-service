package com.coderdojo.zen.belt.exceptions;

public class BeltNotFoundException extends RuntimeException {

    public BeltNotFoundException(Long id) {
        super("Could not find belt " + id);
    }
}
