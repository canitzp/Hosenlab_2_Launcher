package de.canitzp.hosenlab2launcher.MINECRAFTFORGE;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Iterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import java.lang.reflect.Type;
import java.util.Iterator;

public class OldPropertyMapSerializer implements JsonSerializer<PropertyMap> {
    public OldPropertyMapSerializer() {
    }

    public JsonElement serialize(PropertyMap var1, Type var2, JsonSerializationContext var3) {
        JsonObject var4 = new JsonObject();
        Iterator var5 = var1.keySet().iterator();

        while(var5.hasNext()) {
            String var6 = (String)var5.next();
            JsonArray var7 = new JsonArray();
            Iterator var8 = var1.get(var6).iterator();

            while(var8.hasNext()) {
                Property var9 = (Property)var8.next();
                var7.add(new JsonPrimitive(var9.getValue()));
            }

            var4.add(var6, var7);
        }

        return var4;
    }
}
