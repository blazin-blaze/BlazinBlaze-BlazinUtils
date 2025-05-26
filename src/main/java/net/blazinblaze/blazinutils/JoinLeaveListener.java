package net.blazinblaze.blazinutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class JoinLeaveListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {

        Player player = e.getPlayer();
        e.quitMessage(Component.text(player.displayName() + "'s flame has dwindled out.").color(TextColor.color(4862243)));

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();

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

        e.joinMessage(Component.text(player.displayName() + "'s flame has ignited!").color(TextColor.color(11350273)));

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
                final Component component = Component.text("Proximity voice chat is available if you're interested!")
                        .decorate(TextDecoration.BOLD)
                        .color(TextColor.color(16777215))
                        .append(Component.text(" Click here to install Simple Voice Chat (fabric/forge/etc)!", NamedTextColor.AQUA).decorate(TextDecoration.ITALIC).decorate(TextDecoration.UNDERLINED))
                        .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://modrinth.com/plugin/simple-voice-chat"));
                playerInstance.sendMessage(component);
                playerInstance.playSound(playerInstance.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0F, 1.0F);
            }
        }
    }

}
