package EnhancedItems.attribute;

import EnhancedItems.App;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;

import java.util.logging.Level;

public interface Attribute {
    NamespacedKey namespacedKey = new NamespacedKey(App.getPlugin(), "attributes");

    AttributeMethod AUTO_SMELT = AutoSmelt::trigger;


    static void trigger(Event e, String[] args){
        Bukkit.getLogger().log(Level.WARNING, "Attribute method missing");
    }
}
