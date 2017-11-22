package com.sy.ksyplayerdemoxu.bean;

import java.util.List;

/**
 * 创建时间： 2017/11/17 0017.
 * 编写人：Tina
 * 邮箱：1208156801@qq.com
 * 功能描述：
 */

public class VideoBean {

       private int  id;
       private int  width;
       private int  height;
       private int  margeleft;
       private int  margetop;

       private List<VideoBeanList>  videodata;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getMargeleft() {
        return margeleft;
    }

    public void setMargeleft(int margeleft) {
        this.margeleft = margeleft;
    }

    public int getMargetop() {
        return margetop;
    }

    public void setMargetop(int margetop) {
        this.margetop = margetop;
    }

    public List<VideoBeanList> getVideodata() {
        return videodata;
    }

    public void setVideodata(List<VideoBeanList> videodata) {
        this.videodata = videodata;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id=" + id +
                ", width=" + width +
                ", height=" + height +
                ", margeleft=" + margeleft +
                ", margetop=" + margetop +
                ", videodata=" + videodata +
                '}';
    }
}
