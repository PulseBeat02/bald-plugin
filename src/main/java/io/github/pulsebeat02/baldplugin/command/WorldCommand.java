package io.github.pulsebeat02.baldplugin.command;

import io.github.pulsebeat02.baldplugin.BaldPlugin;
import io.github.pulsebeat02.baldplugin.locale.Locale;
import io.github.pulsebeat02.baldplugin.utils.CommandUtils;
import io.github.pulsebeat02.baldplugin.world.StoredLocation;
import io.github.pulsebeat02.baldplugin.world.WorldManager;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WorldCommand implements BaldCommand {

  private static final List<String> VALID_WORLDS;
  private static final Set<String> VALID_SURVIVAL_WORLDS;

  static {
    VALID_WORLDS = List.of("survival", "creative");
    VALID_SURVIVAL_WORLDS = Set.of("world", "world_nether", "world_the_end");
  }

  private final BaldPlugin plugin;

  public WorldCommand(@NotNull final BaldPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command,
      @NotNull final String label, @NotNull final String[] args) {

    if (this.isValidPlayer(sender)) {
      return true;
    }

    if (this.isValidArgumentLength(sender, args)) {
      return true;
    }

    final String world = this.isValidWorld(sender, args);
    if (world == null) {
      return true;
    }

    final Player player = (Player) sender;
    if (this.checkDifferentWorld(player, world)) {
      return true;
    }

    if (world.equals("creative")) {
      this.handleWorld(player, "survival", "creative");
    } else if (world.equals("survival")) {
      this.handleWorld(player, "creative", "survival");
    }

    return true;
  }

  private boolean checkDifferentWorld(@NotNull final Player player, @NotNull final String to) {

    final String world = player.getLocation().getWorld().getName();
    if (to.equals("creative") && world.equals("creative")) {
      player.sendMessage(Locale.ERR_SAME_WORLD.build());
      return true;
    }

    if (to.equals("survival") && VALID_SURVIVAL_WORLDS.contains(world)) {
      player.sendMessage(Locale.ERR_SAME_WORLD.build());
      return true;
    }

    return false;
  }

  private void handleWorld(@NotNull final Player player, @NotNull final String from,
      @NotNull final String to) {

    final Location location = player.getLocation();
    final WorldManager manager = this.plugin.getWorldManager();
    final StoredLocation stored = manager.getWorldLocation(player.getUniqueId());

    this.setPreviousLocation(from, location, stored);
    this.teleportPlayer(to, player, stored);
    this.saveLocations(manager);
  }

  private void saveLocations(@NotNull final WorldManager manager) {
    try {
      manager.save();
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  private void setPreviousLocation(@NotNull final String from, final Location location,
      @NotNull final StoredLocation stored) {
    stored.setLocation(from, location);
  }

  private void teleportPlayer(@NotNull final String to, @NotNull final Player player,
      @NotNull final StoredLocation stored) {

    final Location tp = stored.getLocation(to);
    final String command = "mvtp %s %s".formatted(player.getName(), tp.getWorld().getName());

    CommandUtils.executeConsoleCommand(command);
    player.teleport(tp);
  }

  @Nullable
  private String isValidWorld(@NotNull final CommandSender sender, final String @NotNull [] args) {
    final String world = args[0].toLowerCase(java.util.Locale.ROOT);
    if (!this.checkWorld(world)) {
      sender.sendMessage(Locale.ERR_WORLD_INVALID.build());
      return null;
    }
    return world;
  }

  private boolean isValidArgumentLength(@NotNull final CommandSender sender,
      final String @NotNull [] args) {
    final int length = args.length;
    if (length == 0) {
      sender.sendMessage(Locale.ERR_WORLD_NOT_SPECIFIED.build());
      return true;
    }
    return false;
  }

  private boolean isValidPlayer(@NotNull final CommandSender sender) {
    if (!(sender instanceof Player)) {
      sender.sendMessage(Locale.ERR_INVALID_SENDER.build());
      return true;
    }
    return false;
  }

  private boolean checkWorld(@NotNull final String world) {
    return VALID_WORLDS.contains(world);
  }

  @Override
  public @NotNull List<String> onTabComplete(@NotNull final CommandSender sender,
      @NotNull final Command command, @NotNull final String label, final String @NotNull [] args) {
    return VALID_WORLDS;
  }
}
