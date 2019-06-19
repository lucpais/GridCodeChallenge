package com.lucas.concept.gridcodechallenge.model;


import java.io.Serializable;

public class UserInfo implements Serializable, Comparable<UserInfo> {

    //  @ValueArea
    private String mFirstName;
    private String mLastName;
    private String mUsername;
    private String mEmailAddress;
    private String mThumbUrl;
    private String mImageUrl;


    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getEmailAddress() {
        return mEmailAddress;
    }

    public void setEmailAddress(String homeAddress) {
        this.mEmailAddress = homeAddress;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String uid) {
        this.mUsername = uid;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getThumbUrl() {
        return mThumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.mThumbUrl = thumbUrl;
    }

    @Override
    public int compareTo(UserInfo o) {
        return this.getFirstName().compareTo(o.getFirstName());
    }
}
