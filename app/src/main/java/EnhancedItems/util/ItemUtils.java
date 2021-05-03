package EnhancedItems.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

public final class ItemUtils {
    private ItemUtils(){}

    private final static List<FurnaceRecipe> furnaceRecipes = new ArrayList<>();
    private final static Map<Material, ItemStack> recipeMap = new HashMap<>();

    static {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        Recipe recipe;
        while(recipeIterator.hasNext()){
            recipe = recipeIterator.next();
            if(recipe instanceof FurnaceRecipe)
                furnaceRecipes.add((FurnaceRecipe) recipe);
        }
    }

    public static ItemStack getSmeltingResult(Material item){
        ItemStack result = recipeMap.get(item);
        if(result != null) return result;

        for (FurnaceRecipe recipe : furnaceRecipes) {
            if(recipe.getInput().getType() != item) continue;
            result = recipe.getResult();
            recipeMap.put(item, result);
            furnaceRecipes.remove(recipe);
            return result;
        }
        return null;
    }
}
