package net.blazinblaze.blazinutils;

import org.bukkit.plugin.java.JavaPlugin;

public final class BlazinUtils extends JavaPlugin {

    private static BlazinUtils instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
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

        getServer().getPluginManager().registerEvents(new JoinLeaveListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
        getLogger().info("BlazinUtils is disabled!");
    }

    public static BlazinUtils getInstance() {
        return instance;
    }
}
