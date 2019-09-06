package com.example.voicerecognition;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txtSay, txtDo;
    private ImageView resultsImg;

    private MediaRecorder mediaRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

        txtSay = (TextView) findViewById(R.id.txtSays);
        mediaRecorder = new MediaRecorder();
    }

    public void startRecording(View view){
        try {
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(path, "/Audioclip.mp3");

            System.out.println(path);

            mediaRecorder.setOutputFile(file);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);

            System.out.println("LOL");

            mediaRecorder.prepare();

            System.out.println("LOL");

            mediaRecorder.start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    int cResult = 0; //At the mic btn
    public void clicked(View view){
        ImageView recsImg;
        recsImg = (ImageView) findViewById(R.id.recs);
        if (cResult == 0){
            startRecording(view);
            recsImg.setBackgroundResource(R.drawable.stop);
            cResult = 1;
        }
        else{
            stopRecording(view);
            recsImg.setBackgroundResource(R.drawable.record);
            Toast.makeText(this, "Recording Successful!", Toast.LENGTH_SHORT).show();
            cResult = 0;
        }
    }

    public void stopRecording(View view){
        mediaRecorder.stop();
        mediaRecorder.release();
    }

}
