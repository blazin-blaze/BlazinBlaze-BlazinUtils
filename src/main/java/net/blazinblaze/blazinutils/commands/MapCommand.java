package net.blazinblaze.blazinutils.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            final Component component = Component.text("Server Map - ")
                    .decorate(TextDecoration.BOLD)
                    .color(TextColor.color(1976316))
                    .append(Component.text("[map.blazinblaze.net, click here!]", NamedTextColor.AQUA).decorate(TextDecoration.ITALIC).decorate(TextDecoration.UNDERLINED))
                    .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.OPEN_URL,"https://map.blazinblaze.net"));
            player.sendMessage(component);

        }

        return true;
    }
}
