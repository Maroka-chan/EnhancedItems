package EnhancedItems.attribute;

import EnhancedItems.App;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Event;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Attribute {
    private Attribute(){}

    public static final NamespacedKey namespacedKey = new NamespacedKey(App.getPlugin(), "attributes");
    private static final Map<String, AttributeMethod> attributeMethods = new HashMap<>();

    static {
        attributeMethods.put("AUTO_SMELT", AutoSmelt::trigger);
    }

    public static void invoke(String attribute, Event event, String[] args) {
        AttributeMethod attributeMethod = attributeMethods.get(attribute);
        if(attributeMethod != null) attributeMethod.trigger(event, args);
        else Bukkit.getLogger().log(Level.SEVERE, String.format("Unrecognized attribute '%s'.", attribute));
    }
}
