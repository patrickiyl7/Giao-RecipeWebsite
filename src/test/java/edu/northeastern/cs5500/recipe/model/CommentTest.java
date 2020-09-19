package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class CommentTest {
    @Test
    void testGetUserId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        Assert.assertEquals("UserId", comment.getUserId());
    }

    @Test
    void testSetUserId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        comment.setUserId("UserID");
        Assert.assertEquals("UserID", comment.getUserId());
    }

    @Test
    void testDate() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        Assert.assertEquals(curDate, comment.getDate());
    }

    @Test
    void testSetDate() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        comment.setDate(curDate);
        Assert.assertEquals(curDate, comment.getDate());
    }

    @Test
    void testContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        Assert.assertEquals("Good!", comment.getContent());
    }

    @Test
    void testSetContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Comment comment = new Comment("UserId", curDate, "Good!");
        comment.setContent("Bad!");
        Assert.assertEquals("Bad!", comment.getContent());
    }
}
