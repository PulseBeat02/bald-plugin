package io.github.pulsebeat02.baldplugin.world;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.github.pulsebeat02.baldplugin.BaldPlugin;
import io.github.pulsebeat02.baldplugin.json.GsonProvider;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class WorldManager {

  private final Map<UUID, StoredLocation> playerWorlds;
  private final Path file;

  public WorldManager(@NotNull final BaldPlugin plugin) throws IOException {
    this.file = plugin.getDataFolder().toPath().resolve("player-worlds.json");
    this.playerWorlds = this.read();
  }

  public void setWorldLocation(@NotNull final UUID player, @NotNull final StoredLocation world) {
    this.playerWorlds.put(player, world);
  }

  public StoredLocation getWorldLocation(@NotNull final UUID player) {

    final StoredLocation location = new StoredLocation();
    if (!this.playerWorlds.containsKey(player)) {
      this.playerWorlds.put(player, location);
      return location;
    }

    return this.playerWorlds.get(player);
  }

  public void save() throws IOException {
    final Gson gson = GsonProvider.getGson();
    try (final BufferedWriter writer = Files.newBufferedWriter(this.file)) {
      gson.toJson(this.playerWorlds, writer);
    }
  }

  public Map<UUID, StoredLocation> read() throws IOException {

    if (this.createFile()) {
      return Maps.newHashMap();
    }

    final Gson gson = GsonProvider.getGson();
    try (final BufferedReader reader = Files.newBufferedReader(this.file)) {
      final Type type = new TypeToken<Map<UUID, StoredLocation>>(){}.getType();
      final Map<UUID, StoredLocation> map = gson.fromJson(reader, type);
      return map == null ? Maps.newHashMap() : map;
    }
  }

  private boolean createFile() throws IOException {
    if (Files.notExists(this.file)) {
      Files.createFile(this.file);
      return true;
    }
    return false;
  }
}
