package com.dataenlighten.aimjsdk.demo.utils;

import com.google.gson.Gson;
import com.google.gson.internal.Primitives;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/12/18 0018.
 */

public class GsonUtils {

    public static  <T> T parseJsonToBean(String json, Class<T> classOfT){
        Object object = new Gson().fromJson(json, (Type)classOfT);
        return Primitives.wrap(classOfT).cast(object);
    }
}
