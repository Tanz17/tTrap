package ru.tanz.Utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.tanz.tTraps;

public class Item {

    public static ItemStack traps() {
        ItemStack item = new ItemStack(Material.getMaterial(tTraps.getInstance().getConfig().getString("item.material")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(tTraps.getInstance().getConfig().getString("item.name").replace("&", "§"));
        List<String> lore = new ArrayList<>();

        for (String s : tTraps.getInstance().getConfig().getStringList("item.lore")) {
            lore.add(s.replace("&", "§"));
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }
}
