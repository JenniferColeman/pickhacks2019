package com.pickhacks.pickhacks2019;

public class SearchItem {
    private String mPhoto;
    private String mBrief;
    private boolean mStar;
    private String mTime;
    private String mName;

    public SearchItem(String photo, String brief, boolean star, String time, String name) {
        this.mPhoto = photo;
        this.mBrief = brief;
        this.mStar = star;
        this.mTime = time;
        this.mName = name;
    }

    public String getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(String mPhoto) {
        this.mPhoto = mPhoto;
    }

    public String getmBrief() {
        return mBrief;
    }

    public void setmBrief(String mBrief) {
        this.mBrief = mBrief;
    }

    public boolean ismStar() {
        return mStar;
    }

    public void setmStar(boolean mStar) {
        this.mStar = mStar;
    }

    public String getmTime() {
        return mTime;
    }

    public void setmTime(String mTime) {
        this.mTime = mTime;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }
}
