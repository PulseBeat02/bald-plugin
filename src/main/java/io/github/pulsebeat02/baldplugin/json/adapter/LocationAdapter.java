package io.github.pulsebeat02.baldplugin.json.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class LocationAdapter extends TypeAdapter<Location> {

  @Override
  public void write(@NotNull final JsonWriter out, @NotNull final Location value)
      throws IOException {
    out.beginObject();
    out.name("world").value(value.getWorld().getName());
    out.name("x").value(value.getX());
    out.name("y").value(value.getY());
    out.name("z").value(value.getZ());
    out.name("yaw").value(value.getYaw());
    out.name("pitch").value(value.getPitch());
    out.endObject();
  }

  @Override
  public Location read(@NotNull final JsonReader in) throws IOException {

    in.beginObject();

    String world = "";
    double x = 0.0;
    double y = 0.0;
    double z = 0.0;
    float yaw = 0;
    float pitch = 0;

    while (in.hasNext()) {
      switch (in.nextName()) {
        case "world" -> world = in.nextString();
        case "x" -> x = in.nextDouble();
        case "y" -> y = in.nextDouble();
        case "z" -> z = in.nextDouble();
        case "yaw" -> yaw = (float) in.nextDouble();
        case "pitch" -> pitch = (float) in.nextDouble();
      }
    }

    in.endObject();

    return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
  }
}
