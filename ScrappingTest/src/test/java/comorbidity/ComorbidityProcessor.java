package comorbidity;

import java.util.*;

import testBase.BaseClass;
import util.ExcelReader;

public abstract class ComorbidityProcessor {

    // Abstract method to process a recipe
    public abstract void processRecipe(Comorbidity.Recipe recipe);

    public static void readExcelAndProcess() {
        try {
            // Reading data from "Diabetes" sheet
            Map<String, List<String>> diabetesIngredients = ExcelReader.readIngredientsFromExcel(BaseClass.excelFilePath, "Diabetes");
            List<String> diabetesEliminate = diabetesIngredients.getOrDefault("Eliminate", new ArrayList<>());
            List<String> diabetesToAdd = diabetesIngredients.getOrDefault("To Add", new ArrayList<>());

            // Reading data from "Hypothyroidism" sheet
            Map<String, List<String>> hypothyroidismIngredients = ExcelReader.readIngredientsFromExcel(BaseClass.excelFilePath, "Hypothyroidism");
            List<String> hypothyroidismEliminate = hypothyroidismIngredients.getOrDefault("Eliminate", new ArrayList<>());
            List<String> hypothyroidismToAdd = hypothyroidismIngredients.getOrDefault("To Add", new ArrayList<>());

            // Create processors for each comorbidity
            ComorbidityProcessor diabetesProcessor = new DiabetesProcessor(diabetesEliminate, diabetesToAdd);
            ComorbidityProcessor hypothyroidismProcessor = new HypothyroidismProcessor(hypothyroidismEliminate, hypothyroidismToAdd);

            // Fetch recipes from a data source
            List<Comorbidity.Recipe> recipes = getRecipes(); // Replace with actual fetching logic

            // Process each recipe with both processors
            for (Comorbidity.Recipe recipe : recipes) {
                diabetesProcessor.processRecipe(recipe);
                hypothyroidismProcessor.processRecipe(recipe);
            }
        } catch (Exception e) {
            System.err.println("Error during Excel reading or recipe processing: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Placeholder for recipe fetching logic
    public static List<Comorbidity.Recipe> getRecipes() {
        // Replace with actual logic to fetch recipes, e.g., from a database or scraping
        return new ArrayList<>();
    }
}
