package EnhancedItems.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.Iterator;

public final class ItemUtils {
    private ItemUtils(){}

    public static ItemStack getSmeltingResult(Material item){
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while(recipeIterator.hasNext()){
            Recipe recipe = recipeIterator.next();
            if(!(recipe instanceof FurnaceRecipe)) continue;
            if(((FurnaceRecipe) recipe).getInput().getType() != item) continue;
            return recipe.getResult();
        }
        return null;
    }
}
