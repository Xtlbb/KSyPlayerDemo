package com.sy.ksyplayerdemoxu.bean;

import com.sy.ksyplayerdemoxu.haha.VVideoView;

/**
 * 创建时间： 2017/11/16 0016.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：管理cust
 */

public class ViewBean {
    private int videoid ;
    private VVideoView mview;
    private String starttime;
    private String endtime;
    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public VVideoView getMview() {
        return mview;
    }

    public void setMview(VVideoView mview) {
        this.mview = mview;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    @Override
    public String toString() {
        return "ViewBean{" +
                "videoid=" + videoid +
                ", mview=" + mview +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }
}
