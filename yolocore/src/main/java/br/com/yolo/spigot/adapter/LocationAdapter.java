package br.com.yolo.spigot.adapter;

import br.com.yolo.core.Client;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.io.IOException;

public final class LocationAdapter extends TypeAdapter<Location> {

    @Override
    public void write(JsonWriter jsonWriter, Location location) throws IOException {
        if (location == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.value(getRaw(location));
    }

    @Override
    public Location read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return fromRaw(jsonReader.nextString());
    }

    private String getRaw(Location location) {
        JsonObject json = new JsonObject();
        json.addProperty("world", location.getWorld().getName());
        json.addProperty("x", location.getX());
        json.addProperty("y", location.getY());
        json.addProperty("z", location.getZ());
        json.addProperty("yaw", location.getYaw());
        json.addProperty("pitch", location.getPitch());
        return json.toString();
    }

    private Location fromRaw(String raw) {
        JsonObject json = Client.getJsonModule().read("main").fromJson(raw, JsonObject.class);
        String worldName = json.get("world").getAsString();
        World w = Bukkit.getWorld(worldName);
        if (w == null && !worldName.equals("world"))
            w = Bukkit.createWorld(new WorldCreator(worldName));
        return new Location(w, json.get("x").getAsDouble(), json.get("y").getAsDouble(),
                json.get("z").getAsDouble(),
                json.get("yaw").getAsFloat(), json.get("pitch").getAsFloat());
    }
}


