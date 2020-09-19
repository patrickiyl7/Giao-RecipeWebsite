package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReviewCommentTest {
    @Test
    void testGetUsrId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        Assert.assertEquals("123", reviewcomment.getUserId());
    }

    @Test
    void testSetUsrId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        reviewcomment.setUserId("124");
        Assert.assertEquals("124", reviewcomment.getUserId());
    }

    @Test
    void testGetDate() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        Assert.assertEquals(curDate, reviewcomment.getDate());
    }

    @Test
    void testSetDate() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        reviewcomment.setDate(curDate);
        Assert.assertEquals(curDate, reviewcomment.getDate());
    }

    @Test
    void testGetContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        Assert.assertEquals("delicious", reviewcomment.getContent());
    }

    @Test
    void testSetContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        ReviewComment reviewcomment =
                new ReviewComment("123", curDate, "delicious", "delicious/Chicken/123");
        reviewcomment.setContent("disgusting");
        Assert.assertEquals("disgusting", reviewcomment.getContent());
    }
}
