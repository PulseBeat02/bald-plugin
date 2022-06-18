package io.github.pulsebeat02.baldplugin.world;

import static java.util.Objects.requireNonNull;

import com.google.common.collect.Maps;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class StoredLocation {

  private final Map<String, Location> locations;

  public StoredLocation() {
    this.locations = Maps.newHashMap();
  }

  public void setLocation(@NotNull final String world, @NotNull final Location location) {
    this.locations.put(world, location);
  }

  public Location getLocation(@NotNull final String world) {
    return this.locations.getOrDefault(world, requireNonNull(
        requireNonNull(Bukkit.getWorld(this.getCorrectWorldLocation(world))).getSpawnLocation()));
  }

  public @NotNull String getCorrectWorldLocation(@NotNull final String world) {
    return requireNonNull(world.equals("creative") ? "creative" : "world");
  }
}
