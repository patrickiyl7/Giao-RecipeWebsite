package edu.northeastern.cs5500.recipe.model;

import edu.northeastern.cs5500.recipe.tools.Tools;
import java.net.UnknownHostException;
import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecipeTest {
    Recipe test;

    @BeforeEach
    void setup() {
        this.test =
                new Recipe(new Date(2020, 2, 14), "ChickenDinner!", "Penglu", "delicious", "", "");
    }

    @Test
    void testGetRecipeId() throws UnknownHostException {
        Recipe recipe =
                new Recipe(new Date(2020, 2, 14), "ChickenDinner!", "Penglu", "delicious", "", "");
        Assert.assertEquals("Penglu_ChickenDinner!", recipe.getRecipeId());
    }

    @Test
    void testGetDate() throws UnknownHostException {
        Recipe recipe =
                new Recipe(
                        Tools.parseDate("03-14-2020"),
                        "ChickenDinner!",
                        "delicious",
                        "Penglu",
                        "",
                        "");
        Assert.assertEquals("03-14-2020", recipe.getDate());
    }

    @Test
    void testGetTitle() throws UnknownHostException {
        Recipe recipe =
                new Recipe(new Date(2020, 2, 14), "ChickenDinner!", "delicious", "Penglu", "", "");
        Assert.assertEquals("ChickenDinner!", recipe.getTitle());
    }

    @Test
    void testGetDescription() throws UnknownHostException {
        Recipe recipe =
                new Recipe(new Date(2020, 2, 14), "ChickenDinner!", "Penglu", "delicious", "", "");
        Assert.assertEquals("delicious", recipe.getDescription());
    }

    @Test
    void testGetUserName() throws UnknownHostException {
        Recipe recipe =
                new Recipe(new Date(2020, 2, 14), "ChickenDinner!", "Penglu", "delicious", "", "");
        Assert.assertEquals("Penglu", recipe.getUserName());
    }

    @Test
    void testSetUserName() throws UnknownHostException {
        this.test.setUserName("Yiwen");
        Assert.assertEquals("Yiwen", test.getUserName());
    }

    @Test
    void testSetDate() throws UnknownHostException {
        this.test.setDate("03-14-2020");
        Assert.assertEquals("03-14-2020", test.getDate());
    }

    @Test
    void testSetDescription() throws UnknownHostException {
        this.test.setDescription("disgusting");
        Assert.assertEquals("disgusting", test.getDescription());
    }

    @Test
    void testSetTitle() throws UnknownHostException {
        this.test.setTitle("shit");
        Assert.assertEquals("shit", test.getTitle());
    }
}
