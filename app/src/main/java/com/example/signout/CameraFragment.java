package com.example.signout;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
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

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    VideoView videoView;
    Button record;
    Boolean play;
    Bundle bundle = new Bundle();
    Uri videoUri;

    ArrayList<Bitmap> imageList = new ArrayList<Bitmap>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_camera, null);

        videoView = view.findViewById(R.id.ID_videoView);
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
            getPictures(videoUri);
            //videoView.start();
        }
    }

    private void getPictures(Uri inURI) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(getContext(), inURI);
            long videoLengthInSec = Long.parseLong(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION))/1000;
            for (int i=1;i<videoLengthInSec+1;i++)
                imageList.add(retriever.getFrameAtTime(1000000*i));

            Log.d("asdf", imageList.size()+"");
            retriever.release();
        }catch (Exception e){
            Log.d("asdf","asdf");
        }
    }

}
