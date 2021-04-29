package EnhancedItems.listener;

import EnhancedItems.attribute.Attribute;
import EnhancedItems.attribute.AttributeMethod;
import EnhancedItems.util.Convert;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;

public class BlockDropItemListener implements Listener {
    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent e){
        ItemMeta itemMeta = e.getPlayer().getInventory().getItemInMainHand()
                .getItemMeta();
        if(itemMeta == null) return;
        try {
            String attributes =
                    itemMeta.getPersistentDataContainer().get(Attribute.namespacedKey, PersistentDataType.STRING);
            if(attributes == null) return;

            Map<String, String[]> attributeMap = Convert.stringToMap(attributes);
            for (String attribute : attributeMap.keySet())
                ((AttributeMethod)Attribute.class.getField(attribute).get(null)).trigger(e, attributeMap.get(attribute));

        } catch (NoSuchFieldException | IllegalAccessException ignored) {
        }
    }
}
