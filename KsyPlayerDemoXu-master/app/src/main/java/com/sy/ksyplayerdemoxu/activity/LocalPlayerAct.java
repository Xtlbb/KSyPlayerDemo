package com.sy.ksyplayerdemoxu.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
public class LocalPlayerAct extends Activity{
    private KSYTextureView mVideoView;
    private int mVideoWidth;
    private int mVideoHeight;
    private String TAG = LocalPlayerAct.class.getSimpleName();
    private Context mContext;
    private View mView;
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
    String localStirng3 ="http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";

    //使用reload或者reset


    private  String[] localUrl ={localString,localString2,localStirng3};
    private List<String> local ;
    private List<String> localT ;
    int end;
    private boolean isHaveMoreUrl= false;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root_local);
        mContext = this;
        initView();
        initData();
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
        mView = LayoutInflater.from(this).inflate(R.layout.player_local,null);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                500, 500);
//        lp.setMargins(200,200,200,200);
        lp.leftMargin=230;
        lp.topMargin=230;
        mView.setLayoutParams(lp);//设置布局参数
        root_player.addView(mView);


        mVideoView = (KSYTextureView) mView.findViewById(R.id.texture_view_palyer);
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
        //......
        //(其它参数设置)
        //......
        //设置播放地址并准备
//        try {
//            File  f= new File(localString);
//            if(f.exists()) {
//                mVideoView.setDataSource(localString);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            mVideoView.setDataSource(localString);
            mVideoView.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if (end==1){
//            isHaveMoreUrl =false;
//            mVideoView.setLooping(true);
//        }else {
//            isHaveMoreUrl = true;
//        }

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

         int one =0;

//    播放完成的回调方法
    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            Toast.makeText(mContext, "OnCompletionListener, play complete.", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCompletion: ----------播放完成方法");
//            if (localT.size()>0){
//                if (isHaveMoreUrl){
//                    mVideoView.reload(localT.get(0),true, KSYMediaPlayer.KSYReloadMode.KSY_RELOAD_MODE_FAST);
//                    localT.add(localT.get(0));
//                    localT.remove(0);
//                    Log.d(TAG, "onCompletion: ----进入if判断");
//                }
//
//              }

//            for (int i=0;i<localT.size();i++){
//                end--;
//
//                localT.add(localT.get(i));
//                localT.remove(0);
//
//                one++;
//                Log.d(TAG, "onCompletion: -----播放完成调用reload（）----");
//             }
//             if (end==0){
//                 mVideoView.setLooping(true);
//                 Log.d(TAG, "onCompletion: -----播放完成调用 setLooping");
//             }

//            videoPlayEnd();
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




}
