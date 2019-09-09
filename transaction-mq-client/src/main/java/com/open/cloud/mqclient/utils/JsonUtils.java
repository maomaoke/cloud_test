package com.open.cloud.mqclient.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class JsonUtils {

    private static Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段都注入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消Date转timestamps
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略 在json字符串中存在,但是在java对象中不存在对应属性的情况. 防止错误
//        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //取消Date转timestamps
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    /***
     *  对象转json字符串
     * @param /<T>obj 要转换的对象
     * @return 返回json字符串
     */
    public static <T> String obj2Json(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("parse Object to String error", e);
            return null;
        }
    }

    /***
     * 对象转json字符串(格式化) 调试用
     * @param /<T>obj 要转换的对象
     * @return 返回json字符串
     */
    public static <T> String obj2PrettyJson(T obj) {
        if (obj == null) {
            throw new IllegalArgumentException();
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            throw new RuntimeException("parse Object to String error");
        }
    }

    public static <T> T json2Obj(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json) || clazz == null) {
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)json : objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("parse String to Object error");
        }
    }

    /***
     * json转复杂对象
     * @param json
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2Obj(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json) || typeReference == null) {
            return null;
        }
        try {
            return typeReference.getType().equals(String.class) ? (T)json : objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("parse String to Object error");
        }
    }

    public static <T> T json2Obj(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException("parse String to Object error");
        }
    }

    public static <T> T mapToObj(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static <T> Map objToMap(T object) {
        return objectMapper.convertValue(object, Map.class);
    }
}
