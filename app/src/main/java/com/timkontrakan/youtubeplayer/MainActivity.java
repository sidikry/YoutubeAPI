package com.timkontrakan.youtubeplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends YouTubeBaseActivity {

    private static final String TAG = "MainActivity";
    YouTubePlayerView playerView;
    Button btnPlay;
    YouTubePlayer.OnInitializedListener onInitializedListener;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "OnCreate: Starting");
        btnPlay = findViewById(R.id.btnPlay);
        playerView = findViewById(R.id.youtubePlay);
        onInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {
                Log.d(TAG, "onClick: Done Initializing");
                final List<String> stringList = new ArrayList<>();
                reference = FirebaseDatabase.getInstance().getReference().child("Movie").child("fiftyshades");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        youTubePlayer.loadVideo(String.valueOf(dataSnapshot.child("url")));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                stringList.add("W4hTJybfU7s");
//                stringList.add("lYWYWyX04JI");
//                youTubePlayer.loadVideos(stringList);
                btnPlay.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d(TAG, "onClick: Failed to Initializing");
            }
        };

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Initializing Youtube Player.");
                playerView.initialize(YoutubeConfig.getApiKey(), onInitializedListener);

            }
        });
    }
}
