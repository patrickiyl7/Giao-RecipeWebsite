package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IngredientTest {
    Ingredient test;

    @BeforeEach
    void setup() {
        this.test = new Ingredient("Chicken", "123", "1", "gram");
    }

    @Test
    void testGetIngredientId() throws UnknownHostException {
        Ingredient ingredient = new Ingredient("Chicken", "123", "1", "gram");
        Assert.assertEquals("Chicken/123", ingredient.getIngredientId());
    }

    @Test
    void testGetIngredientName() throws UnknownHostException {
        Ingredient ingredient = new Ingredient("Chicken", "123", "1", "gram");
        Assert.assertEquals("Chicken", ingredient.getIngredientName());
    }

    @Test
    void testGetRecipeId() throws UnknownHostException {
        Ingredient ingredient = new Ingredient("Chicken", "123", "1", "gram");
        Assert.assertEquals("123", ingredient.getRecipeId());
    }

    @Test
    void testGetAmount() throws UnknownHostException {
        Ingredient ingredient = new Ingredient("Chicken", "123", "1", "gram");
        Assert.assertEquals("1", ingredient.getAmount());
    }

    @Test
    void testGetUnit() throws UnknownHostException {
        Ingredient ingredient = new Ingredient("Chicken", "123", "1", "gram");
        Assert.assertEquals("gram", ingredient.getUnit());
    }

    @Test
    void testSetIngredientId() throws UnknownHostException {
        test.setIngredientId("1");
        Assert.assertEquals("1", test.getIngredientId());
    }

    @Test
    void testSetIngredientName() throws UnknownHostException {
        test.setIngredientName("shit");
        Assert.assertEquals("shit", test.getIngredientName());
    }

    @Test
    void testSetRecipeId() throws UnknownHostException {
        test.setRecipeId("1");
        Assert.assertEquals("1", test.getRecipeId());
    }

    @Test
    void testSetAmount() throws UnknownHostException {
        test.setAmount("2");
        Assert.assertEquals("2", test.getAmount());
    }

    @Test
    void testSetUnit() throws UnknownHostException {
        test.setUnit("Kg");
        Assert.assertEquals("Kg", test.getUnit());
    }
}
