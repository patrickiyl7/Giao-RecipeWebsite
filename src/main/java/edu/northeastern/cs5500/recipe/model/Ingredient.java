package edu.northeastern.cs5500.recipe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mongodb.DBObject;
import java.util.Objects;
import lombok.Data;

/**
 * A class used to save all small pieces of a ingredients, including amount and unit. Each
 * ingredient bound to an recipe.
 */
@Data
public class Ingredient {
    private String ingredientId;
    private String ingredientName;
    private String recipeId;
    private String amount;
    private String unit;

    /**
     * @param ingredientName Name of ingredient. *
     * @param recipeId The recipe that contain this ingredient. *
     * @param amount The amount of current ingredient. *
     * @param unit The unit of ingredient. *
     */
    public Ingredient(String ingredientName, String recipeId, String amount, String unit) {
        this.ingredientId = ingredientName + "/" + recipeId;
        this.ingredientName = ingredientName;
        this.recipeId = recipeId;
        this.amount = amount;
        this.unit = unit;
    }

    /**
     * Constructor for MongoDB Object, used to connect to MongoDB
     *
     * @param ingredient
     */
    public Ingredient(DBObject ingredient) {
        this.ingredientId = (String) ingredient.get("_id");
        this.ingredientName = (String) ingredient.get("ingredientname");
        this.recipeId = (String) ingredient.get("recipeId");
        this.amount = (String) ingredient.get("amount");
        this.unit = (String) ingredient.get("unit");
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Ingredient ingredient = (Ingredient) o;
        return Objects.equals(ingredientId, ingredient.ingredientId)
                && Objects.equals(ingredientName, ingredient.ingredientName)
                && Objects.equals(recipeId, ingredient.recipeId)
                && Objects.equals(amount, ingredient.amount)
                && Objects.equals(unit, ingredient.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, ingredientName, recipeId, amount, unit);
    }

    @Override
    public String toString() {
        return "{"
                + " ingredientId='"
                + getIngredientId()
                + "'"
                + ", ingredientName='"
                + getIngredientName()
                + "'"
                + ", recipeId='"
                + getRecipeId()
                + "'"
                + ", amount='"
                + getAmount()
                + "'"
                + ", unit='"
                + getUnit()
                + "'"
                + "}";
    }
    /**
     * getter for ingredientId. *
     *
     * @return ingredientId
     */
    public String getIngredientId() {
        return this.ingredientId;
    }

    /**
     * Setter for ingredientId. *
     *
     * @param ingredientId
     */
    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }
    /**
     * getter for ingredientName. *
     *
     * @return ingredientName
     */
    public String getIngredientName() {
        return this.ingredientName;
    }
    /** @param ingredientName */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
    /** @return recipeId */
    public String getRecipeId() {
        return this.recipeId;
    }
    /** @param recipeId */
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }
    /** @return amount */
    public String getAmount() {
        return this.amount;
    }
    /** @param amount */
    public void setAmount(String amount) {
        this.amount = amount;
    }
    /** @return unit */
    public String getUnit() {
        return this.unit;
    }
    /** @param unit */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /** @return true if this ingredient is valid */
    @JsonIgnore
    public boolean isValid() {
        return this.ingredientName != null && !this.ingredientName.isEmpty();
    }
}
