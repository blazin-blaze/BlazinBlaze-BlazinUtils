package net.blazinblaze.blazinutils;

import net.blazinblaze.blazinutils.commands.*;
import net.blazinblaze.blazinutils.data.PlayerMemory;
import net.blazinblaze.blazinutils.utility.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class BlazinUtils extends JavaPlugin {

    private static BlazinUtils instance;
    private static boolean purgeActive;
    private static boolean spawnEventActive;
    private static boolean isTimedPurgeActive;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        purgeActive = false;
        spawnEventActive = false;
        isTimedPurgeActive = false;
        getLogger().info("BlazinUtils is enabled!");
        getCommand("discord").setExecutor(new DiscordCommand());
        getCommand("youtube").setExecutor(new YouTubeCommand());
        getCommand("youtube2").setExecutor(new YouTube2Command());
        getCommand("twitch").setExecutor(new TwitchCommand());
        getCommand("servermap").setExecutor(new MapCommand());
        getCommand("serverip").setExecutor(new ServerIPCommand());
        getCommand("serverinfo").setExecutor(new InfoCommand());
        getCommand("servervc").setExecutor(new ServerVCCommand());
        getCommand("announcement").setExecutor(new AnnouncementCommand());
        getCommand("announcementchat").setExecutor(new ChatAnnouncementCommand());
        getCommand("playtimeboard").setExecutor(new PlaytimeBoardCommand());
        getCommand("playtimeuser").setExecutor(new PlaytimeUserCommand());
        getCommand("purgetoggle").setExecutor(new PurgeToggleCommand());
        getCommand("purgetimed").setExecutor(new PurgeTimedCommand());
        getCommand("tpspawn").setExecutor(new TPSpawnCommand());
        getCommand("tpreturn").setExecutor(new TPReturnCommand());
        getCommand("spawneventtoggle").setExecutor(new SpawnEventToggleCommand());

        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                for(int i = 0; i < players.size(); i++) {
                    Player plr = players.get(i);
                    PlayerMemory memory = PlayerUtil.getPlayerMemory(plr);
                    memory.setSecondsPlayed(memory.getSecondsPlayed() + 1);
                }
            }
        }.runTaskTimer(this, 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

                for(int i = 0; i < players.size(); i++) {
                    Player plr = players.get(i);
                    PlayerMemory memory = PlayerUtil.getPlayerMemory(plr);
                    File f = new File(PlayerUtil.getFolderPath(plr) + "/general.yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
                    cfg.set("data.team", memory.getTeam());
                    cfg.set("data.secondsPlayed", memory.getSecondsPlayed());
                    cfg.set("data.locationTP", memory.getLastLocationBeforeTP());

                    try{cfg.save(f);} catch (IOException eo){eo.printStackTrace();}
                }
            }
        }.runTaskTimer(this, 20*300, 20*300);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());

        for(int i = 0; i < players.size(); i++) {
            Player plr = players.get(i);
            PlayerMemory memory = PlayerUtil.getPlayerMemory(plr);
            File f = new File(PlayerUtil.getFolderPath(plr) + "/general.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            cfg.set("data.team", memory.getTeam());
            cfg.set("data.secondsPlayed", memory.getSecondsPlayed());
            cfg.set("data.locationTP", memory.getLastLocationBeforeTP());

            try{cfg.save(f);} catch (IOException eo){eo.printStackTrace();}
            PlayerUtil.setPlayerMemory(plr, null);
        }

        instance = null;
        purgeActive = false;
        isTimedPurgeActive = false;
        spawnEventActive = false;

        getLogger().info("BlazinUtils is disabled!");
    }

    public static BlazinUtils getInstance() {
        return instance;
    }

    public static boolean isPurgeActive() {
        return purgeActive;
    }

    public static void setPurgeActive(boolean value) {
        purgeActive = value;
    }

    public static boolean isSpawnEventActive() {
        return spawnEventActive;
    }

    public static void setSpawnEventActive(boolean value) {
        spawnEventActive = value;
    }

    public static boolean isTimedPurgeActive() {
        return isTimedPurgeActive;
    }

    public static void setTimedPurgeActive(boolean value) {
        isTimedPurgeActive = value;
    }
}
