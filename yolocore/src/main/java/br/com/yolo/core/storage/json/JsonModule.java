package br.com.yolo.core.storage.json;

import br.com.yolo.core.storage.Storage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import lombok.Getter;

@Getter
public class JsonModule extends Storage<String, Gson> {

    private final JsonParser parser = new JsonParser();

    public void registerGson(String name, Gson gson) {
        add(name, gson);
    }

    public void registerGson(String name, GsonBuilder builder) {
        registerGson(name, builder.create());
    }
}
