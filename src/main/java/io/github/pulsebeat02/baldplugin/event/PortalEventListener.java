package io.github.pulsebeat02.baldplugin.event;

import io.github.pulsebeat02.baldplugin.utils.WorldUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.jetbrains.annotations.NotNull;

public final class PortalEventListener implements Listener {

  @EventHandler
  public void onPortalEnter(@NotNull final PlayerPortalEvent event) {
    final Player player = event.getPlayer();
    if (WorldUtils.checkCreativeWorld(player)) {
      event.setCancelled(true);
    }
  }
}
