package net.blazinblaze.blazinutils.commands;

import net.blazinblaze.blazinutils.BlazinUtils;
import net.blazinblaze.blazinutils.data.PlayerMemory;
import net.blazinblaze.blazinutils.utility.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        PlayerMemory memory = PlayerUtil.getPlayerMemory(player);
        File f = new File(PlayerUtil.getFolderPath(player) + "/general.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
        cfg.set("data.team", memory.getTeam());
        cfg.set("data.secondsPlayed", memory.getSecondsPlayed());
        cfg.set("data.locationTP", memory.getLastLocationBeforeTP());

        try{cfg.save(f);} catch (IOException eo){eo.printStackTrace();}
        PlayerUtil.setPlayerMemory(player, null);

        e.quitMessage(player.displayName().color(TextColor.color(4862243)).append(Component.text("'s flame has dwindled out.").color(TextColor.color(4862243))));

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

        PlayerMemory memory = new PlayerMemory();
        File f = new File(PlayerUtil.getFolderPath(player) + "/general.yml");

        if(f.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(f);
            memory.setTeam(cfg.getString("data.team"));
            memory.setSecondsPlayed(cfg.getInt("data.secondsPlayed"));
            memory.setLastLocationBeforeTP(cfg.getLocation("data.locationTP"));
        }else {
            memory.setTeam("none");
            memory.setSecondsPlayed(0);
            BlazinUtils instance = BlazinUtils.getInstance();
            if(instance != null) {
                memory.setLastLocationBeforeTP(new Location(instance.getServer().getWorlds().getFirst(), 0, 0, 0));
            }
        }

        PlayerUtil.setPlayerMemory(player, memory);

        if(player.hasPlayedBefore()) {
            final Component component = Component.text("Welcome back!").decorate(TextDecoration.BOLD)
                    .color(TextColor.color(13657113))
                    .append(Component.text(" Blazin!", NamedTextColor.RED));
            player.sendMessage(component);
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 10.0F, 1.0F);
        }else {
            final Component component = Component.text("Welcome to the Blazin MC Server!").decorate(TextDecoration.BOLD)
                    .color(TextColor.color(13657113))
                    .append(Component.text(" I hope you enjoy your stay!", NamedTextColor.RED));
            player.sendMessage(component);
            player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 10.0F, 1.0F);
            SendMessageLater laterMessage = new SendMessageLater(player);
            laterMessage.runTaskLater(BlazinUtils.getInstance(), 20L * 5);
        }

        e.joinMessage(player.displayName().color(TextColor.color(11350273)).append(Component.text("'s flame has ignited!")).color(TextColor.color(11350273)));

    }

    private static class SendMessageLater extends BukkitRunnable {

        Player playerInstance;

        private SendMessageLater(Player player) {
            playerInstance = player;
        }

        @Override
        public void run() {
            if(playerInstance != null && playerInstance.isOnline()) {
                final Component component2 = Component.text("Make sure to check out the information board at spawn for rules, useful commands, and various features!").decorate(TextDecoration.BOLD)
                        .color(TextColor.color(8355711));
                playerInstance.sendMessage(component2);
                final Component component = Component.text("Proximity voice chat is available if you're interested! - ")
                        .decorate(TextDecoration.BOLD)
                        .color(TextColor.color(16777215))
                        .append(Component.text("Click here to install Simple Voice Chat (fabric/forge/etc)!", NamedTextColor.AQUA).decorate(TextDecoration.ITALIC).decorate(TextDecoration.UNDERLINED))
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://modrinth.com/plugin/simple-voice-chat"));
                playerInstance.sendMessage(component);
                playerInstance.playSound(playerInstance.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0F, 1.0F);
            }
        }
    }

}
