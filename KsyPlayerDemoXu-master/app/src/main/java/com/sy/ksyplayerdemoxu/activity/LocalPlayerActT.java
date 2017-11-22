package com.sy.ksyplayerdemoxu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.sy.ksyplayerdemoxu.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 创建时间： 2017/9/5 0005.
 * 编写人：xutailian
 * 功能描述：
 */
public class LocalPlayerActT extends Activity implements View.OnClickListener {
    private static final String TAG = "LocalPlayerActT";
    private KSYTextureView mVideoView;
    private int mVideoWidth;
    private int mVideoHeight;
    private Context mContext;
    private RelativeLayout root_player;


    //    //播放路径的设置1
//    String localString2 = "/storage/emulated/0/a.mp4";
//    //播放路径的设置2
//    String localString = "/storage/emulated/0/meishipian.mkv";
//
//
//    //播放路径的设置3
//    String localStirng3 ="/storage/emulated/0/meishipian.mkv";
    //    //播放路径的设置1
    String localString2 = "http://playback.ks.zb.mi.com/record/live/107578_1467605748/hls/107578_1467605748.m3u8";
    //播放路径的设置2
    String localString = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";


    //播放路径的设置3
    String localStirng3 ="rtmp://live.hkstv.hk.lxdns.com/live/hks";

    //使用reload或者reset


    private  String[] localUrl ={localString,localString2,localStirng3};
    private List<String> local ;
    private List<String> localT ;
    int end;

    private Button button_add;
    private Button button_exit;

    ////////////////////////////////////////视频的控件大小控制
    private int mPlayWidth = 200;
    private int mPlayHeight = 200;
    private  int mLeft = 200;
    private int mTop = 200;

    private int numCount = 80;
    private int clickCount=0;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " );
        setContentView(R.layout.root_local);
        mContext = this;
//        initView();
        initData();
        button_add = findViewById(R.id.button_add);
        button_add.setOnClickListener(this);
        button_exit = findViewById(R.id.button_exit);
        button_exit.setOnClickListener(this);
    }

    private void initData() {
        local = new ArrayList<String>();
        local.add(localString);
        localT = new ArrayList<String>();
        localT.add(localString);
//        localT.add(localString);
//        localT.add(localString2);
        end =localT.size();
    }

    private void initView() {
        root_player = (RelativeLayout) findViewById(R.id.root_player);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                mPlayWidth, mPlayHeight);
//        lp.setMargins(200,200,200,200);
        lp.leftMargin=mLeft;
        lp.topMargin=mTop;

        mVideoView=new KSYTextureView(this);
        mVideoView.setLayoutParams(lp);
        root_player.addView(mVideoView);
        //设置监听器
        mVideoView.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);//播放完成的回调
        mVideoView.setOnPreparedListener(mOnPreparedListener);//可以开播时回调
        mVideoView.setOnInfoListener(mOnInfoListener);
        mVideoView.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mVideoView.setOnErrorListener(mOnErrorListener);
        mVideoView.setOnSeekCompleteListener(mOnSeekCompletedListener);

//        mVideoView.setLooping(true);//测试重播

//        // 重新链接
//        mVideoView.reload(localString, false);

        //设置播放参数
        mVideoView.setBufferTimeMax(2.0f);
        mVideoView.setTimeout(5, 30);

        try {
            mVideoView.setDataSource(localStirng3);
            mVideoView.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

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


    //    播放完成的回调方法
    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCompletion: ----------播放完成方法");

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

//        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mVideoView = null;
//        NetStateUtil.unregisterNetState(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mVideoView != null) {
            mVideoView.runInBackground(true);
        }
//        NetStateUtil.registerNetState(this, netChangeListener);
    }

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_add://添加布局

                if (clickCount==0){
                    initView();

                }else {
                    mPlayWidth =mPlayWidth+numCount;
                    mPlayHeight =mPlayHeight+numCount;
                    mLeft = mLeft +numCount;
                    mTop = mTop + numCount;
                    initView();
                }
                break;
            case R.id.button_exit://删除布局
                break;
        }
    }
}
