package com.wutanda.sdk.money.mobilemoney.mtn.utils;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.stream.Collectors;

public final class Json {

    public static <T> String toJson(T data){
        final Gson gson = new Gson();
        return gson.toJson(data);
    }

    public static <T> T fromJson(final String jsonData,Class<T> tClass){
        final Gson gson = new Gson();
        return gson.fromJson(jsonData,tClass);
    }

    public static <T> T fromJson(final InputStream inputStream, Type type){
        final Gson gson = new Gson();
        final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        final String jsonString = new BufferedReader(inputStreamReader).lines().collect(Collectors.joining("\n"));
        return gson.fromJson(jsonString,type);
    }


}