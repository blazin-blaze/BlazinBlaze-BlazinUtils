package net.blazinblaze.blazinutils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerIPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            final Component component = Component.text("Server IP:")
                    .decorate(TextDecoration.BOLD)
                    .color(TextColor.color(4868682))
                    .append(Component.text(" play.blazinblaze.net", NamedTextColor.AQUA).decorate(TextDecoration.ITALIC).decorate(TextDecoration.UNDERLINED));
            player.sendMessage(component);

        }

        return true;
    }
}
