package com.example.loadpictureworker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.URL;

public class PictureLoader extends Worker {
    URL imageAddress;
    Bitmap image;
    String imageJson = "";
    Data outputData;
    public PictureLoader(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);

    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            imageAddress = new URL(getInputData().getString("Address"));
            Log.d("Address", imageAddress.toString());
            image = BitmapFactory.decodeStream(imageAddress.openStream());
            Log.d("Address", image.toString());
            imageJson = ImageHelper.imageToJson(image);
            outputData = new Data.Builder()
                    .putString("imageJson", imageJson)
                    .build();
            //Thread.sleep(500);
        } catch ( IOException e) {
            e.printStackTrace();
        }

        return Result.success(outputData);
    }
}
