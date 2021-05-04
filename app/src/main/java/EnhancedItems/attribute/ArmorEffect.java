package EnhancedItems.attribute;

import EnhancedItems.util.ItemUtils;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ArmorEffect {
    private ArmorEffect(){}

    public static void trigger(Event e, String[] args) {
        if(!(e instanceof PlayerArmorChangeEvent)) return;
        PlayerArmorChangeEvent event = (PlayerArmorChangeEvent) e;
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        removeArmorEffects(event.getPlayer(), ItemUtils.getItemAttributeMap(
                event.getOldItem()).keySet().toArray(new String[0])
        );

        String[] potionEffect;
        int effectAmplifier;
        PotionEffectType potionEffectType;
        for (String s : args){
            potionEffect = s.split("\s*:\s*");
            potionEffectType = PotionEffectType.getByName(potionEffect[0]);
            if(potionEffectType == null) continue;
            effectAmplifier = Integer.parseInt(potionEffect[1]);
            player.addPotionEffect(
                    new PotionEffect(potionEffectType, Integer.MAX_VALUE, effectAmplifier, false, false)
            );
        }
    }

    private static void removeArmorEffects(Player player, String[] potionEffects){
        PotionEffectType potionEffectType;
        for(String s : potionEffects){
            potionEffectType = PotionEffectType.getByName(s);
            if(potionEffectType == null) continue;
            player.removePotionEffect(potionEffectType);
        }
    }
}
