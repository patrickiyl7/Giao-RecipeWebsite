package edu.northeastern.cs5500.recipe.controller;

import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoSet;

@Module
public class ControllerModule {
    @Provides
    @IntoSet
    public Controller provideReviewController(ReviewController reviewController) {

        return reviewController;
    }

    @Provides
    @IntoSet
    public Controller provideUserController(UserController userController) {

        return userController;
    }

    @Provides
    @IntoSet
    public Controller provideReviewCommentController(
            ReviewCommentController reviewCommentController) {

        return reviewCommentController;
    }

    @Provides
    @IntoSet
    public Controller provideRecipeController(RecipeController recipeController) {
        return recipeController;
    }

    @Provides
    @IntoSet
    public Controller provideStepController(StepController stepController) {
        return stepController;
    }

    @Provides
    @IntoSet
    public Controller provideIngredientController(IngredientController ingredientController) {
        return ingredientController;
    }

    @Provides
    @IntoSet
    public Controller provideStatusController(StatusController statusController) {
        return statusController;
    }
}
