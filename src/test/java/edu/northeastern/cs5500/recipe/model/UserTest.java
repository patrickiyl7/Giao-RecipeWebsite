package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    void testGetUserId() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("123", user.getUserId());
    }

    @Test
    void testSetUserId() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setUserId("1");
        Assert.assertEquals("1", user.getUserId());
    }

    @Test
    void testUserName() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("Penglu", user.getUserName());
    }

    @Test
    void testSetUserName() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setUserName("Yiwen");
        Assert.assertEquals("Yiwen", user.getUserName());
    }

    @Test
    void testGetUserPassWord() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("password", user.getPassWord());
    }

    @Test
    void testSetUserPassWord() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setPassWord("p");
        Assert.assertEquals("p", user.getPassWord());
    }

    @Test
    void testGetEmailAddress() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("@", user.getEmailAddress());
    }

    @Test
    void testSetEmailAddress() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setEmailAddress("@1");
        Assert.assertEquals("@1", user.getEmailAddress());
    }

    @Test
    void testGetSlogan() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("lovefood", user.getSlogan());
    }

    @Test
    void testSetSlogan() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setSlogan("c");
        Assert.assertEquals("c", user.getSlogan());
    }

    @Test
    void testGetStatus() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        Assert.assertEquals("online", user.getStatus());
    }

    @Test
    void testSetStatus() throws UnknownHostException {
        User user = new User("123", "Penglu", "password", "@", "lovefood", "online", "");
        user.setStatus("single");
        Assert.assertEquals("single", user.getStatus());
    }
}
