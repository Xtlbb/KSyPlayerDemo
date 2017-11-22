package com.sy.ksyplayerdemoxu.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.sy.ksyplayerdemoxu.R;
import com.sy.ksyplayerdemoxu.service.TextToSpeechService;
import com.sy.ksyplayerdemoxu.util.NetState;
import com.sy.ksyplayerdemoxu.util.NetStateUtil;
import com.sy.ksyplayerdemoxu.util.Video;
import java.io.File;
import java.io.IOException;

/**
 * 创建时间： 2017/9/4 0004.
 * 编写人：xutailian
 * 功能描述：
 */

public class MedPlayer extends Activity{
    private static  final String TAG= MedPlayer.class.getSimpleName();
    KSYTextureView mVideoView =null;
    private Context mContext;
    private boolean useHwCodec = false;
    private String   mDataSource;
    private int mVideoWidth = 0;
    private int mVideoHeight = 0;
    private int prepareTimeout;
    private int readTimeout;
    private int bufferTime;
    private int bufferSize;


    final String[] sampleUrl = { "rtmp://live.hkstv.hk.lxdns.com/live/hks",
            "http://playback.ks.zb.mi.com/record/live/107578_1467605748/hls/107578_1467605748.m3u8"
            ,"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4"};

    private String localUrl ;
    private String localUrla;

    /////////////////////////////////////////添加语音文件

    Boolean mBound = false;
    TextToSpeechService mservice;
    private Button bt_startservice;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medplayer_layout);
        Log.e(TAG,"oncreat-----");
        mContext  = this.getApplicationContext();
        useHwCodec = getIntent().getBooleanExtra("HWCodec", false);
        mVideoView = findViewById(R.id.texture_view);
        mVideoView.setKeepScreenOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

//        localUrl = Environment.().getAbsolutePath()+"meishipian.mkv";
//        localUrl="/mnt/sdcard/meishipian.mkv" ;
        localUrl="/storage/emulated/0/meishipian.mkv" ;
//        localUrl=  Environment.getExternalStorageDirectory().getAbsolutePath()+"meishipian.mkv";
        File file = new File(localUrl);
        isFileExists(file);




        mDataSource = sampleUrl[0];
        initPlayer();//集成视频
        initPlayerVoice();//集成语音

    }

    //播放语音
    private void initPlayerVoice() {
        //绑定service;
        Intent serviceIntent = new Intent(this , TextToSpeechService.class);
        //如果未绑定，则进行绑定
        if(!mBound){
            bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
        }

        bt_startservice = findViewById(R.id.bt_startservice);
        bt_startservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound){
                    String text ="请一零零一等候准备";
                    mservice.startPlayerVoice(text,mVideoView);
                }
            }
        });

    }
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            TextToSpeechService.MyBinder myBinder = (TextToSpeechService.MyBinder) iBinder;

            //获取service
            mservice = (TextToSpeechService) myBinder.getService();
            //绑定成功
            mBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //绑定成功
            mBound = false;
        }
    };


    //判断文件是否存在
    private  void isFileExists(File file){
        if (file.exists()){
            Log.e(TAG,"视频文件存在===");
        }else {
            Log.e(TAG,"找不到文件===");
        }
    }

    private void initPlayer() {

        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompletedListener);
        mVideoView.setOnMessageListener(mOnMessageListener);
        mVideoView.setScreenOnWhilePlaying(true);

        bufferTime = 2 ;
        bufferSize =  15 ;

        prepareTimeout = 5 ;
        readTimeout = 30;
        if (bufferTime > 0) {
            mVideoView.setBufferTimeMax(2.0f);
            Log.e(TAG, "palyer buffertime :" + bufferTime);
        }

        if (bufferSize > 0) {
            mVideoView.setBufferSize(bufferSize);
            Log.e(TAG, "palyer buffersize :" + bufferSize);
        }

        if (prepareTimeout > 0 && readTimeout > 0){
            mVideoView.setTimeout(prepareTimeout, readTimeout);
            Log.e(TAG, "prepareTimeout :" + prepareTimeout);
            Log.e(TAG, "readTimeout: " + readTimeout);
        }


        try {
            Log.e(TAG,"播放路径"+localUrl);
            mVideoView.setDataSource(mDataSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mVideoView.prepareAsync();
    }



    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer mp) {
            Log.d("VideoPlayer", "OnPrepared");
            mVideoWidth = mVideoView.getVideoWidth();
            mVideoHeight = mVideoView.getVideoHeight();
            // Set Video Scaling Mode
            mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            //start player
            mVideoView.start();
            //  get meta data


        }
    };
    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
            long duration = mVideoView.getDuration();
            long progress = duration * percent / 100;
        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    if (mVideoView != null)
                        mVideoView.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        }
    };

    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Log.e(TAG, "onSeekComplete...............");
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            videoPlayEnd();
        }
    };

    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer mp, int what, int extra) {
            switch (what) {
                //case KSYVideoView.MEDIA_ERROR_UNKNOWN:
                // Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
                //  break;
                default:
                    Log.e(TAG, "OnErrorListener, Error:" + what + ",extra:" + extra);
            }

            videoPlayEnd();

            return false;
        }
    };

    public IMediaPlayer.OnInfoListener mOnInfoListener = new IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
                    Log.d(TAG, "Buffering Start.");
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
                    Log.d(TAG, "Buffering End.");
                    break;
                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
                    Toast.makeText(mContext, "Audio Rendering Start", Toast.LENGTH_SHORT).show();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    Toast.makeText(mContext, "Video Rendering Start", Toast.LENGTH_SHORT).show();
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                    Toast.makeText(mContext, "Succeed to reload video.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Succeed to mPlayerReload video.");
                    return false;
            }
            return false;
        }
    };

    private IMediaPlayer.OnMessageListener mOnMessageListener = new IMediaPlayer.OnMessageListener() {
        @Override
        public void onMessage(IMediaPlayer iMediaPlayer, String name, String info, double number) {
            Log.e(TAG, "name:" + name + ",info:" + info + ",number:" + number);
        }
    };

    private void videoPlayEnd() {
        if (mVideoView != null) {
            mVideoView.release();
            mVideoView = null;
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoView != null) {
            mVideoView.runInBackground(true);
        }
        NetStateUtil.registerNetState(this, netChangeListener);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView = null;
        NetStateUtil.unregisterNetState(this);
        unbindService(mConnection);
    }

    private NetStateUtil.NetChangeListener netChangeListener = new NetStateUtil.NetChangeListener() {
        @Override
        public void onNetStateChange(int netWorkState) {
            switch (netWorkState) {
                case NetState.NETWORK_MOBILE:
                    break;
                case NetState.NETWORK_WIFI:
                    break;
                case NetState.NETWORK_NONE:
                    Toast.makeText(MedPlayer.this, "没有监测到网络,请检查网络连接", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.runInForeground();
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            videoPlayEnd();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
