package com.sy.ksyplayerdemoxu.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYTextureView;
import com.sy.ksyplayerdemoxu.R;
import com.sy.ksyplayerdemoxu.haha.VVideoView;

import java.io.IOException;

/**
 * 创建时间： 2017/11/15 0015.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：
 */

public class CustomPlayView extends KSYTextureView implements IMediaPlayer.OnPreparedListener, IMediaPlayer.OnCompletionListener, IMediaPlayer.OnErrorListener, View.OnClickListener {
    private static final String TAG = "CustomPlayView";
    private int videoid, width, height, margeleft, margetop, timetype;
    private String voideurl;
    private Context context;
    private KSYTextureView mVideoView;
    private ViewGroup parent;

    public CustomPlayView(Context context) {
        super(context);
        this.context = context;
    }

    public void setParams(int videoid, int width, int height, int margeleft,
                          int margetop, int timetype, String voideurl,
                          VVideoView.VVideoViewOnListener listener) {
        this.videoid = videoid;
        this.width = width;
        this.height = height;
        this.margeleft = margeleft;
        this.margetop = margetop;
        this.timetype = timetype;
        this.voideurl = voideurl;
        this.listener = listener;
        initView(context);
        initListener();
    }

    public void setParams(ViewGroup root, int videoid, int width, int height, int margeleft,
                          int margetop, int timetype, String voideurl,
                          VVideoView.VVideoViewOnListener listener) {
        this.parent = root;
        this.videoid = videoid;
        this.width = width;
        this.height = height;
        this.margeleft = margeleft;
        this.margetop = margetop;
        this.timetype = timetype;
        this.voideurl = voideurl;
        this.listener = listener;
        initView(context);
        initListener();
    }


    private void initView(Context context) {
        mVideoView = this;
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                width, height);
        lp.leftMargin = margeleft;
        lp.topMargin = margetop;
        mVideoView.setLayoutParams(lp);
        this.parent.addView(mVideoView);
        mVideoView.setBufferTimeMax(2.0f);
        mVideoView.setTimeout(5, 30);
        if (timetype==0){//直播

        }else {
            // 只播放 10秒至 100秒这区间的音视频数据
            mVideoView.setPlayableRanges(100 * 1000l, 900 * 1000l);
            mVideoView.setLooping(true);

        }
        try {
            mVideoView.setDataSource(voideurl);
            mVideoView.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        mVideoView.setOnPreparedListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnErrorListener(this);
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {

        Log.d(TAG, "onPrepared: 视频准备就绪");

        if (listener != null) {
            listener.onPreparedListener(iMediaPlayer, videoid);
        }
        playAndPause(true);
    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {
        Log.d(TAG, "onCompletion:视频 播放完成");
        if (listener != null) {
            listener.onCompletionListener(iMediaPlayer, videoid);
        }
//        vvRelease();
    }

    private VVideoView.VVideoViewOnListener listener;

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        Log.e(TAG, "onError: 视频异常");
        if (listener != null) {
            listener.onErrorListener(iMediaPlayer, i, i1, videoid);
        }
        vvRelease();
        return false;
    }

    @Override
    public void onClick(View view) {
        if (parent != null) {
            Log.d(TAG, "onClick: 视频移除");
            parent.removeView(mVideoView);
            vvRelease();
        }
    }


    public interface VVideoViewOnListener {
        void onPreparedListener(IMediaPlayer iMediaPlayer, int id);

        void onCompletionListener(IMediaPlayer iMediaPlayer, int id);

        void onErrorListener(IMediaPlayer iMediaPlayer, int i, int i1, int id);
    }

    public void playAndPause(boolean isPlay) {
        if (isPlay && mVideoView != null) {
            mVideoView.start();
        } else if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    public void vvRelease() {
        if (mVideoView != null) {
            mVideoView.stop();
            mVideoView.release();
            mVideoView = null;
        }
    }

    public void vvRemove(KSYTextureView mVideoView ){
        if (mVideoView != null) {
            parent.removeView(mVideoView);
            mVideoView.stop();
            mVideoView.release();
            mVideoView = null;
        }
    }
}
