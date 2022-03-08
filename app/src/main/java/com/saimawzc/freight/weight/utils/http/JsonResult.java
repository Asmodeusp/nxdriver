package com.saimawzc.freight.weight.utils.http;

import com.google.gson.reflect.TypeToken;

public class JsonResult<T> {

    public boolean success;
    public String message;
    int errCode;
    public T data;
    public JsonResult() {

    }

    public JsonResult(boolean success, String message, T data,int errCode) {
        this.success = success;
        this.message = message;
        this.errCode=errCode;
        this.data = data;
    }

    /**
     * 由字符串构造
     *
     * @param value
     * @return
     */
    public static JsonResult fromString(String value) {
        if (value == null) {
            return null;
        }
        JsonResult result = JsonUtil.fromJson(value, JsonResult.class);
        return result;
    }

    public static JsonResult fail() {
        JsonResult result = new JsonResult(false, "网络请求失败，请检查网络连接", null,500);
        return result;
    }
    public <T> T get(Class<T> clazz) {
        return JsonUtil.fromObject(data, clazz);
    }

    public <T> T get(TypeToken<T> token) {
        return JsonUtil.fromObject(data, token);
    }

    public <T> T getDecrypt(Class<T> clazz) {
        if (data == null) {
            return null;
        }
        return get(clazz);
    }
    public <T> T getDecrypt(TypeToken<T> token) {
        if (data == null) {
            return null;
        }
        return get(token);
    }
    public boolean isOk() {
        if (success==true ){
            return true;
        } else {
            return false;
        }
    }
    public String getData() {
        return String.valueOf(data);
    }
    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }
}
