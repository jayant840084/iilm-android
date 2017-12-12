package net.models;

import com.google.gson.annotations.SerializedName;

import constants.UserAttributes;

/**
 * Created by jayan on 19-11-2016.
 */

public class UserModel {

    @SerializedName(UserAttributes.UID)
    private String uid;

    @SerializedName(UserAttributes.NAME)
    private String name;

    @SerializedName(UserAttributes.EMAIL)
    private String email;

    @SerializedName(UserAttributes.SCOPE)
    private String scope;

    @SerializedName(UserAttributes.GENDER)
    private String gender;

    @SerializedName(UserAttributes.PHONE_NUMBER)
    private String phoneNumber;

    @SerializedName(UserAttributes.BRANCH)
    private String branch;

    @SerializedName(UserAttributes.YEAR)
    private String year;

    @SerializedName(UserAttributes.ROOM_NUMBER)
    private String roomNumber;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
