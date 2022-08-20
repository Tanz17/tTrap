package ru.tanz;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import ru.tanz.Utils.Item;

import java.util.ArrayList;
import java.util.List;

public class Command implements CommandExecutor {
    String playername;
    String itemname;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if (!sender.hasPermission("ttraps.give")) {
                sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.noPerm"));
                return true;
            } else if (args.length == 0) {
                sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.giveHelp"));
                return false;
            } else {
                if (args[0].equalsIgnoreCase("give")) {
                    try {
                        this.playername = args[1];
                        try {
                            this.itemname = args[2];
                        } catch (IndexOutOfBoundsException ex) {
                            sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.giveHelp"));
                            return false;
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.giveHelp"));
                        return false;
                    }

                    Player player = Bukkit.getPlayer(this.playername);
                    if (player == null) {
                        sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.noPlayer"));
                        return true;
                    }

                    List<String> items = new ArrayList<>();
                    items.add("trap");
                    if (!items.contains(this.itemname)) {
                        sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.giveHelp"));
                        return true;
                    }

                    PlayerInventory inv;
                    if (this.itemname.equals("trap")) {
                        inv = player.getInventory();
                        inv.addItem(Item.traps());
                        sender.sendMessage(tTraps.getInstance().getConfig().getString("commands.succesfull"));
                    }
                }

                return false;
            }
        }
}
