package EnhancedItems.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

import java.util.*;

public final class ItemUtils {
    private ItemUtils(){}

    private final static Map<Material, ItemStack> recipeMap = new HashMap<>();

    static {
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        Recipe recipe;
        FurnaceRecipe furnaceRecipe;

        while(recipeIterator.hasNext()){
            recipe = recipeIterator.next();
            if(recipe instanceof FurnaceRecipe){
                furnaceRecipe = (FurnaceRecipe) recipe;
                recipeMap.put(furnaceRecipe.getInput().getType(), furnaceRecipe.getResult());
            }
        }
    }

    public static ItemStack getSmeltingResult(Material item){
        return recipeMap.get(item);
    }
}
