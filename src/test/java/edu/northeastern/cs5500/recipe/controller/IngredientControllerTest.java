package edu.northeastern.cs5500.recipe.controller;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import edu.northeastern.cs5500.recipe.model.Ingredient;
import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class IngredientControllerTest {

    @Test
    void testRegisterCreatesIngredients() throws UnknownHostException {
        IngredientController ingredientController = new IngredientController();
        ingredientController.register();
        assertThat(ingredientController.getIngredients("YiwenZhang_slava_chiken")).isNotEmpty();
    }

    @Test
    void testRegisterCreatesValidSteps() throws UnknownHostException {
        IngredientController ingredientController = new IngredientController();
        for (Ingredient ingredient :
                ingredientController.getIngredients("YiwenZhang_slava_chiken")) {
            assertWithMessage(ingredient.getIngredientId()).that(ingredient.isValid()).isTrue();
        }
    }

    @Test
    void testCanAddIngredient() throws Exception {
        IngredientController ingredientController = new IngredientController();
        Ingredient ingredient =
                new Ingredient("fart", "YiwenZhang_slava_chiken", "1", "table spoon");
        ingredientController.addIngredient(ingredient);
        Assert.assertEquals(
                ingredientController.getIngredients("YiwenZhang_slava_chiken").size(), 3);
        ingredientController.deleteIngredient("fart/YiwenZhang_slava_chiken");
    }

    @Test
    void testCanReplaceStep() throws Exception {
        IngredientController ingredientController = new IngredientController();
        Ingredient ingredient =
                new Ingredient("fart", "YiwenZhang_slava_chiken", "1", "table spoon");
        ingredientController.addIngredient(ingredient);
        Ingredient updateIngredient =
                new Ingredient("fart", "YiwenZhang_slava_chiken", "2", "table spoon");
        ingredientController.updateIngredient(updateIngredient);
        Assert.assertEquals(
                ingredientController.getIngredient("fart/YiwenZhang_slava_chiken").getAmount(),
                "2");
        ingredientController.deleteIngredient("fart/YiwenZhang_slava_chiken");
    }

    @Test
    void testCanDeleteStep() throws Exception {
        IngredientController ingredientController = new IngredientController();
        Ingredient ingredient =
                new Ingredient("fart", "YiwenZhang_slava_chiken", "1", "table spoon");
        ingredientController.addIngredient(ingredient);
        Assert.assertEquals(
                ingredientController.getIngredients("YiwenZhang_slava_chiken").size(), 3);
        ingredientController.deleteIngredient("fart/YiwenZhang_slava_chiken");
        Assert.assertEquals(
                ingredientController.getIngredient("fart/YiwenZhang_slava_chiken"), null);
    }
}
