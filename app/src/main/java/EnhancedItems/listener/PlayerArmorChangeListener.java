package EnhancedItems.listener;

import EnhancedItems.attribute.Attribute;
import EnhancedItems.util.ItemUtils;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Map;

public class PlayerArmorChangeListener implements Listener {
    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent e){
        Map<String, String[]> attributeMap = ItemUtils.getItemAttributeMap(e.getNewItem());

        for (String attribute : attributeMap.keySet())
            Attribute.invoke(attribute, e, attributeMap.get(attribute));
    }


}
