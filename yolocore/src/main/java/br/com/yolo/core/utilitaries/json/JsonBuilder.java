package br.com.yolo.core.utilitaries.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Getter;

import java.util.function.Consumer;

public final class JsonBuilder {

	@Getter private final JsonObject json;
	
	public JsonBuilder() {
		this.json = new JsonObject();
	}
	
	public JsonBuilder accept(Consumer<JsonObject> consumer) {
		consumer.accept(this.json);
		return this;
	}
	
	public JsonBuilder addElement(String key, JsonElement value) {
		return accept(json -> json.add(key, value));
	}
	
	public JsonBuilder addProperty(String key, String value) {
		return accept(json -> json.addProperty(key, value));
	}
	
	public JsonBuilder addProperty(String key, Number value) {
		return accept(json -> json.addProperty(key, value));
	}
	
	public JsonBuilder addProperty(String key, Boolean value) {
		return accept(json -> json.addProperty(key, value));
	}
	
	public JsonBuilder addProperty(String key, Character value) {
		return accept(json -> json.addProperty(key, value));
	}
	
	public JsonObject build() {
		return this.json;
	}
}
