package edu.northeastern.cs5500.recipe.model;

import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class StepTest {
    Step step = new Step("Chicken/123", 5, "delicious", "");

    @Test
    void testGetRecipeId() throws UnknownHostException {
        Assert.assertEquals("Chicken/123", step.getRecipeId());
    }

    @Test
    void testGetStepId() throws UnknownHostException {
        Assert.assertEquals("Chicken/1235", step.getStepId());
    }

    @Test
    void testGetDescription() throws UnknownHostException {
        Assert.assertEquals("delicious", step.getDescription());
    }

    @Test
    void testGetOrder() throws UnknownHostException {
        Assert.assertEquals(Integer.valueOf(5), step.getOrder());
    }

    void testSetRecipeId() throws UnknownHostException {
        step.setRecipeId("Chicken/124");
        Assert.assertEquals("Chicken/124", step.getRecipeId());
    }

    @Test
    void testSetStepId() throws UnknownHostException {
        step.setStepId("Chicken/1236");
        Assert.assertEquals("Chicken/1236", step.getStepId());
    }

    @Test
    void testSetDescription() throws UnknownHostException {
        step.setDescription("disgusting");
        Assert.assertEquals("disgusting", step.getDescription());
    }

    @Test
    void testSetOrder() throws UnknownHostException {
        step.setOrder(6);
        Assert.assertEquals(Integer.valueOf(6), step.getOrder());
    }
}
