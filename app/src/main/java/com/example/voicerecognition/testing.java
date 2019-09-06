package com.example.voicerecognition;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class testing extends AppCompatActivity {

    private TextView txtSay, txtDo;
    private ImageView resultsImg;

    private MediaRecorder mediaRecorder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        txtSay = (TextView) findViewById(R.id.txtSay);
        txtDo = (TextView) findViewById(R.id.txtDo);
        mediaRecorder = new MediaRecorder();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PackageManager.PERMISSION_GRANTED);

    }

    public void getSpeechInput(View view){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,10);
//            startRecording();
        } else{
            Toast.makeText(this, "This Device Does Not Support Speech Input!", Toast.LENGTH_SHORT).show();
        }

        

    }
    public void startRecording(){
        try {
            mediaRecorder.reset();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
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

    public void print(String word){
        System.out.println(word);
    }

    public void stopRecording(){
        mediaRecorder.stop();
        mediaRecorder.release();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 10:
                if (resultCode == RESULT_OK && data != null){

                    print("LOL");

                    Bundle bundle = data.getExtras();
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtDo.setText(result.get(0));

                    resultsImg = (ImageView) findViewById(R.id.resultsImg);
                    String compare1 = result.get(0).toLowerCase();
                    String compare2 = txtSay.getText().toString().toLowerCase().replaceAll("[^a-zA-Z0-9\\s+]", "");
                    if (compare1.equals(compare2)){
                        resultsImg.setVisibility(View.VISIBLE);
                        resultsImg.setImageResource(R.drawable.correct);
                    }
                    else{
                        resultsImg.setVisibility(View.VISIBLE);
                        resultsImg.setImageResource(R.drawable.cross);
                    }

//                    mediaRecorder.stop();
//                    mediaRecorder.release();

                }
                break;
        }
    }
}
