package com.sy.ksyplayerdemoxu;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sy.ksyplayerdemoxu.activity.CustomActivity;
import com.sy.ksyplayerdemoxu.activity.LocalPlayerAct;
import com.sy.ksyplayerdemoxu.activity.LocalPlayerActT;
import com.sy.ksyplayerdemoxu.activity.MedPlayer;
import com.sy.ksyplayerdemoxu.activity.ServicePlayerAct;
import com.sy.ksyplayerdemoxu.annotation.OnMPermissionDenied;
import com.sy.ksyplayerdemoxu.annotation.OnMPermissionGranted;
import com.sy.ksyplayerdemoxu.haha.Main2Activity;
import com.sy.ksyplayerdemoxu.util.MPermission;

//https://github.com/ksvc
//https://github.com/ksvc/KSYMediaPlayer_Android
//使用金山云的直播和播放视频的开源库

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_networkplayer;
    private Button bt_lingyun;
    private Button tb_localplayer;
    private Button button_moredis;
    private Button button_onedis;

    private static final int BASIC_PERMISSION_REQUEST_CODE=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        requestBasicPermission();
    }

    private void initView() {
        bt_networkplayer = (Button) findViewById(R.id.bt_networkplayer);
        bt_networkplayer.setOnClickListener(this);
        bt_lingyun = (Button) findViewById(R.id.bt_lingyun);
        bt_lingyun.setOnClickListener(this);
        tb_localplayer = (Button) findViewById(R.id.tb_localplayer);
        tb_localplayer.setOnClickListener(this);
        button_moredis = (Button) findViewById(R.id.button_moredis);
        button_moredis.setOnClickListener(this);
        button_onedis = (Button) findViewById(R.id.button_onedis);
        button_onedis.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_moredis:
                Intent intentc = new Intent(this,CustomActivity.class);
                startActivity(intentc);
                break;
            case R.id.button_onedis:
                Intent intenta = new Intent(this,Main2Activity.class);
                startActivity(intenta);
                break;

            case R.id.bt_networkplayer:
                Intent intent = new Intent(this,MedPlayer.class);
                startActivity(intent);
                break;
            case R.id.bt_lingyun:
                Intent intent2 = new Intent(this,ServicePlayerAct.class);
                startActivity(intent2);
                break;
            case R.id.tb_localplayer:
                Intent intent3 = new Intent(this,LocalPlayerAct.class);
                startActivity(intent3);
                break;


        }
    }
    /**
     * 基本权限管理
     */
    private void requestBasicPermission() {
        /**
         *   Manifest.permission.RECORD_AUDIO,
         Manifest.permission.ACCESS_COARSE_LOCATION,
         Manifest.permission.ACCESS_FINE_LOCATION
         */
        MPermission.with(MainActivity.this)
                .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.MODIFY_PHONE_STATE
                        ,Manifest.permission.ACCESS_FINE_LOCATION
                        ,Manifest.permission.CALL_PHONE
                        ,Manifest.permission.READ_LOGS
                        ,Manifest.permission.SET_DEBUG_APP
                        ,Manifest.permission.SYSTEM_ALERT_WINDOW
                        ,Manifest.permission.GET_ACCOUNTS
                        ,Manifest.permission.GET_ACCOUNTS
                        ,Manifest.permission.WAKE_LOCK
                        ,Manifest.permission.READ_CONTACTS
                        ,Manifest.permission.WRITE_SETTINGS
                        ,Manifest.permission.SEND_SMS
                        ,Manifest.permission.BLUETOOTH_ADMIN
                        ,Manifest.permission.USE_FINGERPRINT
                        ,Manifest.permission.BLUETOOTH
                        ,Manifest.permission.GET_TASKS
                        ,Manifest.permission.MODIFY_AUDIO_SETTINGS

                )
                .request();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        if (requestCode == CAMERA_PERMISSION) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Permission Granted
////                showCameraAction();
//            } else {
//                // Permission Denied
//                Toast.makeText(this, "没有相机权限", Toast.LENGTH_SHORT).show();
//            }
//        }
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
//        Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
//        Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
    }

}
