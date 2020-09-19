package edu.northeastern.cs5500.recipe.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import edu.northeastern.cs5500.recipe.JavaMongoDBConnection;
import edu.northeastern.cs5500.recipe.exception.InvalidException;
import edu.northeastern.cs5500.recipe.exception.NullKeyException;
import edu.northeastern.cs5500.recipe.model.Step;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

/**
 * StepController used to build connection to MongoDB and have multiple method to add and edit step
 * information on MongoDB.
 */
@Singleton
@Slf4j
public class StepController implements Controller {
    private static DBCollection collection = JavaMongoDBConnection.getDB().getCollection("step");;

    @Inject
    StepController() {}

    /** register a collection and add it into MongoDB. * */
    @Override
    public void register() throws UnknownHostException {
        final Step defaultStep1 =
                new Step("YiwenZhang_slava_chiken", 1, "Take off your pants.", "");
        final Step defaultStep2 = new Step("YiwenZhang_slava_chiken", 2, "Sit on your toilet.", "");
        try {
            addStep(defaultStep1);
            addStep(defaultStep2);
        } catch (Exception e) {
            log.error("RecipeController > register > adding default recipes > failure?");
            e.printStackTrace();
        }
    }

    /**
     * get the step by stepId. *
     *
     * @param stepId
     * @return step
     */
    @Nullable
    public Step getStep(@Nonnull String stepId) {
        log.debug("RecipeController > getRecipe({})", stepId);
        DBCursor steps = collection.find(new BasicDBObject("_id", stepId));
        if (steps.size() != 0) {
            DBObject step = steps.one();
            steps.close();
            return new Step(step);
        }
        steps.close();
        return null;
    }

    /**
     * @param recipeId
     * @return list of all review steps.
     */
    @Nullable
    public List<Step> getSteps(@Nonnull String recipeId) {
        log.debug("RecipeController > getRecipe({})", recipeId);
        DBCursor steps = collection.find(new BasicDBObject("recipeId", recipeId));
        List<Step> stepList = new ArrayList<>();
        if (steps.size() != 0) {
            for (DBObject step : steps) {
                stepList.add(new Step(step));
            }
            steps.close();
            Collections.sort(stepList);
            return stepList;
        }
        steps.close();
        return stepList;
    }

    /**
     * @param step
     * @return step.getStepId()
     * @throws Exception DuplicateKeyException
     */
    @Nonnull
    public String addStep(@Nonnull Step step) throws Exception {
        if (!step.isValid()) {
            throw new InvalidException("The ingredient is invalid.");
        }
        DBCursor ingredients = collection.find(new BasicDBObject("_id", step.getStepId()));
        if (ingredients.size() != 0) {
            ingredients.close();
            throw new Exception("You have two duplicated steps in your recipe");
        }
        DBObject stepObject =
                new BasicDBObject("_id", step.getStepId())
                        .append("recipeId", step.getRecipeId())
                        .append("order", step.getOrder())
                        .append("description", step.getDescription())
                        .append("stepImage", step.getStepImage());
        collection.insert(stepObject);
        log.debug("RecipeController > addRecipe(...)");
        ingredients.close();
        return step.getStepId();
    }

    /**
     * @param step
     * @throws Exception NullKeyException
     */
    public void updateStep(@Nonnull Step step) throws Exception {
        log.debug("RecipeController > updateRecipe(...)");
        String id = step.getStepId();
        if (id == null) {
            throw new NullKeyException("The key is null.");
        }

        if (!step.isValid()) {
            throw new InvalidException("The step is invalid.");
        }
        DBCursor steps = collection.find(new BasicDBObject("_id", step.getStepId()));
        if (steps.size() == 0) {
            steps.close();
            throw new NullKeyException("The key is not found.");
        }
        DBObject stepObject =
                new BasicDBObject("_id", step.getStepId())
                        .append("recipeId", step.getRecipeId())
                        .append("order", step.getOrder())
                        .append("description", step.getDescription())
                        .append("stepImage", step.getStepImage());
        collection.save(stepObject);
        steps.close();
    }

    /**
     * @param stepId
     * @throws Exception KeyNotFoundException
     */
    public void deleteStep(@Nonnull String stepId) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        DBCursor steps = collection.find(new BasicDBObject("_id", stepId));
        if (steps.size() == 0) {
            // TODO: replace with a real recipe not found exception
            throw new NullKeyException("The key is not found.");
        }
        collection.findAndRemove(steps.one());
        steps.close();
    }

    /**
     * @param recipeId
     * @throws Exception KeyNotFoundException
     */
    public void deleteRecipeSteps(@Nonnull String recipeId) throws Exception {
        log.debug("RecipeController > deleteRecipe(...)");
        DBCursor steps = collection.find(new BasicDBObject("recipeId", recipeId));
        while (steps.hasNext()) {
            collection.findAndRemove(steps.next());
        }
        steps.close();
    }
}
