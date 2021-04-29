package EnhancedItems.command;

import EnhancedItems.App;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Recipe;

import java.util.logging.Level;

public class GiveItem implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            Bukkit.getLogger().log(Level.INFO,"Command can only be executed by a player.");
            return false;
        }
        if(args.length == 2) giveItem(Bukkit.getPlayer(args[0]), args[1]);
        else if(args.length == 1){
            giveItem((Player)sender, args[0]);
            return true;
        }
        return false;
    }

    private void giveItem(Player player, String item){
        Recipe recipe = Bukkit.getRecipe(new NamespacedKey(App.getPlugin(), item.toLowerCase()));
        if(recipe != null) player.getInventory().addItem(recipe.getResult());
        else player.sendMessage(ChatColor.YELLOW + String.format("Item '%s' doesn't exist.", item));
    }
}
