package ru.tanz;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ru.tanz.Utils.BlockHolder;
import ru.tanz.Utils.Cube;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Events implements Listener {

    //Ивент был сделан скитайловым, не позорьтесь сами
    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (e.getAction() != Action.LEFT_CLICK_AIR && e.getAction() != Action.LEFT_CLICK_BLOCK && p.getItemInHand().getType() == Material.getMaterial(tTraps.getInstance().getConfig().getString("item.material"))) {
            ItemStack item = p.getItemInHand();
            if (item.getItemMeta().getDisplayName().equals(tTraps.getInstance().getConfig().getString("item.name").replace("&", "§"))) {
                if (item.getAmount() == 1) {







                    int radius = tTraps.getInstance().getConfig().getInt("traps.cube.radius");
                    int time = tTraps.getInstance().getConfig().getInt("traps.cube.time-for-remove");
                    String cfgblock = tTraps.getInstance().getConfig().getString("traps.cube.block");
                    List<BlockHolder> blockholder = new ArrayList<>();

                    for (int x = -radius; x <= radius; x++) {
                        for (int z = -radius; z <= radius; z++) {
                            for (int y = -radius; y <= radius; y++) {
                                Block block = p.getLocation().getBlock().getRelative(x, y, z);
                                blockholder.add(new BlockHolder(block));
                                assert cfgblock != null;
                                if(isInRegion(block.getLocation())){
                                    p.sendMessage(tTraps.getInstance().getConfig().getString("commands.inregion").replace("&", "§"));
                                    e.setCancelled(true);
                                    return;
                                }
                                block.setType(Objects.requireNonNull(Material.getMaterial(cfgblock)));

                            }
                        }
                    }
                    for (int x = -(radius-1); x <= (radius-1); x++) {
                        for (int z = -(radius-1); z <= (radius-1); z++) {
                            for (int y = -(radius-1); y <= (radius-1); y++) {
                                Block block = p.getLocation().getBlock().getRelative(x, y, z);
                                if(isInRegion(block.getLocation())){
                                    p.sendMessage(tTraps.getInstance().getConfig().getString("commands.inregion").replace("&", "§"));
                                    e.setCancelled(true);
                                    return;
                                }
                                block.setType(Material.AIR);
                            }
                        }
                    }

                    Bukkit.getScheduler().runTaskLater(tTraps.getInstance(), ()-> {
                        for (int x = -radius; x <= radius; x++) {
                            for (int z = -radius; z <=radius; z++) {
                                for (int y = -radius; y <= radius; y++) {
                                    for (BlockHolder blockinfo : blockholder){
                                        try{
                                            blockinfo.blocklocatin.getBlock().setType(blockinfo.blockmaterial);
                                        } catch(NullPointerException ex){
                                            blockinfo.blocklocatin.getBlock().setType(Material.AIR);
                                        }
                                    }

                                }
                            }
                        }
                    }, time * 20L);


















                    removeItem(p);
                    p.sendMessage(tTraps.getInstance().getConfig().getString("item.used").replace("&", "§"));
                    p.sendTitle(tTraps.getInstance().getConfig().getString("item.title-header").replace("&", "§"), tTraps.getInstance().getConfig().getString("item.title-footer").replace("&", "§"));
                    } else {
                        p.sendMessage(tTraps.getInstance().getConfig().getString("item.cooldown").replace("&", "§"));
                }

                if (item.getAmount() > 1) {
                    p.sendMessage(tTraps.getInstance().getConfig().getString("item.no-stack").replace("&", "§"));
                }
            }
        }
    }
    public static void removeItem(Player p) {
        int getamount = p.getItemInHand().getAmount();
        p.getItemInHand().setAmount(getamount - 1);
    }


    public static String getRegion(Location loc) {
        ApplicableRegionSet regions = WorldGuardPlugin.inst().getRegionManager(loc.getWorld()).getApplicableRegions(loc);

        for (ProtectedRegion region : regions.getRegions()) {
            return region.getId();
        }

        return null;
    }


    public static boolean isInRegion(Location loc) {
        return getRegion(loc) != null;
    }

}
