package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class ReviewTest {
    @Test
    void testGetRecipeId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        Assert.assertEquals("delicious", review.getRecipeId());
    }

    @Test
    void testSetRecipeId() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        review.setRecipeId("no");
        Assert.assertEquals("no", review.getRecipeId());
    }

    @Test
    void testGetContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        Assert.assertEquals("ChickenDinner!", review.getContent());
    }

    @Test
    void testSetContent() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        review.setContent("c");
        Assert.assertEquals("c", review.getContent());
    }

    @Test
    void testGetRating() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        Assert.assertEquals(Integer.valueOf(5), review.getRating());
    }

    @Test
    void tesSetRating() throws UnknownHostException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String curDate = dateFormat.format(date);
        Review review =
                new Review(
                        "Chicken/123",
                        curDate,
                        "ChickenDinner!",
                        "delicious",
                        5,
                        "delicious/Chicken/123");
        review.setRating(4);
        Assert.assertEquals(Integer.valueOf(4), review.getRating());
    }
}
