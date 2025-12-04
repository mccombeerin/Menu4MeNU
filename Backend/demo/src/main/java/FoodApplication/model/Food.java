package FoodApplication.model;

import java.util.List;

public class Food {

    // ----- Fields -----
    private String name;                     // Name of the food item
    private String mainNutrition;            // Main nutritional info (e.g., Protein, Vitamins)
    private List<String> ingredients;        // List of ingredients
    private List<String> recipes;            // List of recipe steps or recipe names
    private List<String> recommendations;    // Recommended uses, pairings, etc.
    private List<String> tags;               // Tags like "vegan", "healthy", "spicy", etc.

    // ----- Constructors -----

    // No-args constructor
    public Food() {
    }

    // All-args constructor
    public Food(String name, String mainNutrition, List<String> ingredients,
                List<String> recipes, List<String> recommendations, List<String> tags) {
        this.name = name;
        this.mainNutrition = mainNutrition;
        this.ingredients = ingredients;
        this.recipes = recipes;
        this.recommendations = recommendations;
        this.tags = tags;
    }

    // ----- Getters & Setters -----

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMainNutrition() {
        return mainNutrition;
    }

    public void setMainNutrition(String mainNutrition) {
        this.mainNutrition = mainNutrition;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<String> recipes) {
        this.recipes = recipes;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<String> recommendations) {
        this.recommendations = recommendations;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // ----- Optional: toString() -----
    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", mainNutrition='" + mainNutrition + '\'' +
                ", ingredients=" + ingredients +
                ", recipes=" + recipes +
                ", recommendations=" + recommendations +
                ", tags=" + tags +
                '}';
    }
}
