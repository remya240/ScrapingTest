package comorbidity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import comorbidity.Comorbidity.Recipe;

public class DatabaseHelper {

	private static final String DB_URL = "jdbc:postgresql://localhost:5432/recipe_db"; // Replace with your DB URL
	private static final String USER = "postgres"; // Replace with your DB username
	private static final String PASSWORD = "1717E@birch"; // Replace with your DB password

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, USER, PASSWORD);
	}

	public static synchronized void saveRecipeToDatabase(Recipe recipe) {
		try (Connection conn = getConnection()) {
			String sql = "INSERT INTO recipes (name, url, ingredients, method) VALUES (?, ?, ?, ?)";

			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setString(1, recipe.name);
				stmt.setString(2, recipe.url);
				stmt.setString(3, recipe.ingredients);
				stmt.setString(4, recipe.method);
				stmt.executeUpdate();
				System.out.println("Recipe saved to database: " + recipe.name);
			}
		} catch (SQLException e) {
			System.err.println("Error saving recipe to database: " + e.getMessage());
		}
	}
}
