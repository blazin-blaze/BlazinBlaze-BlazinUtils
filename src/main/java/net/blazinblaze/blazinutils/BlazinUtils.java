package net.blazinblaze.blazinutils;

import org.bukkit.plugin.java.JavaPlugin;

public final class BlazinUtils extends JavaPlugin {

    private static BlazinUtils instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getLogger().info("BlazinUtils is enabled!");
        getCommand("discordcmd").setExecutor(new DiscordCommand());
        getCommand("youtubecmd").setExecutor(new YouTubeCommand());
        getCommand("youtube2cmd").setExecutor(new YouTube2Command());
        getCommand("twitchcmd").setExecutor(new TwitchCommand());
        getCommand("mapcmd").setExecutor(new MapCommand());
        getCommand("serveripcmd").setExecutor(new ServerIPCommand());
        getCommand("infocmd").setExecutor(new InfoCommand());
        getCommand("announcement").setExecutor(new ServerIPCommand());
        getCommand("announcementchat").setExecutor(new InfoCommand());

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
