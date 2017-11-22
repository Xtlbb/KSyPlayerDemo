package com.sy.ksyplayerdemoxu.bean;
import com.sy.ksyplayerdemoxu.view.CustomPlayView;
/**
 * 创建时间： 2017/11/20 0020.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：管理customview的bean
 */

public class CustomViewBean {
    private int videoid ;
    private CustomPlayView mview;
    private String starttime;
    private String endtime;

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    public CustomPlayView getMview() {
        return mview;
    }

    public void setMview(CustomPlayView mview) {
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
        return "CustomViewBean{" +
                "videoid=" + videoid +
                ", mview=" + mview +
                ", starttime='" + starttime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }
}
