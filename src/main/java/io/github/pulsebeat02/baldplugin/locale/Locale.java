package io.github.pulsebeat02.baldplugin.locale;

import static net.kyori.adventure.text.Component.join;
import static net.kyori.adventure.text.Component.space;
import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.JoinConfiguration.separator;
import static net.kyori.adventure.text.event.ClickEvent.openUrl;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.DARK_GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.GOLD;
import static net.kyori.adventure.text.format.NamedTextColor.GRAY;
import static net.kyori.adventure.text.format.NamedTextColor.RED;
import static net.kyori.adventure.text.format.Style.style;
import static net.kyori.adventure.text.format.TextDecoration.BOLD;
import static net.kyori.adventure.text.format.TextDecoration.UNDERLINED;

import io.github.pulsebeat02.baldplugin.utils.NetworkUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface Locale {

  UniComponent<Sender, String> REQUIRED_ARGUMENT =
      (argument) ->
          text()
              .color(DARK_GRAY)
              .append(text("<"))
              .append(text(argument, GRAY))
              .append(text(">"))
              .build();

  UniComponent<Sender, String> OPTIONAL_ARGUMENT =
      (argument) ->
          text()
              .color(DARK_GRAY)
              .append(text("["))
              .append(text(argument, GRAY))
              .append(text("]"))
              .build();

  NullComponent<Sender> PLUGIN_PREFIX = () ->
      text()
          .color(AQUA)
          .append(
              text('['), text("SMP", GOLD), text(']'), space(), text(">", GRAY))
          .build();

  NullComponent<Sender> DYNMAP_MESSAGE = () ->
      format(
          text("A dynamic map of the server can be found ", GOLD)
              .append(text("here",
                  style(AQUA, BOLD, UNDERLINED, openUrl(NetworkUtils.getDynmapIP())))));

  NullComponent<Sender> ERR_WORLD_NOT_SPECIFIED = () -> format(
      text("You must specify a world to go to! (valid worlds: survival, creative)", RED));

  NullComponent<Sender> ERR_WORLD_INVALID = () -> format(
      text("That isn't a valid world! (valid worlds: survival, creative)", RED));

  NullComponent<Sender> ERR_INVALID_SENDER = () -> format(
      text("You must be a player to execute this command!", RED));

  NullComponent<Sender> ERR_SAME_WORLD = () -> format(
      text("You are already in that world!", RED));


  @FunctionalInterface
  interface NullComponent<S extends Sender> {

    Component build();

    default void send(@NotNull final S sender) {
      sender.sendMessage(format(this.build()));
    }
  }

  @FunctionalInterface
  interface UniComponent<S extends Sender, A0> {

    Component build(A0 arg0);

    default void send(@NotNull final S sender, final A0 arg0) {
      sender.sendMessage(format(this.build(arg0)));
    }
  }

  @FunctionalInterface
  interface BiComponent<S extends Sender, A0, A1> {

    Component build(A0 arg0, A1 arg1);

    default void send(@NotNull final S sender, @NotNull final A0 arg0, @NotNull final A1 arg1) {
      sender.sendMessage(format(this.build(arg0, arg1)));
    }
  }

  @FunctionalInterface
  interface TriComponent<S extends Sender, A0, A1, A2> {

    Component build(A0 arg0, A1 arg1, A2 arg2);

    default void send(
        @NotNull final S sender,
        @NotNull final A0 arg0,
        @NotNull final A1 arg1,
        @NotNull final A2 arg2) {
      sender.sendMessage(format(this.build(arg0, arg1, arg2)));
    }
  }

  @FunctionalInterface
  interface QuadComponent<S extends Sender, A0, A1, A2, A3> {

    Component build(A0 arg0, A1 arg1, A2 arg2, A3 arg3);

    default void send(
        @NotNull final S sender,
        @NotNull final A0 arg0,
        @NotNull final A1 arg1,
        @NotNull final A2 arg2,
        @NotNull final A3 arg3) {
      sender.sendMessage(format(this.build(arg0, arg1, arg2, arg3)));
    }
  }

  @FunctionalInterface
  interface PentaComponent<S extends Sender, A0, A1, A2, A3, A4> {

    Component build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4);

    default void send(
        @NotNull final S sender,
        @NotNull final A0 arg0,
        @NotNull final A1 arg1,
        @NotNull final A2 arg2,
        @NotNull final A3 arg3,
        @NotNull final A4 arg4) {
      sender.sendMessage(format(this.build(arg0, arg1, arg2, arg3, arg4)));
    }
  }

  @FunctionalInterface
  interface HexaComponent<S extends Sender, A0, A1, A2, A3, A4, A5> {

    Component build(A0 arg0, A1 arg1, A2 arg2, A3 arg3, A4 arg4, A5 arg5);

    default void send(
        @NotNull final S sender,
        @NotNull final A0 arg0,
        @NotNull final A1 arg1,
        @NotNull final A2 arg2,
        @NotNull final A3 arg3,
        @NotNull final A4 arg4,
        @NotNull final A5 arg5) {
      sender.sendMessage(format(this.build(arg0, arg1, arg2, arg3, arg4, arg5)));
    }
  }

  static @NotNull Component format(@NotNull final Component message) {
    return join(separator(space()), PLUGIN_PREFIX.build(), message);
  }
}
