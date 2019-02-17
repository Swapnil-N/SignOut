package com.example.signout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class callWatson extends AsyncTask<Void, Void, Void> {
    ClassifiedImages result;
    InputStream imageStream;
    Context context;

    callWatson(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Bitmap bm = BitmapFactory.decodeResource( context.getResources(), R.drawable.galaxy);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 50, baos);

            imageStream = new ByteArrayInputStream(baos.toByteArray());




            //Uri uri = Uri.parse("android.resource://com.example.testing_watson_visualrecognition/drawable/watson_test_1.jpg");
            //imageStream = context.getContentResolver().openInputStream(uri);
            //imageStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //5Pk0YXMz39A7V_o_TutdS5AGG7jyX3RpQkWYC3gypwpd
        IamOptions options = new IamOptions.Builder()
                .apiKey("5Pk0YXMz39A7V_o_TutdS5AGG7jyX3RpQkWYC3gypwpd")
                .url("https://gateway.watsonplatform.net/visual-recognition/api")

                //.accessToken("p-e1311e5812fb88afdff6f2e68ffd68d90111d859")
                .build();

        VisualRecognition visualRecognition = new VisualRecognition("2018-03-19", options);

        ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                .imagesFile(imageStream)
                .imagesFilename("test2.jpg")
                .threshold(.3f)
//                .owners(Arrays.asList("me"))
                .classifierIds(Arrays.asList("NoPhotos_824194539"))
                .build();
        result = visualRecognition.classify(classifyOptions).execute();
        System.out.println(result);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Gson gson = new Gson();
        String json = gson.toJson(result);
        String name = null;
        JSONArray classes = null;
        int maxIndex = 0;
        try {
            JSONObject jsonObject = new JSONObject(json);
            classes = jsonObject.getJSONArray("images").getJSONObject(0).getJSONArray("classifiers").getJSONObject(0).getJSONArray("classes");
//            for(JSONObject js : classes)
            maxIndex = 0;
            for(int i = 0; i < classes.length(); i++){
                if(classes.getJSONObject(i).getInt("score")>classes.getJSONObject(maxIndex).getInt("score") && maxIndex!=i)
                    maxIndex = i;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        System.out.println("--------------");

        try {
            System.out.println("Most Likely: " + classes.getJSONObject(maxIndex).getString("class"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
