package com.pickhacks.pickhacks2019;

public class PlantItem {

    private int mPhoto;
    private String mBrief;
    private boolean mStar;
    private String mTime;
    private String mName;
    private String mFreq_water;
    private String mGen_advice;
    private String mHarvest_advice;
    private String mCare_advice;
    private String mPlanting_advice;


    public PlantItem(int photo, String brief, boolean star, String time, String name, String freq_water, String gen_advice, String harvest_advice, String care_advice, String planting_advice) {
        this.mPhoto = photo;
        this.mBrief = brief;
        this.mStar = star;
        this.mTime = time;
        this.mName = name;
        this.mFreq_water =  freq_water;
        this.mGen_advice = gen_advice;
        this.mHarvest_advice = harvest_advice;
        this.mCare_advice = care_advice;
        this.mPlanting_advice = planting_advice;
    }

    public int getmPhoto() {
        return mPhoto;
    }

    public void setmPhoto(int mPhoto) {
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

    public String getmFreq_water() { return mFreq_water;}

    public void setmFreq_water(String mFreq_water) { this.mFreq_water = mFreq_water;}

    public String getmGen_advice() { return mGen_advice;}

    public void setmGen_advice(String mGen_advice) { this.mGen_advice = mGen_advice;}

    public String getmHarvest_advice() { return mHarvest_advice;}

    public void setmHarvest_advice(String mHarvest_advice) {this.mHarvest_advice = mHarvest_advice;}

    public String getmCare_advice() {return mCare_advice;}

    public void setmCare_advice(String mCare_advice) {this.mCare_advice = mCare_advice;}

    public String getmPlanting_advice() {return mPlanting_advice;}

    public void setmPlanting_advice(String mPlanting_advice) {this.mPlanting_advice = mPlanting_advice;}
}
