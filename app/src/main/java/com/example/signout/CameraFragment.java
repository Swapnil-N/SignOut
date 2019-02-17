package com.example.signout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    VideoView videoView;
    Button record;
    Boolean play;
    Bundle bundle=new Bundle();
    Uri videoUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_camera, null);

        videoView=view.findViewById(R.id.ID_videoView);
        record=view.findViewById(R.id.ID_Record);
        play=true;

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakeVideoIntent();
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(play)
                    videoView.start();
                else
                    videoView.pause();
                play=!play;
            }
        });

        return view;
    }

    static final int REQUEST_VIDEO_CAPTURE = 1;

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            videoUri = intent.getData();
            //bundle.putString("Vid",videoUri.toString());
            videoView.setVideoURI(videoUri);

            /*TextFragment newTextFragment = new TextFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fra, newGamefragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*/
        }
    }

    /*@Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        bundle.putString("Vid",videoUri.toString());
        outState.putAll(bundle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            if(savedInstanceState!=null) {
                videoUri= Uri.parse(savedInstanceState.getString("Vid"));
                videoView.setVideoURI(videoUri);
            }
     }*/

}
