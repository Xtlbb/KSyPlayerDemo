package com.sy.ksyplayerdemoxu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ksyun.media.player.IMediaPlayer;
import com.sy.ksyplayerdemoxu.R;
import com.sy.ksyplayerdemoxu.bean.CustomViewBean;
import com.sy.ksyplayerdemoxu.bean.VideoBean;
import com.sy.ksyplayerdemoxu.bean.VideoBeanList;
import com.sy.ksyplayerdemoxu.haha.VVideoView;
import com.sy.ksyplayerdemoxu.util.UIUtil;
import com.sy.ksyplayerdemoxu.view.CustomPlayView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 创建时间： 2017/11/16 0016.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：
 */

public class CustomActivity extends Activity implements View.OnClickListener, VVideoView.VVideoViewOnListener {

    private String TAG = CustomActivity.class.getSimpleName();

    private  RelativeLayout aroot_player;

    private Button button_data;
    private Button tbutton_add1;
    private Button tbutton_exit1;
    private Button tbutton_add2;
    private Button tbutton_exit2;
    private Button tbutton_add3;
    private Button tbutton_exit3;
    private CustomPlayView customPlayView;

    //播放路径的设置2
    String url2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";


    //播放路径的设置3
    String url3 = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    private String data = "{\"id\":123,\"width\":500,\"height\":500,\"margeleft\":200,\"margetop\":100,\"videodata\":[{\"videoid\":1,\"timetype\":50,\"videourl\":\"http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4\",\"starttime\":\"2017-11-20 18:26:00\",\"endtime\":\"2017-11-20 18:30:00\",\"videoflag\":1},{\"videoid\":2,\"timetype\":50,\"videourl\":\"rtmp://live.hkstv.hk.lxdns.com/live/hks\",\"starttime\":\"2017-11-20 17:40:00\",\"endtime\":\"2017-11-20 17:50:00\",\"videoflag\":0},{\"videoid\":3,\"timetype\":50,\"videourl\":\"rtmp://live.hkstv.hk.lxdns.com/live/hks\",\"starttime\":\"2017-11-20 17:20:00\",\"endtime\":\"2017-11-20 17:30:00\",\"videoflag\":0}]}";

    private Handler mHandler;
    private static final int START_PLYER = 1001;
    private static  int PLAY_CODE_ONE = 1002;

    private VideoBean bean;
    private List<VideoBeanList> beanLists;
    private List<CustomViewBean> viewLists;

    private String starTime ;
    private String endTime;
    private String currentTime;
    private long timeNumber;

