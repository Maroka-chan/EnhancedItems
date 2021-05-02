package EnhancedItems.parser;

import EnhancedItems.attribute.Attribute;
import EnhancedItems.util.Convert;
import com.google.gson.JsonSyntaxException;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Level;

public final class ItemParser {
    public static ItemStack parseItem(String item){
        ItemStack itemstack = null;
        String filename = item.toLowerCase();
        InputStream inputStream = ItemParser.class.getResourceAsStream(String.format("/item/%s.json", filename));
        if(inputStream == null) return null;

        try{
            Object jsonObject = new JSONParser().parse(new InputStreamReader(inputStream));
            JSONObject itemJson = (JSONObject) jsonObject;

            Map<String,?> itemSection = (Map<String,?>) itemJson.get("ITEM");
            if(itemSection == null) return null;

            itemstack = new ItemStack(Material.valueOf((String)itemSection.get("material")));
            ItemMeta meta = itemstack.getItemMeta();
            String itemName = ((String) itemSection.get("name")).trim();
            meta.displayName(Component.empty().content(itemName));

            JSONArray lore = (JSONArray)itemSection.get("lore");
            List<String> loreList = new ArrayList<>(lore);

            List<Component> loreComponents = Component.empty().children();
            for (String s : loreList)
                loreComponents.add(Component.empty().content(s));
            meta.lore(loreComponents);

            Map<String,JSONArray> attributesJson = (Map<String,JSONArray>) itemSection.get("attributes");
            Map<String,String[]> attributes = new HashMap<>();

            for (String key : attributesJson.keySet()){
                List<String> attributeList = new ArrayList<>(attributesJson.get(key));
                attributes.put(key, attributeList.toArray(new String[attributeList.size()]));
            }

            PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
            dataContainer.set(
                    Attribute.namespacedKey
                    , PersistentDataType.STRING
                    , Convert.mapToString(attributes)
            );

            itemstack.setItemMeta(meta);
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            Bukkit.getLogger().log(Level.WARNING, "Syntax Error in " + filename);
        }catch (IOException | ParseException e1){
            e1.printStackTrace();
        }

        return itemstack;
    }
}
