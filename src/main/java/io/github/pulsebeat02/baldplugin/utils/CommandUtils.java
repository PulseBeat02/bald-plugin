package io.github.pulsebeat02.baldplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public final class CommandUtils {

  private static final ConsoleCommandSender CONSOLE;

  static {
    CONSOLE = Bukkit.getConsoleSender();
  }

  private CommandUtils() {}

  public static boolean executeConsoleCommand(@NotNull final String command) {
    return Bukkit.dispatchCommand(CONSOLE, command);
  }
}
