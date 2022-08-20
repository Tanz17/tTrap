package ru.tanz;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public final class tTraps extends JavaPlugin {
    static tTraps inst;
    static String IP;

    public static tTraps getInstance(){
        return inst;
    }

    @Override
    public void onEnable() {
        inst = this;
        saveDefaultConfig();
        getCommand("ttrap").setExecutor(new Command());
        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }

    public void onDisable() {
        Logger log = Logger.getLogger("");
        log.info("§e| §c████████╗ █████╗ ███╗  ██╗███████╗");
        log.info("§e| §c╚══██╔══╝██╔══██╗████╗ ██║╚════██║");
        log.info("§e| §c   ██║   ███████║██╔██╗██║  ███╔═╝");
        log.info("§e| §c   ██║   ██╔══██║██║╚████║██╔══╝");
        log.info("§e| §c   ██║   ██║  ██║██║ ╚███║███████╗");
        log.info("§e| §c   ╚═╝   ╚═╝  ╚═╝╚═╝  ╚══╝╚══════╝");
        log.info("");
        log.info("§e| §fПлагин §ctTraps §7& §fВерсия плагина - §a1.0");
        log.info("§e| §fРазработчик - §e§nvk.com/tanz_ind");
    }
}
