package io.github.pulsebeat02.baldplugin.json.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class UUIDAdapter extends TypeAdapter<UUID> {

  @Override
  public void write(@NotNull final JsonWriter out, @NotNull final UUID value) throws IOException {
    out.beginObject();
    out.name("uuid").value(value.toString());
    out.endObject();
  }

  @Override
  public @NotNull UUID read(@NotNull final JsonReader in) throws IOException {
    in.beginObject();
    final String id = in.nextString();
    in.close();
    return UUID.fromString(id);
  }
}