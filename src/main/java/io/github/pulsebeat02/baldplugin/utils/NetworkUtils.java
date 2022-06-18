package io.github.pulsebeat02.baldplugin.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class NetworkUtils {

  private NetworkUtils() {
  }

  private static final String IP_RETRIEVER;
  private static final String SERVER_IP;
  private static final String DYNAMP_IP;

  static {
    IP_RETRIEVER = "http://checkip.amazonaws.com";
    try {
      final URL url = new URL(IP_RETRIEVER);
      try (final BufferedReader in = new BufferedReader(new InputStreamReader(
          url.openStream()))) {
        SERVER_IP = in.readLine();
      }
    } catch (final IOException e) {
      throw new AssertionError(e);
    }
    DYNAMP_IP = "%s:8888".formatted(SERVER_IP);
  }

  public static String getServerIP() {
    return SERVER_IP;
  }

  public static String getDynmapIP() {
    return DYNAMP_IP;
  }
}
