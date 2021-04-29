package EnhancedItems.attribute;

import EnhancedItems.util.ItemUtils;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public final class AutoSmelt implements Attribute {
    private static final List<String> whitelist = Arrays.asList(
            "ORE",
            "DEBRIS"
    );

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

        String blockName = block.name().toUpperCase();
        for (String s : args)
            if(blockName.matches(String.format("(.*)%s(.*)$", s))) {
                world.dropItem(blockState.getLocation(), blockDrop);
                return;
            }

        boolean whitelisted = false;
        for (String s : whitelist)
            if(blockName.matches(String.format("(.*)%s(.*)$", s))){
                whitelisted = true;
                break;
            }
        if(!whitelisted) {
            world.dropItem(blockState.getLocation(), blockDrop);
            return;
        }
        ItemStack item = ItemUtils.getSmeltingResult(block);
        if(item == null) {
            world.dropItem(blockState.getLocation(), blockDrop);
            return;
        }
        player.getInventory().addItem(item);
    }
}
