package com.sy.ksyplayerdemoxu.bean;

/**
 * 创建时间： 2017/11/17 0017.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：
 */

public class VideoBeanList {
     private int     timetype;//播放时长
     private String  videourl;
     private String  starttime;//开始的时间
     private int     videoflag;
    private String   endtime;//结束的时间
    private int videoid;//播放的id

    public int getTimetype() {
        return timetype;
    }

    public void setTimetype(int timetype) {
        this.timetype = timetype;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public int getVideoflag() {
        return videoflag;
    }

    public void setVideoflag(int videoflag) {
        this.videoflag = videoflag;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getVideoid() {
        return videoid;
    }

    public void setVideoid(int videoid) {
        this.videoid = videoid;
    }

    @Override
    public String toString() {
        return "VideoBeanList{" +
                "timetype=" + timetype +
                ", videourl='" + videourl + '\'' +
                ", starttime='" + starttime + '\'' +
                ", videoflag=" + videoflag +
                ", endtime='" + endtime + '\'' +
                ", videoid=" + videoid +
                '}';
    }
}
