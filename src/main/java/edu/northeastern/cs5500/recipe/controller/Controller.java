package edu.northeastern.cs5500.recipe.controller;

import java.net.UnknownHostException;

public interface Controller {

    /**
     * Initialize the controller and whatever resources it needs. Connect to databases, set up
     * default values, etc.
     *
     * @throws UnknownHostException
     */
    void register() throws UnknownHostException;
}
