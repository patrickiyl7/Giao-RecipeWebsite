package edu.northeastern.cs5500.recipe.exception;

public class NullKeyException extends Exception {
    /** */
    private static final long serialVersionUID = 1L;

    public NullKeyException(String s) {
        // Call constructor of parent Exception
        super(s);
    }
}
