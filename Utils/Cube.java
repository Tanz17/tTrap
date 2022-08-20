package ru.tanz.Utils;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import ru.tanz.tTraps;

import java.util.*;

public class Cube {

    public static void spawnCube(Location l) {
        int radius = tTraps.getInstance().getConfig().getInt("traps.cube.radius");
        int time = tTraps.getInstance().getConfig().getInt("traps.cube.time-for-remove");
        String cfgblock = tTraps.getInstance().getConfig().getString("traps.cube.block");
        List<BlockHolder> blockholder = new ArrayList<>();

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for (int y = -radius; y <= radius; y++) {
                    Block block = l.getBlock().getRelative(x, y, z);
                    blockholder.add(new BlockHolder(block));
                    assert cfgblock != null;
                    if(isInRegion(block.getLocation())){
                        return;
                    }
                    block.setType(Objects.requireNonNull(Material.getMaterial(cfgblock)));

                }
            }
        }
        for (int x = -(radius-1); x <= (radius-1); x++) {
            for (int z = -(radius-1); z <= (radius-1); z++) {
                for (int y = -(radius-1); y <= (radius-1); y++) {
                    Block block = l.getBlock().getRelative(x, y, z);
                    if(isInRegion(block.getLocation())){
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
