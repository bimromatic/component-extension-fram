package com.bimromatic.component.lib_net.analysis;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * author : bimromatic
 * e-mail : xxx@xx
 * time   : 2019-12-04
 * desc   : 自定义TypeAdapter ,null对象将被解析成空字符串
 * version: 1.0
 */
public class StringNullAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader reader) throws IOException {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull();
            return "";//原先是返回Null，这里改为返回空字符串
        }

        String jsonStr = reader.nextString();
        if(jsonStr.equals("null")) {
            return "";
        }else {
            return jsonStr;
        }
    }

    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        if (value == null) {
            writer.nullValue();
            return;
        }
        writer.value(value);
    }
}
