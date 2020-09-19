package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.exception.DuplicateKeyException;
import edu.northeastern.cs5500.recipe.exception.InvalidException;
import edu.northeastern.cs5500.recipe.exception.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Recipe;
import edu.northeastern.cs5500.recipe.tools.Tools;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * RecipeController used to build connection to MongoDB and have multiple method to add and edit
 * recipe information on MongoDB.
 */
@Singleton
@Slf4j
public class RecipeController implements Controller {
    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("recipe");

    @Inject
    RecipeController() {}

    /** To test and to create an sample collection of MongoDB */
    @Override
    public void register() throws UnknownHostException {
        final Recipe defaultRecipe1 =
                new Recipe(
                        Tools.parseDate("10-19-2019"),
                        "slava chiken",
                        "YiwenZhang",
                        "super delicious",
                        "",
                        "");

        try {
            addRecipe(defaultRecipe1);
        } catch (Exception e) {
            log.error("RecipeController > register > adding default recipes > failure?");
            e.printStackTrace();
        }
    }

    /**
     * Find recipe on recipe collenction and return recipe from MongoDB
     *
     * @param recipeId recipeId
     * @return recipe
     */
    @Nullable
    public Recipe getRecipe(@Nonnull String recipeId) {
        // TODO: Should this be null or should this throw an exception?
        log.debug("RecipeController > getRecipe({})", recipeId);
        DBCursor recipes = collection.find(new BasicDBObject("_id", recipeId));
        if (recipes.size() != 0) {
            DBObject recipe = recipes.one();
            recipes.close();
            return new Recipe(recipe);
        }
        return null;
    }

    /**
     * return recipes when try to search by username. *
     *
     * @param username username
     * @return recipe
     */
    @Nonnull
    public Collection<Recipe> getRecipesPageByUserName(
            String username, Integer pageSize, Integer pageNum) {
        Integer skips = pageSize * (pageNum - 1);
        log.debug("RecipeController > getRecipes()");
        DBCursor recipes =
                collection
                        .find(new BasicDBObject("username", username))
                        .skip(skips)
                        .limit(pageSize);
        if (recipes.size() == 0) {
            return new ArrayList<>();
        }
        List<Recipe> results = new ArrayList<>();
        for (DBObject r : recipes) {
            results.add(new Recipe(r));
        }
        recipes.close();
        return results;
    }

    /**
     * return recipes when try to search by recipeTitle. *
     *
     * @param title
     * @return recipe
     */
    @Nonnull
    public Collection<Recipe> getRecipesByTitle(String title) {
        log.debug("RecipeController > getRecipes()");
        BasicDBObject q = new BasicDBObject();
        q.put("title", java.util.regex.Pattern.compile(title));
        DBCursor recipes = collection.find(q);
        if (recipes.size() == 0) {
            return new ArrayList<>();
        }
        List<Recipe> results = new ArrayList<>();
        for (DBObject r : recipes) {
            results.add(new Recipe(r));
        }
        recipes.close();
        return results;
    }

    /**
     * return all recipes in collection. *
     *
     * @return allrecipes
     */
    @Nonnull
    public Collection<Recipe> getAllRecipes() {
        DBCursor recipes = collection.find();
        if (recipes.size() == 0) {
            return new ArrayList<>();
        }
        List<Recipe> results = new ArrayList<>();
        for (DBObject r : recipes) {
            results.add(new Recipe(r));
        }
        recipes.close();
        return results;
    }

    /**
     * return recipes on the page in collection. *
     *
     * @return recipes on the page
     */
    @Nonnull
    public Collection<Recipe> getPageRecipes(Integer pageSize, Integer pageNum) {
        Integer skips = pageSize * (pageNum - 1);
        DBCursor recipes = collection.find().skip(skips).limit(pageSize);
        if (recipes.size() == 0) {
            return new ArrayList<>();
        }
        List<Recipe> results = new ArrayList<>();
        for (DBObject r : recipes) {
            results.add(new Recipe(r));
        }
        recipes.close();
        return results;
    }

    /**
     * return recipes on the page when try to search by recipeTitle. *
     *
     * @param title
     * @return recipe
     */
    @Nonnull
    public Collection<Recipe> getPageRecipesByTitle(
            String title, Integer pageSize, Integer pageNum) {
        log.debug("RecipeController > getRecipes()");
        Integer skips = pageSize * (pageNum - 1);
        DBCursor recipes =
                collection
                        .find(new BasicDBObject("title", "/" + title + "/"))
                        .skip(skips)
                        .limit(pageSize);
        if (recipes.size() == 0) {
            return new ArrayList<>();
        }
        List<Recipe> results = new ArrayList<>();
        for (DBObject r : recipes) {
            results.add(new Recipe(r));
        }
        recipes.close();
        return results;
    }

    /**
     * Adding a recipe to collection. *
     *
     * @param recipe recipe
     * @return recipe.getRecipeId()
     * @throws Exception DuplicateKeyException
     */
    @Nonnull
    public String addRecipe(@Nonnull Recipe recipe) throws Exception {
        if (!recipe.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new InvalidException("Your recipe is invalid.");
        }
        DBCursor recipes = collection.find(new BasicDBObject("_id", recipe.getRecipeId()));
        if (recipes != null && recipes.size() != 0) {
            throw new DuplicateKeyException("You have created the recipe.");
        }
        DBObject recipeObject =
                new BasicDBObject("_id", recipe.getRecipeId())
                        .append("username", recipe.getUserName())
                        .append("title", recipe.getTitle())
                        .append("description", recipe.getDescription())
                        .append("date", recipe.getDate())
                        .append("finalImage", recipe.getFinalImage())
                        .append("userImage", recipe.getUserImage());
        collection.insert(recipeObject);
        recipes.close();
        log.debug("RecipeController > addRecipe(...)");
        return recipe.getRecipeId();
    }

    /**
     * Updating a recipe by its recipeId. *
     *
     * @param recipe
     * @throws Exception NullKeyException
     */
    public void updateRecipe(@Nonnull Recipe recipe) throws Exception {
        log.debug("RecipeController > updateRecipe(...)");
        String id = recipe.getRecipeId();
        if (id == null) {
            // TODO: replace with a real null key exception
            throw new NullKeyException("The key is null.");
        }

        if (!recipe.isValid()) {
            // TODO: replace with a real invalid object exception
            // probably not one exception per object type though...
            throw new InvalidException("The Recipe is invalid.");
        }
        DBCursor recipes = collection.find(new BasicDBObject("_id", recipe.getRecipeId()));
        if (recipes.size() == 0) {
            recipes.close();
            throw new NullKeyException("The key is not found");
        }
        DBObject recipeObject =
                new BasicDBObject("_id", recipe.getRecipeId())
                        .append("username", recipe.getUserName())
                        .append("title", recipe.getTitle())
                        .append("description", recipe.getDescription())
                        .append("date", recipe.getDate())
                        .append("finalImage", recipe.getFinalImage());
        collection.save(recipeObject);
        recipes.close();
    }

    /**
     * Delete a recipe by its recipeId. *
     *
     * @param recipeId
     * @throws Exception NullKeyException
     */
    public void deleteRecipe(@Nonnull String recipeId) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        DBCursor recipes = collection.find(new BasicDBObject("_id", recipeId));
        if (recipes.size() == 0) {
            recipes.close();
            throw new NullKeyException("The key is null.");
        }
        collection.findAndRemove(recipes.one());
        recipes.close();
    }
}
