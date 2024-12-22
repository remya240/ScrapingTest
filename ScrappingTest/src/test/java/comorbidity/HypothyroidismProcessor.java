
package comorbidity;

import java.util.List;

import comorbidity.Comorbidity.Recipe;

public class HypothyroidismProcessor extends ComorbidityProcessor {

	private List<String> eliminateIngredients;
	private List<String> addIngredients;

	// Constructor to initialize the lists for eliminate and add ingredients
	public HypothyroidismProcessor(List<String> eliminateIngredients, List<String> addIngredients) {
		this.eliminateIngredients = eliminateIngredients;
		this.addIngredients = addIngredients;
	}

	// Process the recipe to eliminate and add ingredients
	@Override
	public void processRecipe(Comorbidity.Recipe recipe) {
		// Eliminate ingredients: remove items in the eliminate list
		for (String eliminate : eliminateIngredients) {
			recipe.ingredients = recipe.ingredients.replace(eliminate, "");
		}

		// Add ingredients: append items in the add list
		for (String add : addIngredients) {
			// Ensure no duplicates before adding
			if (!recipe.ingredients.contains(add)) {
				recipe.ingredients += ", " + add;
			}
		}

		// Optional: Clean up the ingredient list formatting
		recipe.ingredients = recipe.ingredients.replaceAll("\\s+", " ").trim();
	}
}