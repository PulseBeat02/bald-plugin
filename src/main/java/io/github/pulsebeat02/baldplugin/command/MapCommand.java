package io.github.pulsebeat02.baldplugin.command;

import io.github.pulsebeat02.baldplugin.locale.Locale;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MapCommand implements BaldCommand {

  @Override
  public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
      @NotNull final String label, @NotNull final String[] args) {
    sender.sendMessage(Locale.DYNMAP_MESSAGE.build());
    return false;
  }

  @Override
  public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender,
      @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
    return null;
  }
}
