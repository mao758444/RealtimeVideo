package com.example.mao.real_timevideo;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.VideoView;

public class LiveActivity extends AppCompatActivity {
    private String mUrl;
    private String mTitle;
    private ImageView backButton;
    private TextView titleText;
    private TextView timeText;
    private VideoView videoView;
    private static final int RETRY_TIME=5;
    private int count=0;
    private RelativeLayout loadingLayout;
    private RelativeLayout rootView;
    private LinearLayout topView;
    private LinearLayout bottomView;
    private Handler handler=new Handler(Looper.getMainLooper());
    private ImageView playing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live);
        Intent intent=getIntent();
        mUrl=intent.getStringExtra("url");
        mTitle=intent.getStringExtra("title");
        initView();
        initPlayer();
    }

    private void initPlayer() {
        Vitamio.isInitialized(getApplicationContext());
        videoView=findViewById(R.id.my_view);
        videoView.setVideoURI(Uri.parse(mUrl));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                loadingLayout.setVisibility(View.GONE);
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                if(count>RETRY_TIME){
                    new AlertDialog.Builder(LiveActivity.this)
                            .setMessage("播放错误！")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    LiveActivity.this.finish();
                                }
                            }).setCancelable(false).show();
                }else {
                    videoView.stopPlayback();
                    videoView.setVideoURI(Uri.parse(mUrl));
                }
                count++;
                return false;
            }
        });
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                switch (i){
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        loadingLayout.setVisibility(View.VISIBLE);
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
                        loadingLayout.setVisibility(View.GONE);
                        break;
                }

                return false;
            }
        });
        playing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(videoView.isPlaying()) {
                    videoView.pause();
                    playing.setImageResource(R.drawable.paste);
                }
                else {
                    /*videoView.setVideoURI(Uri.parse(mUrl));
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            videoView.start();
                        }
                    });*/
                    videoView.start();
                    playing.setImageResource(R.drawable.playing);
                }
            }
        });
    }

    private void initView() {
        backButton=findViewById(R.id.tv_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titleText=findViewById(R.id.tv_title);
        titleText.setText(mTitle);
        timeText=findViewById(R.id.tv_time);
        timeText.setText(getCurrentTime());
        loadingLayout=findViewById(R.id.rl_loading_layout);
        rootView=findViewById(R.id.Root_view);
        topView=findViewById(R.id.top_layout);
        bottomView=findViewById(R.id.bottom_layout);
        playing=findViewById(R.id.playing);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topView.getVisibility()==View.VISIBLE||bottomView.getVisibility()==View.VISIBLE){
                    topView.setVisibility(View.GONE);
                    bottomView.setVisibility(View.GONE);
                    return;
                }
                if(videoView.isPlaying()){
                    playing.setImageResource(R.drawable.playing);
                }else {
                    playing.setImageResource(R.drawable.paste);
                }
                    topView.setVisibility(View.VISIBLE);
                    bottomView.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        topView.setVisibility(View.GONE);
                        backButton.setVisibility(View.GONE);
                    }
                },5000);

            }
        });
    }

    private String getCurrentTime(){
        Calendar c=Calendar.getInstance();
        SimpleDateFormat s=new SimpleDateFormat("yyyy-mm-dd HH:mm:ss" );
        String time=s.format(c.getTime());
        return time;
    }

    @Override
    protected void onStop() {
        super.onStop();
        count=0;
        if(videoView!=null){
            videoView.stopPlayback();
        }
    }
}
