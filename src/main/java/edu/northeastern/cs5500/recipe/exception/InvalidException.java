package edu.northeastern.cs5500.recipe.exception;

public class InvalidException extends Exception {
    /** */
    private static final long serialVersionUID = 1L;

    public InvalidException(String s) {
        // Call constructor of parent Exception
        super(s);
    }
}
