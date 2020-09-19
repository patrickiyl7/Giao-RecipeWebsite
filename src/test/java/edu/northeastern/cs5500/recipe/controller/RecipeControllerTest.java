/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.northeastern.cs5500.recipe.controller;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.tools.Tools;
import java.net.UnknownHostException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class RecipeControllerTest {
    @Test
    void testRegisterCreatesRecipes() throws UnknownHostException {
        RecipeController recipeController = new RecipeController();
        recipeController.register();
        assertThat(recipeController.getAllRecipes()).isNotEmpty();
    }

    @Test
    void testRegisterCreatesValidRecipes() throws UnknownHostException {
        // TODO: Why is this test failing?
        RecipeController recipeController = new RecipeController();
        recipeController.register();

        for (Recipe recipe : recipeController.getAllRecipes()) {
            assertWithMessage(recipe.getTitle()).that(recipe.isValid()).isTrue();
        }
    }

    @Test
    void testCanAddRecipe() throws Exception {
        RecipeController recipeController = new RecipeController();
        Recipe recipe =
                new Recipe(
                        Tools.parseDate("10-19-2019"),
                        "Shit",
                        "Yiwen Zhang",
                        "super delicious",
                        "",
                        "");
        recipeController.addRecipe(recipe);
        Assert.assertEquals(
                recipeController.getRecipe("Yiwen Zhang_Shit").getRecipeId(), "Yiwen Zhang_Shit");
        recipeController.deleteRecipe("Yiwen Zhang_Shit");
    }

    @Test
    void testCanReplaceRecipe() throws Exception {
        RecipeController recipeController = new RecipeController();
        Recipe recipe =
                new Recipe(
                        Tools.parseDate("10-19-2019"),
                        "Shit",
                        "Yiwen Zhang",
                        "super delicious",
                        "",
                        "");
        recipeController.addRecipe(recipe);
        Recipe updateRecipe =
                new Recipe(
                        Tools.parseDate("10-19-2019"), "Shit", "Yiwen Zhang", "disgusting", "", "");
        recipeController.updateRecipe(updateRecipe);
        Assert.assertEquals(
                recipeController.getRecipe("Yiwen Zhang_Shit").getDescription(), "disgusting");
        recipeController.deleteRecipe("Yiwen Zhang_Shit");
    }

    @Test
    void testCanDeleteRecipe() throws Exception {
        RecipeController recipeController = new RecipeController();
        Recipe recipe =
                new Recipe(
                        Tools.parseDate("10-19-2019"),
                        "Shit",
                        "Yiwen Zhang",
                        "super delicious",
                        "",
                        "");
        recipeController.addRecipe(recipe);
        Assert.assertEquals(
                recipeController.getRecipe("Yiwen Zhang_Shit").getRecipeId(), "Yiwen Zhang_Shit");
        recipeController.deleteRecipe("Yiwen Zhang_Shit");
        Assert.assertEquals(recipeController.getRecipe("Yiwen Zhang_Shit"), null);
    }
}
