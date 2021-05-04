package EnhancedItems.attribute;

import EnhancedItems.util.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public final class AutoSmelt {
    private AutoSmelt(){}

    public static void trigger(Event e, String[] args) {
        if(!(e instanceof BlockDropItemEvent)) return;
        BlockDropItemEvent event = (BlockDropItemEvent) e;
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        event.setCancelled(true);
        BlockState blockState = event.getBlockState();
        World world = blockState.getWorld();

        Material block = blockState.getType();
        ItemStack blockDrop = null;
        boolean dropped = false;
        for (Item item : event.getItems()){
            ItemStack droppedItem = item.getItemStack();
            if(droppedItem.getType() == block){
                blockDrop = droppedItem;
                dropped = true;
                break;
            }
            world.dropItem(item.getLocation(), droppedItem);
        }
        if(!dropped) return;

        boolean whitelisted = false;
        boolean blacklisted = false;
        String blockName = block.name().toUpperCase();
        boolean blackList, match;
        for (String s : args){
            blackList = s.charAt(0) == '!';
            match = blockName.matches(String.format("(.*)%s(.*)$", blackList ? s.substring(1) : s));
            if(!blackList && match)
                whitelisted = true;
            else if (blackList && match)
                blacklisted = true;
        }
        if(!whitelisted || blacklisted){
            world.dropItem(blockState.getLocation(), blockDrop);
            return;
        }

        ItemStack item = ItemUtils.getSmeltingResult(blockDrop.getType());
        if(item == null) {
            world.dropItem(blockState.getLocation(), blockDrop);
            return;
        }
        world.dropItem(blockState.getLocation(), item);
    }
}
