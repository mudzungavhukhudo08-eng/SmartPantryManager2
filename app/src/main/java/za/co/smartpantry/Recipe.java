package za.co.smartpantry;

import java.util.Arrays;
import java.util.List;

public class Recipe {
    public long id;
    public String name;
    public String ingredientsText;
    public String method;

    public Recipe(long id, String name, String ingredientsText, String method) {
        this.id = id;
        this.name = name;
        this.ingredientsText = ingredientsText;
        this.method = method;
    }

    public List<String> ingredientNames() {
        return Arrays.asList(ingredientsText.split("\\s*,\\s*"));
    }
}
