package com.sen5.lib.util;


import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by zhurongkun on 2017/8/18.
 */

public class JsonUtil {

    public static <T> T fromJson(String json, Class<T> tClass) {
        Gson gson = getGson();
        return gson.fromJson(json,tClass);
//        return JSON.parseObject(json, tClass);
    }

    /**
     * @param t   Map,List,Object...
     * @param <T>
     * @return
     */
    public static <T> String toJson(T t) {
        Gson gson = getGson();
        return gson.toJson(t);
//        return JSON.toJSONString(t);
    }


    public static int getElementInt(String json, String eleType) {
        Gson gson = getGson();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        return jsonObject.get(eleType).getAsInt();
//        JSONObject jsonObject = JSONObject.parseObject(json);
//        return jsonObject.getIntValue(eleType);
    }

    private JsonUtil() {
    }

    private static Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeHierarchyAdapter(byte[].class,new Base64Adapter());
        return gsonBuilder.create();
    }

    private static class Base64Adapter implements JsonSerializer<byte[]>,JsonDeserializer<byte[]>{

        @Override
        public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            byte[] bytes = Base64.decode(json.getAsString(), Base64.DEFAULT);
            return bytes;
        }

        @Override
        public JsonElement serialize(byte[] src, Type typeOfSrc, JsonSerializationContext context) {
            String string = Base64.encodeToString(src, Base64.DEFAULT);
            return new JsonPrimitive(string);
        }
    }
}
