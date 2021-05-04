package EnhancedItems.listener;

import EnhancedItems.attribute.Attribute;
import EnhancedItems.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;

import java.util.Map;

public class BlockDropItemListener implements Listener {
    @EventHandler
    public void onBlockDropItem(BlockDropItemEvent e){
        Map<String, String[]> attributeMap = ItemUtils.getItemAttributeMap(
                e.getPlayer().getInventory().getItemInMainHand());

        for (String attribute : attributeMap.keySet())
            Attribute.invoke(attribute, e, attributeMap.get(attribute));
    }
}
