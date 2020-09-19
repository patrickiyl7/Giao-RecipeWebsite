package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;
import java.util.Objects;

/**
 * A class used for save all steps in a recipe, including stepId, recipeId, step description and
 * order.
 */
public class Step implements Comparable<Step> {
    private String stepId;
    private String recipeId;
    private String description;
    private String stepImage;
    private Integer order;

    /**
     * Constructor for Step. *
     *
     * @param recipeId recipeId
     * @param order order
     * @param description description
     */
    public Step(String recipeId, Integer order, String description, String stepImage) {
        this.stepId = recipeId + order.toString();
        this.recipeId = recipeId;
        this.description = description;
        this.order = order;
        this.stepImage = stepImage;
    }

    /**
     * Constructor of DBObject, used to connect MongoDB. *
     *
     * @param step
     */
    public Step(DBObject step) {
        this.stepId = (String) step.get("_id");
        this.recipeId = (String) step.get("recipeId");
        this.description = (String) step.get("description");
        this.order = (Integer) step.get("order");
        this.stepImage = (String) step.get("stepImage");
    }

    /** @return stepId */
    public String getStepId() {
        return this.stepId;
    }

    /** @param stepId stepId */
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    /** @return recipeId */
    public String getRecipeId() {
        return this.recipeId;
    }

    /** @param recipeId recipeId */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    /** @return description */
    public String getDescription() {
        return this.description;
    }

    /** @param description description */
    public void setDescription(String description) {
        this.description = description;
    }

    /** @return order */
    public Integer getOrder() {
        return this.order;
    }

    /** @param order order */
    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Step)) {
            return false;
        }
        Step step = (Step) o;
        return Objects.equals(stepId, step.stepId) && Objects.equals(recipeId, step.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepId, recipeId, description);
    }

    @Override
    public int compareTo(Step step) {
        return this.getOrder().compareTo(step.getOrder());
    }

    public String getStepImage() {
        return this.stepImage;
    }

    public void setStepImage(String stepImage) {
        this.stepImage = stepImage;
    }

    /** @return true if this step is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.description != null && !this.description.isEmpty();
    }
}
