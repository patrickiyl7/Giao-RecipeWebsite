package edu.northeastern.cs5500.recipe.exception;

public class DuplicateKeyException extends Exception {
    /** */
    private static final long serialVersionUID = 1L;

    private String info;

    public DuplicateKeyException(String s) {
        // Call constructor of parent Exception
        super(s);
        this.info = s;
    }

    @Override
    public String toString() {
        return this.info;
    }
}
