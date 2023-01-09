package com.example.loadpictureworker;

import android.graphics.Bitmap;

import com.google.gson.Gson;

public class ImageHelper {
    public static String imageToJson(Bitmap btm){
        Gson gson = new Gson();
        return gson.toJson(btm);
    }
    public static Bitmap jsonToImage(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Bitmap.class);
    }
}
