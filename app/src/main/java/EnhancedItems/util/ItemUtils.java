package EnhancedItems.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ItemUtils {
    private ItemUtils(){}

    private final static List<FurnaceRecipe> furnaceRecipes = new ArrayList<>();

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
        for (FurnaceRecipe recipe : furnaceRecipes) {
            if(recipe.getInput().getType() != item) continue;
            return recipe.getResult();
        }
        return null;
    }
}
