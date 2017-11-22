package com.sy.ksyplayerdemoxu.haha;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.ksyun.media.player.IMediaPlayer;
import com.sy.ksyplayerdemoxu.R;
import com.sy.ksyplayerdemoxu.bean.ViewBean;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, VVideoView.VVideoViewOnListener {
    private static final String TAG = "Main2Activity";
    private Button button_add1;
    private Button button_exit1;
    private Button button_add2;
    private Button button_exit2;
    private Button button_add3;
    private Button button_exit3;
    private RelativeLayout root_player;

    private String data  = "{\"width\":500,\"height\":500,\"margeleft\":200,\"margetop\":100,\"videodata\":[{\"timetype\":50,\"videourl\":\"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4\",\"starttime\":\"12\",\"videoflag\":0},{\"timetype\":50,\"videourl\":\"rtmp://live.hkstv.hk.lxdns.com/live/hks\",\"starttime\":\"12\",\"videoflag\":0}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();


    }

    private void initView() {
        button_add1 = (Button) findViewById(R.id.button_add1);
        button_exit1 = (Button) findViewById(R.id.button_exit1);
        button_add2 = (Button) findViewById(R.id.button_add2);
        button_exit2 = (Button) findViewById(R.id.button_exit2);
        button_add3 = (Button) findViewById(R.id.button_add3);
        button_exit3 = (Button) findViewById(R.id.button_exit3);
        root_player = (RelativeLayout) findViewById(R.id.root_player);

        button_add1.setOnClickListener(this);
        button_exit1.setOnClickListener(this);
        button_add2.setOnClickListener(this);
        button_exit2.setOnClickListener(this);
        button_add3.setOnClickListener(this);
        button_exit3.setOnClickListener(this);
    }

    String url1 = "http://playback.ks.zb.mi.com/record/live/107578_1467605748/hls/107578_1467605748.m3u8";
    //播放路径的设置2
    String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";


    //播放路径的设置3
    String url3 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    List<ViewBean> listbean = new ArrayList<ViewBean>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add1:
//                VVideoView vVideoView = new VVideoView(this);
//                vVideoView.setParams(root_player,1, 200, 200, 200, 200, 0, url3, this);
//                ViewBean bean = new ViewBean();
//                bean.setId(1);
//                bean.setMview(vVideoView);
//                listbean.add(bean);


                break;
            case R.id.button_exit1:
                listbean.get(0).getMview().vvRemove();

                break;
            case R.id.button_add2:
                VVideoView vVideoView2 = new VVideoView(this);
                vVideoView2.setParams(root_player,2, 200, 200, 500, 500, 1, url2, this);


                break;
            case R.id.button_exit2:
                break;
            case R.id.button_add3:
                VVideoView vVideoView3 = new VVideoView(this);
                vVideoView3.setParams(root_player,3, 200, 200, 800, 800, 0, url3, this);


                break;
            case R.id.button_exit3:



             


                break;
        }
    }

    @Override
    public void onPreparedListener(IMediaPlayer iMediaPlayer, int id) {
        Log.d(TAG, "onPreparedListener: 视频编号： "+id);

    }

    @Override
    public void onCompletionListener(IMediaPlayer iMediaPlayer, int id) {
        Log.d(TAG, "onCompletionListener: 视频编号： "+id);
    }

    @Override
    public void onErrorListener(IMediaPlayer iMediaPlayer, int i, int i1, int id) {
        Log.e(TAG, "onErrorListener: 视频编号： "+id);
    }
}
