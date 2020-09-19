package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.exception.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exception.InvalidException;
import edu.northeastern.cs5500.recipe.exception.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Ingredient;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class IngredientController implements Controller {
    private static DBCollection collection =
            JavaMongoDBConnection.getDB().getCollection("ingredient");

    @Inject
    IngredientController() {}

    @Override
    public void register() throws UnknownHostException {
        final Ingredient defaultIngredient1 =
                new Ingredient("urine", "YiwenZhang_slava_chiken", "1", "table spoon");
        final Ingredient defaultIngredient2 =
                new Ingredient("shit", "YiwenZhang_slava_chiken", "2", "tea spoon");
        try {
            addIngredient(defaultIngredient1);
            addIngredient(defaultIngredient2);
        } catch (Exception e) {
            log.error("RecipeController > register > adding default recipes > failure?");
            e.printStackTrace();
        }
    }

    @Nullable
    public Ingredient getIngredient(@Nonnull String ingredientId) {
        log.debug("RecipeController > getRecipe({})", ingredientId);
        DBCursor ingredients = collection.find(new BasicDBObject("_id", ingredientId));
        if (ingredients.size() != 0) {
            ingredients.close();
            return new Ingredient(ingredients.one());
        }
        ingredients.close();
        return null;
    }

    @Nullable
    public List<Ingredient> getIngredients(@Nonnull String recipeId) {
        log.debug("RecipeController > getRecipe({})", recipeId);
        DBCursor ingredients = collection.find(new BasicDBObject("recipeId", recipeId));
        List<Ingredient> ingredientList = new ArrayList<>();
        if (ingredients.size() != 0) {
            for (DBObject ingredient : ingredients) {
                ingredientList.add(new Ingredient(ingredient));
            }
            ingredients.close();
            return ingredientList;
        }
        ingredients.close();
        return ingredientList;
    }

    @Nonnull
    public String addIngredient(@Nonnull Ingredient ingredient) throws Exception {
        if (!ingredient.isValid()) {
            throw new InvalidException("The ingredient is invalid");
        }
        DBCursor ingredients =
                collection.find(new BasicDBObject("_id", ingredient.getIngredientId()));
        if (ingredients.size() != 0) {
            ingredients.close();
            throw new DuplicateKeyException("The key is duplicate.");
        }
        DBObject ingredientObject =
                new BasicDBObject("_id", ingredient.getIngredientId())
                        .append("ingredientname", ingredient.getIngredientName())
                        .append("recipeId", ingredient.getRecipeId())
                        .append("amount", ingredient.getAmount())
                        .append("unit", ingredient.getUnit());
        collection.insert(ingredientObject);
        log.debug("RecipeController > addRecipe(...)");
        ingredients.close();
        return ingredient.getIngredientId();
    }

    public void updateIngredient(@Nonnull Ingredient ingredient) throws Exception {
        log.debug("RecipeController > updateRecipe(...)");
        String id = ingredient.getIngredientId();
        if (id == null) {
            throw new NullKeyException("The key is null");
        }

        if (!ingredient.isValid()) {
            throw new InvalidException("The ingredient is invalid.");
        }
        DBCursor ingredients =
                collection.find(new BasicDBObject("_id", ingredient.getIngredientId()));
        if (ingredients.size() == 0) {
            ingredients.close();
            throw new NullKeyException("The key is not found.");
        }
        DBObject ingredientObject =
                new BasicDBObject("_id", ingredient.getIngredientId())
                        .append("ingredientname", ingredient.getIngredientName())
                        .append("recipeId", ingredient.getRecipeId())
                        .append("amount", ingredient.getAmount())
                        .append("unit", ingredient.getUnit());
        collection.save(ingredientObject);
        ingredients.close();
    }

    public void deleteIngredient(@Nonnull String ingredientId) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        DBCursor ingredients = collection.find(new BasicDBObject("_id", ingredientId));
        if (ingredients.size() == 0) {
            ingredients.close();
            throw new NullKeyException("The key is not found.");
        }
        collection.findAndRemove(ingredients.one());
        ingredients.close();
    }

    public void deleteRecipeIngredients(@Nonnull String recipeId) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        DBCursor ingredients = collection.find(new BasicDBObject("recipeId", recipeId));
        while (ingredients.hasNext()) {
            collection.findAndRemove(ingredients.next());
        }
        ingredients.close();
    }
}
