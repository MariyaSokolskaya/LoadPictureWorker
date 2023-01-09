package com.example.loadpictureworker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.graphics.Bitmap;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {
    Button loadButton;
    TableRow imageRow;
    String [] imageAddresses = {"https://www.fonstola.ru/pic/202003/1920x1080/fonstola.ru_376107.jpg",
        "https://i.ytimg.com/vi/4PUhM01PZf8/maxresdefault.jpg",
        "https://i.etsystatic.com/24419106/r/il/e571b0/2440722344/il_1588xN.2440722344_gi2c.jpg"};
    Bitmap image;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadButton = findViewById(R.id.load_btn);
        imageRow = findViewById(R.id.image_table);

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                data = new Data.Builder()
                        .putString("Address", imageAddresses[0])
                        .build();
                OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(
                        PictureLoader.class)
                        .setInputData(data)
                        .build();
                WorkManager workManager = WorkManager.getInstance();
                workManager.enqueue(workRequest);
                workManager.getWorkInfoByIdLiveData(workRequest.getId())
                        .observe(MainActivity.this, new Observer<WorkInfo>() {
                            @Override
                            public void onChanged(WorkInfo workInfo) {
                                String jsonImage = workInfo.getOutputData()
                                        .getString("imageJson");
                                image = ImageHelper.jsonToImage(jsonImage);
                                ((ImageView)imageRow.getChildAt(0)).setImageBitmap(image);
                            }
                        });
            }
        });
    }
}