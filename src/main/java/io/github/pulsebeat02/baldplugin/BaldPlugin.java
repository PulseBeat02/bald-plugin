package io.github.pulsebeat02.baldplugin;

import static java.util.Objects.requireNonNull;

import io.github.pulsebeat02.baldplugin.command.BaldCommand;
import io.github.pulsebeat02.baldplugin.command.MapCommand;
import io.github.pulsebeat02.baldplugin.command.WorldCommand;
import io.github.pulsebeat02.baldplugin.event.PortalEventListener;
import io.github.pulsebeat02.baldplugin.utils.CommandUtils;
import io.github.pulsebeat02.baldplugin.world.WorldManager;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class BaldPlugin extends JavaPlugin {

  private WorldManager worldManager;

  @Override
  public void onEnable() {
    this.initialize();
  }

  private void initialize() {
    new BukkitRunnable() {
      @Override
      public void run() {
        BaldPlugin.this.createParentFolder();
        BaldPlugin.this.setGamerules();
        BaldPlugin.this.loadWorldManager();
        BaldPlugin.this.loadEvents();
        BaldPlugin.this.loadCommands();
      }
    }.runTaskLater(this, 1L);
  }

  private void loadWorldManager() {
    try {
      this.worldManager = new WorldManager(this);
    } catch (final IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDisable() {
  }

  private void setGamerules() {
    CommandUtils.executeConsoleCommand("mvrule announceAdvancements false creative");
  }

  private void createParentFolder() {
    final Path folder = this.getDataFolder().toPath();
    if (Files.notExists(folder)) {
      this.throwFolderException(folder);
    }
  }

  private void throwFolderException(@NotNull final Path folder) {
    if (!folder.toFile().mkdirs()) {
      throw new AssertionError("Could not create folder: %s".formatted(folder));
    }
  }

  private void loadCommands() {
    Map.of("map", new MapCommand(), "world", new WorldCommand(this)).forEach(
        this::registerCommand
    );
  }

  private void loadEvents() {
    Set.of(new PortalEventListener()).forEach(this::registerEvent);
  }

  private void registerEvent(@NotNull final Listener listener) {
    this.getServer().getPluginManager().registerEvents(listener, this);
  }

  private void registerCommand(@NotNull final String key, @NotNull final BaldCommand value) {
    final PluginCommand command = requireNonNull(this.getCommand(key));
    command.setExecutor(value);
    command.setTabCompleter(value);
  }

  public @NotNull WorldManager getWorldManager() {
    return this.worldManager;
  }
}
