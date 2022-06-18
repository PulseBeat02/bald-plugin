package io.github.pulsebeat02.baldplugin.event;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import io.github.pulsebeat02.baldplugin.utils.WorldUtils;
import java.util.Collection;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.jetbrains.annotations.NotNull;

public final class AdvancementListener implements Listener {

  @EventHandler
  public void onAdvancementProgress(@NotNull final PlayerAdvancementCriterionGrantEvent event) {
    final Advancement advancement = event.getAdvancement();
    final String criterion = event.getCriterion();
    final Player player = event.getPlayer();
    if (WorldUtils.checkCreativeWorld(player)) {
      this.revokeCriteria(advancement, criterion, player);
    }
  }

  private void revokeCriteria(@NotNull final Advancement advancement, @NotNull final String criterion, @NotNull final Player player) {
    player.getAdvancementProgress(advancement).revokeCriteria(criterion);
  }

  @EventHandler
  public void onAdvancementDone(@NotNull final PlayerAdvancementDoneEvent event) {
    final Advancement advancement = event.getAdvancement();
    final Player player = event.getPlayer();
    if (WorldUtils.checkCreativeWorld(player)) {
      this.revokeAdvancement(advancement, player);
    }
  }

  private void revokeAdvancement(@NotNull final Advancement advancement, @NotNull final Player player) {
    final Collection<String> criterion = advancement.getCriteria();
    final AdvancementProgress progress = player.getAdvancementProgress(advancement);
    criterion.forEach(progress::revokeCriteria);
  }
}
