package com.example.signout;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.service.WatsonService;
import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new CameraFragment());

        IamOptions options = new IamOptions.Builder()
                .apiKey("9FLgdPdP7umW-3_m_EAjDxXRalbLsCeD21Ovw  eEBEqlH")
                .build();
        TextToSpeech textToSpeech = new TextToSpeech(options);
        textToSpeech.setEndPoint("https://stream.watsonplatform.net/text-to-speech/api");


    }



    private boolean loadFragment(Fragment fragment){
        if (fragment != null){
            getFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment frag = null;

        switch (item.getItemId()){

            case R.id.navigation_camera:
                frag = new CameraFragment();
                break;

            case R.id.navigation_text:
                frag = new TextFragment();
                break;
        }
        return loadFragment(frag);

    }

}
