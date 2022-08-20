package ru.tanz.Utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class BlockHolder {
    public Location blocklocatin;
    public Material blockmaterial;

    public BlockHolder (Block b){
        blocklocatin = b.getLocation();
        blockmaterial = b.getType();
    }
}
