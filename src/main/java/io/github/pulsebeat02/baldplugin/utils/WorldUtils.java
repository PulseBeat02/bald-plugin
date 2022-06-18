package io.github.pulsebeat02.baldplugin.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class WorldUtils {

  private WorldUtils() {}

  public static boolean checkCreativeWorld(@NotNull final Player player) {
    final Location location = player.getLocation();
    final World world = location.getWorld();
    final String name = world.getName();
    return name.equals("creative");
  }
}
