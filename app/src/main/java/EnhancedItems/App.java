/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package EnhancedItems;

import EnhancedItems.command.GiveItem;
import EnhancedItems.listener.BlockDropItemListener;
import EnhancedItems.parser.ItemParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class App extends JavaPlugin {
    private static JavaPlugin plugin;

    public static JavaPlugin getPlugin(){ return plugin; }

    @Override
    public void onEnable(){
        plugin = this;
        getServer().getPluginManager().registerEvents(new BlockDropItemListener(), this);

        registerItemRecipes();
        getCommand("giveitem").setExecutor(new GiveItem());
    }

    private void registerItemRecipes(){
        try (ZipInputStream zip = new ZipInputStream(App.class.getProtectionDomain().getCodeSource().getLocation().openStream())){
            ZipEntry entry;
            while(( entry = zip.getNextEntry() ) != null){
                String entryName = entry.getName();
                if(entryName.startsWith("item/") &&  entryName.endsWith(".json")) {
                    Object jsonObject = new JSONParser().parse(new InputStreamReader(App.class.getResourceAsStream(String.format("/%s", entryName))));
                    JSONObject itemJson = (JSONObject) jsonObject;

                    Map itemSection = (Map) itemJson.get("ITEM");
                    if(itemSection == null) continue;

                    Map<String, String> ingredients = (Map) itemSection.get("ingredients");
                    JSONArray recipe = (JSONArray) itemSection.get("recipe");

                    String itemName = ((String) itemSection.get("name")).trim()
                            .toLowerCase().replaceAll("\\s", "_");
                    if (ingredients != null && recipe != null) {
                        ShapedRecipe shapedRecipe = new ShapedRecipe(
                                new NamespacedKey(App.getPlugin(), itemName)
                                , ItemParser.parseItem(itemName)
                        );

                        List<String> recipeLine = new ArrayList<>(recipe);
                        shapedRecipe.shape(recipeLine.toArray(new String[recipeLine.size()]));

                        for (String key : ingredients.keySet())
                            shapedRecipe.setIngredient(key.charAt(0), Material.valueOf(ingredients.get(key)));

                        Bukkit.addRecipe(shapedRecipe);
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
