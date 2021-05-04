package EnhancedItems.listener;

import EnhancedItems.attribute.Attribute;
import EnhancedItems.util.ItemUtils;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Map;

public class PlayerArmorChangeListener implements Listener {
    @EventHandler
    public void onArmorChange(PlayerArmorChangeEvent e){
        removeArmorEffects(e.getPlayer(), e.getOldItem());

        Map<String, String[]> attributeMap = ItemUtils.getItemAttributeMap(e.getNewItem());
        for (String attribute : attributeMap.keySet())
            Attribute.invoke(attribute, e, attributeMap.get(attribute));
    }

    private void removeArmorEffects(Player player, ItemStack armorItem){
        String[] effects = ItemUtils.getItemAttributeMap(armorItem).get("ARMOR_EFFECT");
        if(effects == null) return;
        PotionEffectType potionEffectType;
        for (String s : effects) {
            potionEffectType = PotionEffectType.getByName(s.split("\s*:\s*")[0]);
            if(potionEffectType == null) continue;
            player.removePotionEffect(potionEffectType);
        }
    }
}