    long startT ;
    long endT ;
    long current;
    private  Timer timer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_custom_layout);
        aroot_player = findViewById(R.id.aroot_player);
        button_data = findViewById(R.id.button_data);
        tbutton_add1 = findViewById(R.id.tbutton_add1);
        tbutton_exit1 = findViewById(R.id.tbutton_exit1);
        tbutton_add2 = findViewById(R.id.tbutton_add2);
        tbutton_exit2 = findViewById(R.id.tbutton_exit2);
        tbutton_add3 = findViewById(R.id.tbutton_add3);
        tbutton_exit3 = findViewById(R.id.tbutton_exit3);
        button_data.setOnClickListener(this);

        tbutton_add1.setOnClickListener(this);
        tbutton_exit1.setOnClickListener(this);
        tbutton_add2.setOnClickListener(this);
        tbutton_exit2.setOnClickListener(this);
        tbutton_add3.setOnClickListener(this);
        tbutton_exit3.setOnClickListener(this);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                  case   START_PLYER:
                      for (int i =0;i<beanLists.size();i++){
                          currentTime = UIUtil.dqsj();
                          starTime = beanLists.get(i).getStarttime();
                          endTime = beanLists.get(i).getEndtime();
                          startT  = UIUtil.fromDateStringToLong(starTime);
                          endT   = UIUtil.fromDateStringToLong(endTime);
                          current = UIUtil.fromDateStringToLong(currentTime);
                          long timenumber = (current -endT)/1000;
                          Log.d(TAG, "handleMessage: 打印所有的时间---"+"开始的时间="+startT+"结束的时间="+endT+"当前的时间="+current+"时间差"+timenumber);
                          if (startT==current){//开始的时间和当前时间相等，或在开始和结束之间，开始播放视频
                              Log.d(TAG, "handleMessage: ---打印当前时间和开始时间相等情况"+startT+"==="+current);
                              viewLists.get(i).getMview().setParams(aroot_player,beanLists.get(i).getVideoid(),
                                      bean.getWidth(), bean.getHeight(), bean.getMargeleft(), bean.getMargetop(),
                                      beanLists.get(i).getVideoflag(), beanLists.get(i).getVideourl(), CustomActivity.this);

                          }else if (startT<current&&current<endT){//开始时间<当前时间<结束时间
                              Log.d(TAG, "handleMessage: ----打印在开始和结束之间的时间");
                              if (viewLists.get(i).getMview().isPlaying()){

                              }else {
                                  viewLists.get(i).getMview().setParams(aroot_player,beanLists.get(i).getVideoid(),
                                          bean.getWidth(), bean.getHeight(), bean.getMargeleft(), bean.getMargetop(),
                                          beanLists.get(i).getVideoflag(), beanLists.get(i).getVideourl(), CustomActivity.this);
                              }

                           }
                          else if (endT==current){//结束的时间和当前的时间相等，关闭视频C
                              Log.d(TAG, "handleMessage: "+"endT==current");
                            if (viewLists.get(i).getMview().isPlaying()){
                                viewLists.get(i).getMview().vvRemove(viewLists.get(i).getMview());
                                viewLists.remove(i);
                                beanLists.remove(i);
                            }
                            }else if (endT<current){//
//                              Log.d(TAG, "handleMessage: "+"-------------endT<current");
                              if (viewLists.get(i).getMview().isPlaying()){
                                  viewLists.get(i).getMview().vvRemove(viewLists.get(i).getMview());
                                  viewLists.remove(i);
                                  beanLists.remove(i);
                              }
                          }
                          else {
                              Log.d(TAG, "handleMessage: "+"-------------无相等的时间");
                          }

                    }

                    break ;
                }
            }
        };
       }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_data:
                Gson gson = new Gson() ;
                bean = gson.fromJson(data,VideoBean.class);
                beanLists =bean.getVideodata();
                if (beanLists.size()>0){
                    parseJsonDataToPalyer();//解析和构建数据
                }

                break;
            case R.id.tbutton_add1:

                break;
            case R.id.tbutton_exit1:

                break;
            case R.id.tbutton_add2:
                CustomPlayView vVideoView2 = new CustomPlayView(this);
                vVideoView2.setParams(aroot_player,2, 200, 200, 500, 500, 1, url2, this);
                break;
            case R.id.tbutton_exit2:

                break;
            case R.id.tbutton_add3:
                CustomPlayView vVideoView3 = new CustomPlayView(this);
                vVideoView3.setParams(aroot_player,3, 200, 200, 800, 800, 0, url3, this);

                break;
            case R.id.tbutton_exit3:

                break;
        }

    }

    private void parseJsonDataToPalyer() {
        viewLists = new  ArrayList<CustomViewBean>();
        timer = new Timer();
        for (int i =0;i<beanLists.size();i++){
            CustomPlayView playerview = new CustomPlayView(this);
            CustomViewBean  viewbean=  new CustomViewBean();
            viewbean.setEndtime(beanLists.get(i).getEndtime());
            viewbean.setMview(playerview);
            viewbean.setStarttime(beanLists.get(i).getStarttime());
            viewbean.setVideoid(beanLists.get(i).getVideoid());
            viewLists.add(viewbean);
          }
        timer.schedule(task,0,10000);//十秒发送一次
    }
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            mHandler.sendEmptyMessage(START_PLYER);
        }
    };

    private void starPlayerVideo(){
        VVideoView vVideoView = new VVideoView(this);
        vVideoView.setParams(aroot_player,bean.getId(), bean.getWidth(), bean.getHeight(), bean.getMargeleft(), bean.getMargetop(), 0, url3, this);
    }


    @Override
    public void onPreparedListener(IMediaPlayer iMediaPlayer, int id) {

    }

    @Override
    public void onCompletionListener(IMediaPlayer iMediaPlayer, int id) {

    }

    @Override
    public void onErrorListener(IMediaPlayer iMediaPlayer, int i, int i1, int id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler !=null){//移除mHandler的列队

        }
        timer.cancel();//关闭计时器
    }
}
