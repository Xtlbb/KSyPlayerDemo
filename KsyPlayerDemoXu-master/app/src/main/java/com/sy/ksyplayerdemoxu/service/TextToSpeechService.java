package com.sy.ksyplayerdemoxu.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.ksyun.media.player.KSYTextureView;
import com.sinovoice.hcicloudsdk.player.TTSCommonPlayer;
import com.sinovoice.hcicloudsdk.player.TTSPlayerListener;
import com.sy.ksyplayerdemoxu.bean.SpeechBean;
import com.sy.ksyplayerdemoxu.ttsutil.AudioFocusHelper;
import com.sy.ksyplayerdemoxu.ttsutil.TtsPlayUtil;
import com.sy.ksyplayerdemoxu.ttsutil.TtsUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/9/1 0001.
 * 编写人：xutailian
 * 功能描述： 语音播报的service
 */

public class TextToSpeechService extends Service implements TTSPlayerListener {
    public static  final String TAG ="TextToSpeechService";
    private MyBinder myBinder = new MyBinder();
    private TtsPlayUtil mTtsPlayUtil ;
    private TtsUtil mInitTts;
    private boolean isInitPlayer;
    private List<SpeechBean> speechBeanList;
    private SpeechBean speechBean;


    KSYTextureView mVideoView =null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        speechBeanList = new ArrayList<SpeechBean>();

        initTts();
    }
    private void initTts() {
        // 灵云语音工具类
        mInitTts = new TtsUtil(getApplicationContext());
        // 语音合成能力工具类
        mTtsPlayUtil = new TtsPlayUtil(this);
        // 初始化语音合成
        AudioFocusHelper.getInstance(getApplicationContext()).startFocus();
        isInitPlayer = mTtsPlayUtil.initPlayer(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTtsPlayUtil != null) {
            mTtsPlayUtil.release();
        }
        if (null != mInitTts) {
            mInitTts.hciRelease();
        }
        AudioFocusHelper.getInstance(this).release();
    }


    @Override
    public void onPlayerEventStateChange(TTSCommonPlayer.PlayerEvent playerEvent) {
        Log.e(TAG,"播放完成");
        if (playerEvent.toString().equals("PLAYER_EVENT_END")) {
            speechBeanList.remove(0);
            if (speechBeanList.size() != 0) {
                synth(speechBeanList.get(0).getText());
            }else {
                mVideoView.setVolume((float) 100 / 100, (float) 100 / 100);

            }

        }

    }

    @Override
    public void onPlayerEventProgressChange(TTSCommonPlayer.PlayerEvent playerEvent, int i, int i1) {

    }

    @Override
    public void onPlayerEventPlayerError(TTSCommonPlayer.PlayerEvent playerEvent, int i) {

    }

    public class  MyBinder extends Binder{
        public Service getService(){
            return TextToSpeechService.this;
        }



    }



    public void startPlayerVoice(String text,KSYTextureView mvideoView){
        mVideoView = mvideoView;
        String texta =text;
        speechBean = new SpeechBean();
        speechBean.setText(texta);
        speechBeanList.add(speechBean);
        mVideoView.setVolume((float) 0 / 100, (float) 0 / 100);
        if (mTtsPlayUtil.mTtsPlayer.getPlayerState() != TTSCommonPlayer.PLAYER_STATE_PLAYING ) {
            synth(speechBeanList.get(0).getText());
        }
    }
    public void synth(String msg) {
        if (!isInitPlayer) {
            Toast.makeText(this, "语音播报初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(msg)) {
            Toast.makeText(this, "语音播报合成内容为空", Toast.LENGTH_SHORT).show();
            return;
        }

        mTtsPlayUtil.synth(msg);


    }
}
