package net.blazinblaze.blazinutils.commands;

import net.blazinblaze.blazinutils.BlazinUtils;
import net.blazinblaze.blazinutils.data.PlayerMemory;
import net.blazinblaze.blazinutils.utility.PlayerUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TPSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (commandSender instanceof Player plr) {
            if(BlazinUtils.isSpawnEventActive()) {
                BlazinUtils instance = BlazinUtils.getInstance();
                if(instance != null) {
                    PlayerMemory memory = PlayerUtil.getPlayerMemory(plr);
                    memory.setLastLocationBeforeTP(plr.getLocation());
                    plr.teleport(new Location(instance.getServer().getWorlds().getFirst(), 0, 80, 0));
                    final Component component = Component.text("Successfully teleported to spawn!")
                            .color(TextColor.color(42496));
                    commandSender.sendMessage(component);
                }
            }else {
                final Component component = Component.text("A spawn event is not currently active.")
                        .color(TextColor.color(14430499));
                commandSender.sendMessage(component);
            }
        }
        return true;
    }
}
