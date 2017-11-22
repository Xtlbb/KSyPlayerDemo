package com.sy.ksyplayerdemoxu.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.sy.ksyplayerdemoxu.R;
import com.sy.ksyplayerdemoxu.service.TextToSpeechService;
import com.sy.ksyplayerdemoxu.ttsutil.AudioFocusHelper;


/**
 * 创建时间： 2017/9/1 0001.
 * 编写人：xutailian
 * 功能描述：
 */

public class ServicePlayerAct extends Activity{
    private Context mContext;
    Boolean mBound = false;
    TextToSpeechService mservice;
    private Button bt_startservice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_layour);
        AudioFocusHelper.getInstance(this).release();
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
//                    mservice.startPlayerVoice(text);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
